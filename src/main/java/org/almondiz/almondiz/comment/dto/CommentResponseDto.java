package org.almondiz.almondiz.comment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.user.dto.UserAsWriterResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;

    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private UserAsWriterResponseDto user;

    public CommentResponseDto(Comment comment, UserAsWriterResponseDto user) {
        this.commentId = comment.getCommentId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.user = user;
    }
}
