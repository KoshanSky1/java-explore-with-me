package ru.yandex.practicum.event.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.event.comment.dto.CommentDto;
import ru.yandex.practicum.users.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public static Comment toComment(CommentDto commentDto, User author) {
        return new Comment(
                null,
                commentDto.getText(),
                commentDto.getEvent(),
                author,
                LocalDateTime.now(),
                null
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getEvent(),
                comment.getAuthor().getName(),
                comment.getCreated(),
                comment.getChanged()
        );
    }

}