package org.almondiz.almondiz.shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.shop.entity.ShopResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"9. SHOP API"})
public class ShopController {

    private final ShopService shopService;

    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/shop")
    @ApiOperation(value = "shop 조회")
    public ListResult<ShopResponseDto> getAllShop() {
        return responseService.getListResult(shopService.getAllShops());
    }

}
