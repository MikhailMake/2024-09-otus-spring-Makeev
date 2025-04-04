package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    @ShellMethod(key = "cbid", value = "Find comment by id")
    public String getComment(@ShellOption long id) {
        return commentService.findById(id).toString();
    }

    @ShellMethod(key = "cbbid", value = "Find all comments by book id")
    public String listComments(@ShellOption long bookId) {
        List<Comment> comments = commentService.findByBookId(bookId);
        return comments.isEmpty()
                ? "No Comments"
                : comments.stream()
                .map(c -> String.format("%d: %s", c.getId(), c.getText()))
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "cins", value = "Insert Command")
    public String insertComment(
            @ShellOption long bookId,
            @ShellOption String text) {
        return "Insert comment: " + commentService.insert(bookId, text);
    }

    @ShellMethod(key = "cupd", value = "Update comment")
    public String updateComment(
            @ShellOption long id,
            @ShellOption String text,
            @ShellOption long bookId) {
        return "Update: " + commentService.update(id, bookId, text);
    }

    @ShellMethod(key = "dcom", value = "Delete comment by id")
    public String deleteComment(@ShellOption long id) {
        commentService.deleteById(id);
        return "Comment " + id + " was deleted";
    }
}