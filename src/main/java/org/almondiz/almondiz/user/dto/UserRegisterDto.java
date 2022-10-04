package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.entity.ProviderType;
import org.almondiz.almondiz.user.entity.Thumb;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

     private String providerUid;

     private ProviderType providerType;

     private String email;

     private Long profileId;

     private Long tagId;

     private Long nutId;

     private Thumb thumb;
}
