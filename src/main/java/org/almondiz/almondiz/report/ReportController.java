package org.almondiz.almondiz.report;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.response.ListResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
@Api(tags = {"5. POST REPORT API"})
public class ReportController {

    private final ReportService reportService;

    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping(value = "/post/report")
    @ApiOperation(value = "post 신고 작성")
    public SingleResult<Report> createReport(@RequestBody ReportRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(reportService.create(uid, requestDto));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/reports")
    @ApiOperation(value = "모든 신고 목록 조회")
    public ListResult<Report> findAllReport() {
        return responseService.getListResult(reportService.findAllReport());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/reports/user")
    @ApiOperation(value = "사용자별 모든 신고 목록 조회")
    public ListResult<Report> findAllReportByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getListResult(reportService.findAllReportByUser(uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/reports/user/{userId}")
    @ApiOperation(value = "사용자id별 모든 신고 목록 조회")
    public ListResult<Report> findAllReportByUserId(@PathVariable Long userId) {
        return responseService.getListResult(reportService.findAllReportByUserId(userId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/reports/user/count")
    @ApiOperation(value = "사용자별 모든 신고 개수 조회")
    public SingleResult<Long> findReportCountByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return responseService.getSingleResult(reportService.findReportCountByUser(uid));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/reports/user/{userId}/count")
    @ApiOperation(value = "사용자id별 모든 신고 개수 조회")
    public SingleResult<Long> findReportCountByUserId(@PathVariable Long userId) {
        return responseService.getSingleResult(reportService.findReportCountByUser(userId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/{postId}/reports")
    @ApiOperation(value = "리뷰별 모든 신고 목록 조회")
    public ListResult<Report> findAllReportByPost(@PathVariable Long postId) {
        return responseService.getListResult(reportService.findAllReportByPost(postId));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/post/{postId}/reports/count")
    @ApiOperation(value = "리뷰별 모든 신고 개수 조회")
    public SingleResult<Long> findReportCountByPost(@PathVariable Long postId) {
        return responseService.getSingleResult(reportService.findReportCountByPost(postId));
    }
}
