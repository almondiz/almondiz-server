package org.almondiz.almondiz.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.CNotificationNotFoundException;
import org.almondiz.almondiz.exception.exception.CNotificationNotPermittedException;
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
    protected CommonResult UserNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 계정이 존재하지 않거나 잘못된 계정입니다.");
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

}
