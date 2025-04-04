package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long id);

    Comment insert(long bookId, String text);

    Comment update(long id,long bookId, String text);

    void deleteById(long id);
    }
