package org.almondiz.almondiz.comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentRequestDto;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"3. COMMENT API"})
public class CommentController {

    private final ResponseService responseService;

    private final CommentService commentService;

    @GetMapping(value="/post/{postId}/comments")
    @ApiOperation(value="해당 게시물의 댓글 목록 조회")
    public ListResult<CommentResponseDto> findAllCommentsByPost(@PathVariable Long postId){
        return responseService.getListResult(commentService.findAllByPostId(postId));
    }

    @PostMapping(value="/post/{postId}/comment")
    @ApiOperation(value="post에 댓글 추가")
    public SingleResult<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto){
        // user id 임시값 - JWT 에서 조회 필요
        Long userId = Long.valueOf(1);

        CommentResponseDto commentResponseDto = commentService.create(userId, postId, commentRequestDto);
        return responseService.getSingleResult(commentResponseDto);
    }

    @DeleteMapping(value="/post/{postId}/comment/{commentId}")
    @ApiOperation(value="댓글 삭제")
    public CommonResult deleteComment(@PathVariable Long commentId){
        commentService.delete(commentId);
        return responseService.getSuccessResult();
    }

    @PatchMapping(value="/post/{postId}/comment/{commentId}")
    @ApiOperation(value="댓글 수정")
    public SingleResult<CommentResponseDto> modifyComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto commentResponseDto = commentService.update(commentId, commentRequestDto);
        return responseService.getSingleResult(commentResponseDto);
    }

}
