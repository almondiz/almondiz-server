package org.almondiz.almondiz.notification;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class NotificationController {

    private final ResponseService responseService;

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}/notifications")
    public CommonResult findByUsers() {
        // 임시
        String email = "";

        return  responseService.getListResult(notificationService.findAllByUser(email));
    }

    @PatchMapping("/user/{userId}/notification/{notId}")
    public CommonResult readNotification(@PathVariable Long notId){
        // 임시
        String email = "";

        notificationService.read(email, notId);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/user/{userId}/notification/{notId}")
    public CommonResult delete(@PathVariable Long notId) {
        // 임시
        String email = "";

        notificationService.delete(email, notId);
        return responseService.getSuccessResult();
    }
}
