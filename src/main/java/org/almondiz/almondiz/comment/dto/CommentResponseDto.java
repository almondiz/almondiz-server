package org.almondiz.almondiz.comment.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private UserSimpleResponseDto user;

    private String text;

    private Long likedCount;

    private boolean like;

    public CommentResponseDto(Comment comment, UserSimpleResponseDto user, Long likedCount, boolean like) {
        this.commentId = comment.getCommentId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.user = user;
        this.likedCount = likedCount;
        this.like = like;
    }
}
