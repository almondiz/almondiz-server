package org.almondiz.almondiz.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.category.entity.Category;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
@Api(tags = {"n. CATEGORY API"})
public class CategoryController {

    private final ResponseService responseService;

    private final CategoryService categoryService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/category/{categoryName}")
    @ApiOperation(value = "업체 카테고리 생성")
    public SingleResult<Category> createCategory(@PathVariable String categoryName) {
        return responseService.getSingleResult(categoryService.create(categoryName));
    }
}
