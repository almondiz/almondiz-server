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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult UserNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 계정이 존재하지 않거나 잘못된 계정입니다.");
    }

    @ExceptionHandler(FollowExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("이미 존재하는 팔로우입니다.");
    }

    @ExceptionHandler(FollowNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 팔로우가 존재하지 않습니다.");
    }

    @ExceptionHandler(FollowNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult FollowNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 팔로우에 접근할 수 없습니다.");
    }

    @ExceptionHandler(NotificationNotFoundException.class)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NotificationNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 알림이 존재하지 않습니다.");
    }

    @ExceptionHandler(NotificationNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NotificationNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 권한으로 알림에 접근할 수 없습니다.");
    }

    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult RefreshTokenException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("잘못된 Refresh 토큰입니다. 다시 입력해주세요.");
    }

    @ExceptionHandler(ExpiredTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ExpiredTokenException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("만료된 토큰입니다. 다시 입력해주세요.");
    }

    @ExceptionHandler(TokenUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult TokenUserNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("토큰에 해당하는 계정이 존재하지 않거나 잘못된 계정입니다.");
    }

    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 리소스에 접근하기 위한 권한이 없습니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("보유한 권한으로 접근할 수 없는 리소스입니다.");
    }

    @ExceptionHandler(NotValidEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NotValidEmailException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("옳지 않은 이메일입니다. 이메일 형식을 확인해주세요");
    }

    @ExceptionHandler(ShopExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ShopExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 위치의 상호명은 이미 존재합니다.");
    }

    @ExceptionHandler(ShopScrapExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ShopScrapExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("사용자는 해당 업체를 이미 스크랩하였습니다.");
    }

    @ExceptionHandler(ShopScrapNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ShopScrapNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 업체스크랩은 존재하지 않습니다");
    }

    @ExceptionHandler(ShopScrapNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ShopScrapNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 권한으로 업체 스크랩에 접근할 수 없습니다.");
    }

    @ExceptionHandler(PostNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 게시글에 접근할 수 없습니다.");
    }

    @ExceptionHandler(CommentNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult CommentNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 댓글에 접근할 수 없습니다.");
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult CommentNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 댓글이 존재하지 않습니다.");
    }

    @ExceptionHandler(AccountExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult AccountExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("주어진 정보에 대한 회원 계정이 이미 존재합니다.");
    }

    @ExceptionHandler(NutNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NutNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 nut 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(PostFileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostFileNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 postfile 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(ProfileFileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ProfileFileNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 profilefile 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 게시글이 존재하지 않습니다.");
    }

    @ExceptionHandler(ShopNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ShopNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 식당이 존재하지 않습니다.");
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult TagNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 태그가 존재하지 않습니다.");
    }

    @ExceptionHandler(TagPostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult TagPostNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 게시글에 해당 태그가 되지 않은 상태입니다.");
    }

    @ExceptionHandler(TagExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult TagExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 태그가 이미 존재합니다.");
    }

    @ExceptionHandler(PostScrapExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostScrapExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("사용자는 해당 리뷰를 이미 스크랩하였습니다.");
    }

    @ExceptionHandler(PostScrapNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostScrapNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 리뷰스크랩은 존재하지 않습니다");
    }

    @ExceptionHandler(PostScrapNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult PostScrapNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 권한으로 리뷰 스크랩에 접근할 수 없습니다.");
    }

    @ExceptionHandler(ReportExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReportExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 게시글은 이미 신고처리되었습니다.");
    }

    @ExceptionHandler(CommentLikeNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult CommentLikeNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 댓글 좋아요는 존재하지 않습니다.");
    }

    @ExceptionHandler(CommentLikeExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult CommentLikeExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 댓글 좋아요는 이미 존재합니다.");
    }

    @ExceptionHandler(CommentLikeNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult CommentLikeNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 댓글 좋아요에 대한 접근 권한이 없습니다.");
    }


    @ExceptionHandler(ReplyLikeNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReplyLikeNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 대댓글 좋아요는 존재하지 않습니다.");
    }

    @ExceptionHandler(ReplyLikeExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReplyLikeExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 대댓글 좋아요는 이미 존재합니다.");
    }

    @ExceptionHandler(ReplyNotPermittedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReplyNotPermittedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 대댓글에 접근할 수 없습니다.");
    }

    @ExceptionHandler(ReplyNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReplyNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 대댓글이 존재하지 않습니다.");
    }

    @ExceptionHandler(ReplyExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult ReplyExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 대댓글은 이미 존재합니다.");
    }

    @ExceptionHandler(NutExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult NutExistedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResultWithMsg("해당 너트가 이미 존재합니다.");
    }

}
