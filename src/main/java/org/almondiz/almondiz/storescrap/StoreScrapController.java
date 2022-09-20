package org.almondiz.almondiz.storescrap;

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
@Api(tags = {"n. STORE SCRAP API"})
public class StoreScrapController {

    private final ResponseService responseService;

    private final StoreScrapService storeScrapService;

    @GetMapping(value = "/storeScrap/{storeScrapId}")
    @ApiOperation(value = "업체 스크랩 단건 조회")
    public CommonResult getScrapById(@PathVariable Long storeScrapId) {
        return responseService.getSingleResult(storeScrapService.getStoreScrapById(storeScrapId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/storeScrap/{storeId}/user")
    @ApiOperation(value = "사용자별 업체별 업체 스크랩 조회")
    public CommonResult getStoreScrapByUserAndStore(@PathVariable Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getSingleResult(storeScrapService.getStoreScrapByUserAndStore(email, storeId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/storeScrap/{storeId}/isScrap")
    @ApiOperation(value = "사용자별 업체별 업체 스크랩 유무 조회")
    public CommonResult isScrapByUserAndStore(@PathVariable Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        boolean isScrap = storeScrapService.isScrapByUserAndStore(email, storeId);

        if(!isScrap) {
            return responseService.getFailResult();
        }

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/storeScraps/user")
    @ApiOperation(value = "사용자별 업체 스크랩 목록 조회")
    public CommonResult getAllStoreScrapByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getListResult(storeScrapService.getAllStoreScrapByUser(email));
    }

    @GetMapping(value = "/storeScraps/{storeId}")
    @ApiOperation(value = "업체별 업체 스크랩 목록 조회")
    public CommonResult getAllStoreScrapByUser(@PathVariable Long storeId) {
        return responseService.getListResult(storeScrapService.getAllStoreScrapByStore(storeId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/storeScrap/{storeId}/user")
    @ApiOperation(value = "업체 스크랩 생성")
    public CommonResult create(@PathVariable Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getSingleResult(storeScrapService.create(email, storeId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/storeScrap/{storeScrapId}")
    @ApiOperation(value = "스크랩 Id로 업체 스크랩 삭제")
    public CommonResult deleteByScrapId(@PathVariable Long storeScrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        storeScrapService.deleteById(email, storeScrapId);

        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/storeScrap/{storeId}")
    @ApiOperation(value = "업체 Id로 업체 스크랩 삭제")
    public CommonResult deleteByStoreId(@PathVariable Long storeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        storeScrapService.deleteByUserAndStore(email,storeId);

        return responseService.getSuccessResult();
    }
}
