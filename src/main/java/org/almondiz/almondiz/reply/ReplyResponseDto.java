package org.almondiz.almondiz.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyResponseDto {

    private Long commentId;

    private LocalDateTime createdAt;

    private UserSimpleResponseDto user;

    private String text;

    private  Long likedCount;

    private boolean like;
}
