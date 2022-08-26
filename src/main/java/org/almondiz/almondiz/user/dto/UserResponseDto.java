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

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole();

        // 다른 테이블에서 조회하는 방법으로 수정 필요 - dto에서 조회하는게 맞는가?
        this.profileImgUrl = "";
        this.nickName = "";
    }

}
