package org.almondiz.almondiz.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.*;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult UserNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 계정이 존재하지 않거나 잘못된 계정입니다.");
    }

    @ExceptionHandler(CFollowExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("이미 존재하는 팔로우입니다.");
    }

    @ExceptionHandler(CFollowNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 팔로우가 존재하지 않습니다.");
    }

    @ExceptionHandler(CFollowNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 팔로우에 접근할 수 없습니다.");
    }
    @ExceptionHandler(CNotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NotificationNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 알림이 존재하지 않습니다.");
    }

    @ExceptionHandler(CNotificationNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NotificationNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 권한으로 알림에 접근할 수 없습니다.");
    }

    @ExceptionHandler(CRefreshTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult RefreshTokenException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("잘못된 Refresh 토큰입니다. 다시 입력해주세요.");
    }

    @ExceptionHandler(CExpiredTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ExpiredTokenException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("만료된 토큰입니다. 다시 입력해주세요.");
    }

    @ExceptionHandler(CTokenUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult TokenUserNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("토큰에 해당하는 계정이 존재하지 않거나 잘못된 계정입니다.");
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 리소스에 접근하기 위한 권한이 없습니다.");
    }

    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("보유한 권한으로 접근할 수 없는 리소스입니다.");
    }

}
