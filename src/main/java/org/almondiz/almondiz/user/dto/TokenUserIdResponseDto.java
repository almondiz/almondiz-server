package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.entity.Token;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenUserIdResponseDto {

    private Token token;

    private Long userId;
}
