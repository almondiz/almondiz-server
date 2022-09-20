package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.entity.Role;
import org.almondiz.almondiz.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAsWriterResponseDto {

    private Long userId;
    private Role role;
    private String profileImgUrl;
    private String nickName;

    // 팔로워 여부 표현 필요

    public UserAsWriterResponseDto(User user, String profileImgUrl, String nickName){
        this.userId = user.getUserId();
        this.role = user.getRole();
        this.profileImgUrl = profileImgUrl;
        this.nickName = nickName;
    }

}
