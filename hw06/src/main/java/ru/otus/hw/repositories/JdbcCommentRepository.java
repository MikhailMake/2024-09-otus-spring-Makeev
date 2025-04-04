package ru.otus.hw.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcCommentRepository implements CommentRepository {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcCommentRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Comment> findByBookId(long id) {
        String sql = "SELECT id, text, book_id FROM comments WHERE book_id = :id";
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.query(
                "select id, text from comments  where book_id = :id",
                params,new JdbcCommentRepository.CommentRowMapper());

    }

    @Override
    public Optional<Comment> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return  Optional.of(namedParameterJdbcOperations.queryForObject(
                "select id, text from comments where id = :id", params, new JdbcCommentRepository.CommentRowMapper()));
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            return insert(comment);
        }
        return update(comment);
    }

    private Comment insert(Comment comment) {
        String sql = "INSERT INTO comments (text, book_id) VALUES (:text, :bookId)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("text", comment.getText())
                .addValue("bookId", comment.getBook().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(sql, parameters, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        comment.setId(keyHolder.getKeyAs(Long.class));
        return comment;
    }

    private Comment update(Comment comment) {
        String sql = "UPDATE comments SET text = :text, book_id = :bookId  WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("text", comment.getText())
                .addValue("bookId", comment.getBook().getId())
                .addValue("id", comment.getId());

        int updatedRows = namedParameterJdbcOperations.update(sql, parameters);

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Comment with id " + comment.getId() + " not found");
        }
        return comment;
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from comments where id = :id", params);
    }

    private static class CommentRowMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String text = rs.getString("text");
            //long bookId = rs.getLong("book_id");
            //Book book = new Book(bookId, text, null, null,new ArrayList<>());
            return new Comment(id, text,new Book());
        }
    }
}
