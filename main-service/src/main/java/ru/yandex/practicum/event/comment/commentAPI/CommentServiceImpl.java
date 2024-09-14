package ru.yandex.practicum.event.comment.commentAPI;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.comment.Comment;
import ru.yandex.practicum.event.comment.CommentRepository;
import ru.yandex.practicum.event.comment.dto.NewCommentDto;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.error.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.publicAPI.PublicEventsServiceImpl;
import ru.yandex.practicum.users.admin.AdminUserService;
import ru.yandex.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdminUserService userService;
    private final PublicEventsServiceImpl eventService;

    @Override
    public Comment addNewComment(long userId, long eventId, NewCommentDto newCommentDto) {
        User user = userService.getUserById((int) userId);
        Event event = eventService.getEventByIdWithoutRequest((int) eventId);

        Comment comment = new Comment(null, newCommentDto.getText(), event, user, LocalDateTime.now(),
                null);

        try {
            comment = commentRepository.save(comment);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Unable to add comment");
        }
        log.info("Добавлен новый комментарий: " + comment);

        return comment;
    }

    @Override
    public Comment updateComment(long userId, long eventId, long commentId, NewCommentDto newCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Comment not found with id = " + commentId));

        if (comment.getAuthor().getId() != userId || comment.getEvent().getId() != eventId) {
            throw new NotFoundException("Comment not found with eventId = " + eventId + " and userId = " + userId);
        }

        if (comment.getText().equals(newCommentDto.getText())) {
            throw new ConflictException("Nothing has changed");
        }

        comment.setText(newCommentDto.getText());
        comment.setChanged(LocalDateTime.now());

        log.info("Обновлен комментарий: " + commentId);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long userId, long eventId, long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Comment not found with id = " + commentId));

        if (comment.getAuthor().getId() != userId || comment.getEvent().getId() != eventId) {
            throw new NotFoundException("Comment not found with eventId = " + eventId + " and userId = " + userId);
        }
        commentRepository.delete(comment);

        log.info("Удален комментарий: " + commentId);
    }

    @Override
    public List<Comment> getAllCommentsByEventId(long userId, long eventId, String sort, HttpServletRequest request) {
        User user = userService.getUserById((int) userId);
        Event event = eventService.getEventByIdWithoutRequest((int) eventId);

        List<Comment> comments = commentRepository.findAllByEventId(eventId);

        if (sort != null) {
            switch (sort) {
                case "CHANGED":
                    comments.sort(Comparator.comparing(Comment::getChanged));
                    break;
                default:
                    comments.sort(Comparator.comparing(Comment::getCreated));
            }
        }

        log.info("Сформирован список всех комментариев к событию: " + eventId);

        return comments;
    }

}