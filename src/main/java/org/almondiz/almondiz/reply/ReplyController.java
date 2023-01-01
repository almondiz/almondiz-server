package org.almondiz.almondiz.reply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.CommentService;
import org.almondiz.almondiz.reply.dto.ReplyRequestDto;
import org.almondiz.almondiz.reply.entity.Reply;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"5-2. REPLY API"})
public class ReplyController {

    private final ResponseService responseService;

    private final CommentService commentService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/comment/{commentId}/reply")
    @ApiOperation(value = "해당 댓글의 대댓글 작성")
    public SingleResult<Reply> createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        Reply reply = commentService.createReply(uid, commentId, requestDto);
        return responseService.getSingleResult(reply);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping(value = "/reply/{replyId}")
    @ApiOperation(value = "해당 댓글의 대댓글 수정")
    public SingleResult<Reply> updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        Reply reply = commentService.updateReply(uid, replyId, requestDto);
        return responseService.getSingleResult(reply);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/reply/{replyId}")
    @ApiOperation(value = "해당 댓글의 대댓글 삭제")
    public CommonResult deleteReply(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        commentService.deleteReply(uid, replyId);
        return responseService.getSuccessResult();
    }

}
