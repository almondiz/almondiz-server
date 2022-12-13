package org.almondiz.almondiz.nut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.nut.dto.NutRequestDto;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.almondiz.almondiz.tag.entity.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"6. NUT API"})
public class NutController {

    private final NutService nutService;

    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/nut")
    @ApiOperation(value = "nut 작성")
    public SingleResult<Nut> createNut(@RequestBody NutRequestDto requestDto) {
        return responseService.getSingleResult(nutService.create(requestDto.getNutName()));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/nut/{nutName}")
    @ApiOperation(value = "nut명으로 조회")
    public SingleResult<Nut> findNutByName(@PathVariable String nutName) {
        return responseService.getSingleResult(nutService.getNutByNutName(nutName));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/nut/{nutId}")
    @ApiOperation(value = "nutId로 조회")
    public SingleResult<Nut> findNutById(@PathVariable Long nutId) {
        return responseService.getSingleResult(nutService.getNutById(nutId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/nut/{nutName}")
    @ApiOperation(value = "nut명으로 삭제")
    public CommonResult deleteNutById(@PathVariable String nutName) {
        nutService.delete(nutName);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/nut/{nutId}")
    @ApiOperation(value = "nutId로 삭제")
    public CommonResult deleteNutById(@PathVariable Long nutId) {
        nutService.deleteById(nutId);
        return responseService.getSuccessResult();
    }

}
