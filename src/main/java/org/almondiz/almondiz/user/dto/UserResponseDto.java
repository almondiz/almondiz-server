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
public class UserResponseDto {
    private Long userId;
    private String email;
    private Role role;
    private String profileImgUrl;
    private String nickName;

    public UserResponseDto(User user, String profileImgUrl, String nickName){
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.profileImgUrl = profileImgUrl;
        this.nickName = nickName;
    }

}
