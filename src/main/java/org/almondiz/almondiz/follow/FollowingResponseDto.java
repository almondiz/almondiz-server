package org.almondiz.almondiz.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FollowingResponseDto {
    private Long followId;

    private Long followingId;

    private String followingNickName;

    private String followingProfileImg;

    private String alias;

    private LocalDateTime createdAt;
}
