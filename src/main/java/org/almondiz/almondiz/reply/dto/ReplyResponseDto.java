package org.almondiz.almondiz.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.common.Relation;
import org.almondiz.almondiz.reply.entity.Reply;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyResponseDto {

    private Long replyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private UserSimpleResponseDto user;

    private Relation relation;

    private String text;

    private Long likedCount;

    private boolean like;

    public ReplyResponseDto(Reply reply, UserSimpleResponseDto user, Relation relation, Long likedCount, boolean like) {
        this.replyId = reply.getReplyId();
        this.text = reply.getText();
        this.createdAt = reply.getCreatedAt();
        this.user = user;
        this.relation = relation;
        this.likedCount = likedCount;
        this.like = like;
    }
}
