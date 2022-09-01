package org.almondiz.almondiz.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.CFollowExistedException;
import org.almondiz.almondiz.exception.exception.CFollowNotFoundException;
import org.almondiz.almondiz.exception.exception.CFollowNotPermittedException;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
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
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
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
}
