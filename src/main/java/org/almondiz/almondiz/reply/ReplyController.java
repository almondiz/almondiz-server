package org.almondiz.almondiz.reply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"6-0. REPLY API"})
public class ReplyController {

    private final ResponseService responseService;

    private final ReplyService replyService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/reply/{replyId}")
    @ApiOperation(value = "대댓글id로 조회")
    public SingleResult<ReplyResponseDto> findReplyById(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(replyService.findReplyDtoById(replyId, uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/comment/{commentId}/replies")
    @ApiOperation(value = "댓글에 해당하는 대댓글 목록 조회")
    public ListResult<ReplyResponseDto> findAllReplyByComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getListResult(replyService.findAllReplyByComment(commentId, uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/comment/{commentId}/reply")
    @ApiOperation(value = "대댓글 생성")
    public CommonResult create(@PathVariable Long commentId, @RequestBody ReplyRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        replyService.create(uid, commentId, requestDto);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping(value = "/reply/{replyId}")
    @ApiOperation(value = "대댓글 수정")
    public CommonResult update(@PathVariable Long replyId, @RequestBody ReplyRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        replyService.update(uid, replyId, requestDto);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/reply/{replyId}")
    @ApiOperation(value = "대댓글 삭제")
    public CommonResult delete(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        replyService.delete(uid, replyId);

        return responseService.getSuccessResult();
    }
}
