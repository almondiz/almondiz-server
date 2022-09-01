package org.almondiz.almondiz.follow;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"Follow api"})
public class FollowController {

    private final FollowService followService;

    private final ResponseService responseService;

    @PostMapping("/follow")
    public CommonResult create(@RequestBody FollowRequestDto requestDto) {
        String email = "";

        return responseService.getSingleResult(followService.create(email, requestDto));
    }

    @DeleteMapping("/follow/{followId}")
    public CommonResult delete(@PathVariable Long followId) {
        String email = "";

        followService.delete(email, followId);

        return responseService.getSuccessResult();
    }

    @PatchMapping("/api/follow")
    public CommonResult setAlias(@RequestBody FollowRequestDto requestDto) {
        String email = "";

        followService.setAlias(email, requestDto.getFolloweeId(), requestDto.getAlias());

        return responseService.getSuccessResult();
    }

    @GetMapping("/api/followings")
    public CommonResult getAllFollowings() {
        String email = "";

        return responseService.getListResult(followService.findAllFollowings(email));
    }

    @GetMapping("/api/followers")
    public CommonResult getAllFollowers() {
        String email = "";

        return responseService.getListResult(followService.findAllFollowers(email));
    }
}
