package org.almondiz.almondiz.postscrap;

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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"4-1. POST SCRAP API"})
public class PostScrapController {

    private final ResponseService responseService;

    private final PostScrapService postScrapService;

    @GetMapping(value = "/postScrap/{postScrapId}")
    @ApiOperation(value = "업체 스크랩 단건 조회")
    public SingleResult<PostScrap> getPostScrapById(@PathVariable Long postScrapId) {
        PostScrap postScrap = postScrapService.findPostScrapById(postScrapId);

        return responseService.getSingleResult(postScrap);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/postScrap/post/{postId}/user")
    @ApiOperation(value = "리뷰 스크랩 생성")
    public SingleResult<PostScrapResponseDto> create(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(postScrapService.create(uid, postId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/postScraps/user")
    @ApiOperation(value = "사용자별 리뷰 스크랩 목록 조회")
    public ListResult<PostScrapResponseDto> findAllPostScrapByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getListResult(postScrapService.findAllPostScrapByUser(uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/postScrap/post/{postId}/isScrap")
    @ApiOperation(value = "리뷰 스크랩 여부 조회")
    public SingleResult<Boolean> isScrap(@PathVariable Long postScrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(postScrapService.isScrap(uid, postScrapId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/postScrap/post/{postId}/count")
    @ApiOperation(value = "리뷰별 리뷰 스크랩 개수 조회")
    public SingleResult<Long> findPostScrapCountByPost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(postScrapService.findPostScrapCountByPost(postId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/postScrap/user/count")
    @ApiOperation(value = "사용자별 리뷰 스크랩 개수 조회")
    public SingleResult<Long> findPostScrapCountByUser(@PathVariable Long postScrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(postScrapService.findPostScrapCountByUser(uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/postScrap/{postScrapId}")
    @ApiOperation(value = "scrapId로 리뷰 스크랩 삭제")
    public CommonResult deletePostScrapById(@PathVariable Long postScrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        postScrapService.deletePostScrapById(uid, postScrapId);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/postScrap/post/{postId}")
    @ApiOperation(value = "사용자와 postId로 리뷰 스크랩 삭제")
    public CommonResult deletePostScrapByUserAndPost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        postScrapService.deletePostScrapByUserAndPost(uid, postId);
        return responseService.getSuccessResult();
    }

}
