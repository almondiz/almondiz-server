package org.almondiz.almondiz.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.almondiz.almondiz.user.dto.UserRegisterDto;
import org.almondiz.almondiz.user.dto.UserRequestDto;
import org.almondiz.almondiz.user.dto.UserResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
@Api(tags = {"user api"})
public class UserController {
    private final ResponseService responseService;
    private final UserService userService;

    @PostMapping(value="/user")
    @ApiOperation(value = "회원가입")
    public CommonResult createUser(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.signup(userRegisterDto);
        return responseService.getSuccessResult();
    }

    @GetMapping(value="/users")
    @ApiOperation(value = "모든 회원 조회")
    public ListResult<UserResponseDto> findAllUsers() {
        return responseService.getListResult(userService.getAllUsers());
    }

    @GetMapping(value="/user/{userId}")
    @ApiOperation(value = "회원 정보 조회")
    public SingleResult<UserResponseDto> findUser(@PathVariable Long userId) {
        return responseService.getSingleResult(userService.getUser(userId));
    }

    @DeleteMapping(value="/user/{userId}")
    @ApiOperation(value = "회원 탈퇴")
    public CommonResult deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return responseService.getSuccessResult();
    }

    @PatchMapping(value="/user/{userId}")
    @ApiOperation(value = "회원 정보 수정")
    public CommonResult modifyUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        userService.modifyUser(userId, userRequestDto);
        return responseService.getSuccessResult();
    }

}

/**
INSERT INTO NUT_TABLE VALUES (1, NOW(), NOW(), '캐슈넛');
INSERT INTO TAG_TABLE VALUES (1, NOW(), NOW(), '마라탕');
INSERT INTO PROFILE_FILE_TABLE VALUES (1, NOW(), NOW(), 'www.image.com');
INSERT INTO USER_TABLE VALUES (1, NOW(), NOW(), NOW(), 'aaa@aaa.com', 1, 1, 'GOOGLE', 'USER', 'ALIVE', 1, 1);
INSERT INTO STORE_TABLE VALUES (1, NOW(), NOW(), '수원시 영통구', 1, '031-111-1111', 1.3, 1.3, 1, '맥도날드');
INSERT INTO POST_FILE_TABLE VALUES (1, NOW(), NOW(),'www.image.com', 1, 'type');
INSERT INTO POST_TABLE VALUES (1, NOW(), NOW(), 'abcd', 'ALIVE', 1, 'title', 1);
 */
