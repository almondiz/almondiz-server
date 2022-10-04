package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.entity.Thumb;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long profileId;

    private Long tagId;

    private Long nutId;

    private Thumb thumb;

}
