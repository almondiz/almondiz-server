package org.almondiz.almondiz.reply.replylike;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.CommentService;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"5-3. REPLYLIKE API"})
public class ReplyLikeController {

    private final ResponseService responseService;

    private final CommentService commentService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value="/reply/{replyId}/like")
    @ApiOperation(value="해당 대댓글 좋아요 생성")
    public SingleResult<ReplyLike> createReplyLike(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        ReplyLike replyLike = commentService.createReplyLike(replyId, uid);
        return responseService.getSingleResult(replyLike);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value="/reply/{replyId}/like")
    @ApiOperation(value="해당 대댓글 좋아요 취소")
    public CommonResult deleteReplyLike(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        commentService.deleteReplyLike(replyId, uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value="/reply/{replyId}/isLike")
    @ApiOperation(value="해당 대댓글 좋아요 유무 조회")
    public SingleResult<Boolean> isReplyLike(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        Boolean isLike = commentService.isReplyLike(replyId, uid);
        return responseService.getSingleResult(isLike);
    }
}
