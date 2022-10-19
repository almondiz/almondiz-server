package org.almondiz.almondiz.replylike;

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
@Api(tags = {"11. REPLY LIKE API"})
public class ReplyLikeController {

    private final ResponseService responseService;

    private final ReplyLikeService replyLikeService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value="/reply/{replyId}/like")
    @ApiOperation(value="해당 게시물 대댓글 좋아요 생성")
    public CommonResult create(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        replyLikeService.create(replyId, uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value="/reply/{replyId}/like")
    @ApiOperation(value="해당 게시물 대댓글 좋아요 삭제(취소)")
    public CommonResult delete(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        replyLikeService.delete(replyId, uid);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value="/reply/{replyId}/islike")
    @ApiOperation(value="해당 게시물 대댓글 좋아요 유무 조회")
    public SingleResult<Boolean> isReplyLike(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        Boolean isLike = replyLikeService.isLike(replyId, uid);
        return responseService.getSingleResult(isLike);
    }
}
