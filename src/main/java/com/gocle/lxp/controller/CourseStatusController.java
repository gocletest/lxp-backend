package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.analytics.CourseDetailResponse;
import com.gocle.lxp.dto.analytics.CourseOverviewResponse;
import com.gocle.lxp.dto.analytics.CourseStatusResponse;
import com.gocle.lxp.security.AuthUtil;
import com.gocle.lxp.service.CourseStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-status")
@RequiredArgsConstructor
public class CourseStatusController {

    private final CourseStatusService courseStatusService;

    /**
     * 과정 KPI
     * - ADMIN: clientId 선택 가능
     * - INSTITUTION: 본인 기관 자동
     */
    @GetMapping("/overview")
    public ApiResponse<CourseOverviewResponse> getOverview(
            @RequestParam(name = "clientId", required = false) Long clientId
    ) throws Exception {

        String role = AuthUtil.getRole();

        // 🔵 관리자
        if ("ADMIN".equals(role)) {

            if (clientId == null) {
                return ApiResponse.success(
                        courseStatusService.getOverviewAll()
                );
            }

            return ApiResponse.success(
                    courseStatusService.getOverview(clientId)
            );
        }

        // 🟢 기관담당자
        Long myClientId = AuthUtil.getClientId();

        return ApiResponse.success(
                courseStatusService.getOverview(myClientId)
        );
    }

    /**
     * 과정 리스트
     */
    @GetMapping("/list")
    public ApiResponse<List<CourseStatusResponse>> getCourseList(
            @RequestParam(name = "clientId", required = false) Long clientId
    ) throws Exception {

        String role = AuthUtil.getRole();

        if ("ADMIN".equals(role)) {

            if (clientId == null) {
                return ApiResponse.success(
                        courseStatusService.getCourseListAll()
                );
            }

            return ApiResponse.success(
                    courseStatusService.getCourseList(clientId)
            );
        }

        Long myClientId = AuthUtil.getClientId();

        return ApiResponse.success(
                courseStatusService.getCourseList(myClientId)
        );
    }
    
    @GetMapping("/{courseId}")
    public ApiResponse<CourseDetailResponse> getCourseDetail(
            @PathVariable("courseId") Long courseId
    ) throws Exception {

        return ApiResponse.success(
                courseStatusService.getCourseDetail(courseId)
        );
    }
}
