package org.almondiz.almondiz.commentlike;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"9. COMMENT LIKE API"})
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value="/comment/{commentId}/like")
    @ApiOperation(value="해당 게시물 댓글 좋아요 생성")
    public CommonResult create(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        commentLikeService.create(commentId, uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value="/comment/{commentId}/like")
    @ApiOperation(value="해당 게시물 댓글 좋아요 삭제(취소)")
    public CommonResult delete(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        commentLikeService.delete(commentId, uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value="/comment/{commentId}/islike")
    @ApiOperation(value="해당 게시물 댓글 좋아요 유무 조회")
    public SingleResult<Boolean> isCommentLike(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        Boolean isLike = commentLikeService.isLike(commentId, uid);
        return responseService.getSingleResult(isLike);
    }
}
