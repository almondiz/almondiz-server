package org.almondiz.almondiz.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowRequestDto {
    private Long followeeId;

    private String alias;
}
