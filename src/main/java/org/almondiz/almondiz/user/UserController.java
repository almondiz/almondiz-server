package org.almondiz.almondiz.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.almondiz.almondiz.user.dto.UserLogInDto;
import org.almondiz.almondiz.user.dto.UserRegisterDto;
import org.almondiz.almondiz.user.dto.UserRequestDto;
import org.almondiz.almondiz.user.dto.UserResponseDto;
import org.almondiz.almondiz.user.entity.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    private final AuthService authService;

    @GetMapping(value = "/user/login")
    @ApiOperation(value = "로그인")
    public CommonResult logIn(@RequestBody UserLogInDto logInDto){
        Token token = authService.signIn(logInDto.getUserEmail());
        return responseService.getSingleResult(token);
    }

    @PostMapping(value="/user")
    @ApiOperation(value = "회원가입")
    public CommonResult createUser(@RequestBody UserRegisterDto userRegisterDto){
        Token token = authService.signup(userRegisterDto);
        return responseService.getSingleResult(token);
    }

    @GetMapping(value="/users")
    @ApiOperation(value = "모든 회원 조회")
    public ListResult<UserResponseDto> findAllUsers() {
        return responseService.getListResult(userService.getAllUsers());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value="/user")
    @ApiOperation(value = "회원 정보 조회")
    public SingleResult<UserResponseDto> findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return responseService.getSingleResult(userService.getUserByEmail(email));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value="/user")
    @ApiOperation(value = "회원 탈퇴")
    public CommonResult deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.deleteUserByEmail(email);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping(value="/user")
    @ApiOperation(value = "회원 정보 수정")
    public CommonResult modifyUser(@RequestBody UserRequestDto userRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.modifyUser(email, userRequestDto);
        return responseService.getSuccessResult();
    }

}

/**
INSERT INTO USER_TABLE VALUES (1, NOW(), NOW(), NOW(), 'aaa@aaa.com', 1, 1, 'GOOGLE', 'USER', 'ALIVE', 1, 1);
INSERT INTO NUT_TABLE VALUES (1, NOW(), NOW(), '캐슈넛');
INSERT INTO TAG_TABLE VALUES (1, NOW(), NOW(), '마라탕');
INSERT INTO PROFILE_FILE_TABLE VALUES (1, NOW(), NOW(), 'www.image.com');
 */
