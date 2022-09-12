package org.almondiz.almondiz.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.almondiz.almondiz.common.TimeStamped;

@Builder
@AllArgsConstructor
@Getter
public class Token extends TimeStamped {

    private final String accessToken;

    private final String refreshToken;
}
