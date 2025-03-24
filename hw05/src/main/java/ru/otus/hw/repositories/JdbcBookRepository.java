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
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {
    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(
                "SELECT books.id,books.title,books.author_id,books.genre_id," +
                        "authors.full_name AS author_name,genres.name AS genre_name " +
                        "FROM books " +
                        "LEFT JOIN authors ON authors.id = books.author_id " +
                        "LEFT JOIN genres ON genres.id = books.genre_id where books.id = :id", params,
                new JdbcBookRepository.BookRowMapper());
       return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT books.id,books.title,books.author_id,books.genre_id," +
                        "authors.full_name AS author_name,genres.name AS genre_name " +
                "FROM books " +
                "LEFT JOIN authors ON authors.id = books.author_id " +
                "LEFT JOIN genres ON genres.id = books.genre_id"
                , new JdbcBookRepository.BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    private Book insert(Book book) {
        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :authorId, :genreId)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId", book.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(sql, parameters, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        String sql = "UPDATE books SET title = :title, author_id = :authorId, " +
                "genre_id = :genreId WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId", book.getGenre().getId())
                .addValue("id", book.getId());

        int updatedRows = namedParameterJdbcOperations.update(sql, parameters);

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Book with id " + book.getId() + " not found");
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long idAuthor = rs.getLong("author_id");
            long idGenre = rs.getLong("genre_id");
            String fullName = rs.getString("author_name");
            String name = rs.getString("genre_name");
            Author author = new Author(idAuthor,fullName);
            Genre genre = new Genre(idGenre,name);
            return new Book(id, title,author,genre);
        }
    }
}
