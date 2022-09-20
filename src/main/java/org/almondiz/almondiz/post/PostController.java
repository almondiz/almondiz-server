package org.almondiz.almondiz.post;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.post.dto.PostInFeedResponseDto;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Api(tags = {"2. POST API"})
public class PostController {

    private final ResponseService responseService;
    private final PostService postService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value="/post")
    @ApiOperation(value="post 작성")
    public SingleResult<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        PostResponseDto postResponseDto = postService.createPost(email, postRequestDto);
        return responseService.getSingleResult(postResponseDto);
    }

    @GetMapping(value="/posts")
    @ApiOperation(value = "모든 post 조회")
    public ListResult<PostInFeedResponseDto> findAllPosts(){
        return responseService.getListResult(postService.getAllPosts());
    }

    @GetMapping(value="/post/{postId}")
    @ApiOperation(value = "post 단건 조회")
    public SingleResult<PostResponseDto> findPost(@PathVariable Long postId){
        return responseService.getSingleResult(postService.getPostDtoById(postId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value="/user/posts")
    @ApiOperation(value = "사용자별 post 조회")
    public ListResult<PostResponseDto> findPostsByUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getListResult(postService.getPostsByUserEmail(email));
    }

    @GetMapping(value="/store/{storeId}/posts")
    @ApiOperation(value = "상점별 post 조회")
    public ListResult<PostResponseDto> findPostsByStoreId(@PathVariable Long storeId){
        return responseService.getListResult(postService.getPostsByStoreId(storeId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value="/post/{postId}")
    @ApiOperation(value = "post 삭제")
    public CommonResult deletePost(@PathVariable Long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        postService.deletePost(email, postId);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping(value="/post/{postId}")
    @ApiOperation(value = "post 수정")
    public CommonResult modifyPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        postService.modifyPost(email, postId, postRequestDto);
        return responseService.getSuccessResult();
    }


















}
