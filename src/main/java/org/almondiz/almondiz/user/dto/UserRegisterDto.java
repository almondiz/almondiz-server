package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
     private Long userId;
     private Long profileId;
     private Long tagId;
     private Long nutId;
}
