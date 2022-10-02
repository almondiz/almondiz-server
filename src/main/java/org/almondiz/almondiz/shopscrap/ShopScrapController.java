package org.almondiz.almondiz.shopscrap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
@Api(tags = {"n. Shop SCRAP API"})
public class ShopScrapController {

    private final ResponseService responseService;

    private final ShopScrapService shopScrapService;

    @GetMapping(value = "/shopScrap/{shopScrapId}")
    @ApiOperation(value = "업체 스크랩 단건 조회")
    public CommonResult getShopById(@PathVariable Long shopScrapId) {
        return responseService.getSingleResult(shopScrapService.getShopScrapById(shopScrapId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/shopScrap/{shopId}/user")
    @ApiOperation(value = "사용자별 업체별 업체 스크랩 조회")
    public CommonResult getShopScrapByUserAndShop(@PathVariable Long shopId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(shopScrapService.getShopScrapByUserAndShop(uid, shopId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/shopScrap/{shopId}/isScrap")
    @ApiOperation(value = "사용자별 업체별 업체 스크랩 유무 조회")
    public CommonResult isScrapByUserAndShop(@PathVariable Long shopId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        boolean isScrap = shopScrapService.isScrapByUserAndShop(uid, shopId);

        if(!isScrap) {
            return responseService.getFailResult();
        }

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/shopScraps/user")
    @ApiOperation(value = "사용자별 업체 스크랩 목록 조회")
    public CommonResult getAllShopScrapByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getListResult(shopScrapService.getAllShopScrapByUser(uid));
    }

    @GetMapping(value = "/shopScraps/{shopId}")
    @ApiOperation(value = "업체별 업체 스크랩 목록 조회")
    public CommonResult getAllShopScrapByUser(@PathVariable Long shopId) {
        return responseService.getListResult(shopScrapService.getAllShopScrapByShop(shopId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/shopScrap/{shopId}/user")
    @ApiOperation(value = "업체 스크랩 생성")
    public CommonResult create(@PathVariable Long shopId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(shopScrapService.create(uid, shopId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/shopScrap/{shopScrapId}")
    @ApiOperation(value = "스크랩 Id로 업체 스크랩 삭제")
    public CommonResult deleteByScrapId(@PathVariable Long shopScrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        shopScrapService.deleteById(uid, shopScrapId);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/shopScrap/{shopId}")
    @ApiOperation(value = "업체 Id로 업체 스크랩 삭제")
    public CommonResult deleteByShopId(@PathVariable Long shopId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        shopScrapService.deleteByUserAndShop(uid,shopId);

        return responseService.getSuccessResult();
    }
}
