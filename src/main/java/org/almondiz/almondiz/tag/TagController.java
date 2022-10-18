package org.almondiz.almondiz.tag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.almondiz.almondiz.tag.dto.TagRequestDto;
import org.almondiz.almondiz.tag.entity.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"7. TAG API"})
public class TagController {

    private final TagService tagService;

    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/tag")
    @ApiOperation(value = "tag 작성")
    public SingleResult<Tag> createTag(@RequestBody TagRequestDto requestDto) {
        return responseService.getSingleResult(tagService.create(requestDto.getTagName()));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/tag/{tagName}")
    @ApiOperation(value = "tag명으로 조회")
    public SingleResult<Tag> findTagByName(@PathVariable String tagName) {
        return responseService.getSingleResult(tagService.getTagByTagName(tagName));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/tag/{tagId}")
    @ApiOperation(value = "tagId로 조회")
    public SingleResult<Tag> findTagById(@PathVariable Long tagId) {
        return responseService.getSingleResult(tagService.getTagById(tagId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/tag/{tagName}")
    @ApiOperation(value = "tag명으로 삭제")
    public CommonResult deleteTagById(@PathVariable String tagName) {
        tagService.delete(tagName);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/tag/{tagId}")
    @ApiOperation(value = "tagId로 삭제")
    public CommonResult deleteTagById(@PathVariable Long tagId) {
        tagService.deleteById(tagId);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/tag/like/{tagName}")
    @ApiOperation(value = "tagName이 들어간 TagList 조회")
    public ListResult<Tag> findTagNameLike(@PathVariable String tagName) {
        return responseService.getListResult(tagService.findByTagNameLike(tagName));
    }
}
