package org.almondiz.almondiz.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;

    private Long userId;

    private String nickName;

    private String text;

    public CommentResponseDto(Comment comment, String nickName) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUser().getUserId();
        this.nickName = nickName;
        this.text = comment.getText();
    }

}
