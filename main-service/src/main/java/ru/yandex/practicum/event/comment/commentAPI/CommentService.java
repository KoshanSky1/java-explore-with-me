package ru.yandex.practicum.event.comment.commentAPI;

import jakarta.servlet.http.HttpServletRequest;
import ru.yandex.practicum.event.comment.Comment;
import ru.yandex.practicum.event.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    Comment addNewComment(long userId, long eventId, NewCommentDto newCommentDto);

    Comment updateComment(long userId, long eventId, long commentId, NewCommentDto newCommentDto);

    void deleteComment(long userId, long eventId, long commentId);

    List<Comment> getAllCommentsByEventId(long userId, long eventId, String sort, HttpServletRequest request);
}