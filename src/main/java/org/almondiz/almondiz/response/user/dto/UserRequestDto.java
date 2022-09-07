package org.almondiz.almondiz.response.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Long profileId;
    private Long tagId;
    private Long nutId;

}
