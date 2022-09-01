package org.almondiz.almondiz.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerResponseDto {
    private Long followId;

    private Long followerId;

    private String  followerNickName;

    private String followerProfileImg;

    private LocalDateTime createdAt;

}
