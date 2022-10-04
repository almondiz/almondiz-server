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
import org.almondiz.almondiz.user.dto.*;
import org.almondiz.almondiz.user.entity.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"1. USER AUTH API"})
public class UserController {
    private final ResponseService responseService;
    private final UserService userService;

    private final AuthService authService;

    @PostMapping(value = "/user/login")
    @ApiOperation(value = "로그인")
    public CommonResult logIn(@RequestBody UserLogInDto logInDto) {
        Token token = authService.signIn(logInDto.getProviderUid(), logInDto.getProviderType());
        return responseService.getSingleResult(token);
    }

    @PostMapping(value = "/user")
    @ApiOperation(value = "회원가입")
    public CommonResult createUser(@RequestBody UserRegisterDto userRegisterDto) {
        Token token = authService.signup(userRegisterDto);
        return responseService.getSingleResult(token);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 refresh_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/user/token")
    @ApiOperation(value = "AccessToken 재발급", notes = "RefreshToken을 헤더에 넣어 AccessToken을 재발급 받는다")
    public CommonResult getAccessTokenByRefreshToken(@RequestHeader(value = "AUTH-TOKEN") String refreshToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        Token token = authService.refreshTokenAccessToken(uid, refreshToken);
        return responseService.getSingleResult(token);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/users")
    @ApiOperation(value = "모든 회원 조회")
    public ListResult<UserResponseDto> findAllUsers() {
        return responseService.getListResult(userService.getAllUsers());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/user")
    @ApiOperation(value = "내 정보 조회")
    public SingleResult<UserResponseDto> findMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(userService.getUserByUid(uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/user/{userId}")
    @ApiOperation(value = "회원 정보 조회")
    public SingleResult<UserSimpleResponseDto> findUserInfo(@PathVariable Long userId) {
        return responseService.getSingleResult(userService.getUserSimpleResponseDto(userId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/user")
    @ApiOperation(value = "회원 탈퇴")
    public CommonResult deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        userService.deleteUserByUid(uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping(value = "/user")
    @ApiOperation(value = "회원 정보 수정")
    public CommonResult modifyUser(@RequestBody UserRequestDto userRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        userService.modifyUser(uid, userRequestDto);
        return responseService.getSuccessResult();
    }
}

