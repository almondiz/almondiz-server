package org.almondiz.almondiz.post;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
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
@RequestMapping(value="/api")
@Api(tags = {"post api"})
public class PostController {

    private final ResponseService responseService;
    private final PostService postService;

    @PostMapping(value="/post")
    @ApiOperation(value="post 작성")
    public SingleResult<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return responseService.getSingleResult(postResponseDto);
    }

    @GetMapping(value="/posts")
    @ApiOperation(value = "모든 post 조회")
    public ListResult<PostResponseDto> findAllPosts(){
        return responseService.getListResult(postService.getAllPosts());
    }

    @GetMapping(value="/post/{postId}")
    @ApiOperation(value = "post 단건 조회")
    public SingleResult<PostResponseDto> findPost(@PathVariable Long postId){
        return responseService.getSingleResult(postService.getPostByPostId(postId));
    }

    @GetMapping(value="/user/{userId}/posts")
    @ApiOperation(value = "사용자별 post 조회")
    public ListResult<PostResponseDto> findPostsByUserId(@PathVariable Long userId){
        return responseService.getListResult(postService.getPostsByUserId(userId));
    }

    @GetMapping(value="/store/{storeId}/posts")
    @ApiOperation(value = "상점별 post 조회")
    public ListResult<PostResponseDto> findPostsByStoreId(@PathVariable Long storeId){
        return responseService.getListResult(postService.getPostsByStoreId(storeId));
    }

    @DeleteMapping(value="/post/{postId}")
    @ApiOperation(value = "post 삭제")
    public CommonResult deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return responseService.getSuccessResult();
    }

    @PatchMapping(value="/post/{postId}")
    @ApiOperation(value = "post 수정")
    public CommonResult modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        postService.modifyPost(postId, postRequestDto);
        return responseService.getSuccessResult();
    }


















}
