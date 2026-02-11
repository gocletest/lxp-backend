 package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    /**
     * 통합 관리자 대시보드 Overview KPI
     */
    @GetMapping("/overview")
    public ApiResponse<?> overview() {
        return ApiResponse.success(dashboardService.getOverview());
    }

    /**
     * 기관별 Health 상태
     */
    @GetMapping("/clients")
    public ApiResponse<?> clients() {
        return ApiResponse.success(dashboardService.getClientHealth());
    }

    /**
     * 실시간 이벤트 로그 (Elasticsearch)
     */
    @GetMapping("/events")
    public ApiResponse<?> events(
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "lastSeenAt", required = false) String lastSeenAt
    ) throws Exception {
        return ApiResponse.success(
            dashboardService.getRecentEvents(size, lastSeenAt)
        );
    }
    
    /**
     * 🔥 관리자 - 기관별 과정 현황
     * GET /api/admin/dashboard/courses?clientId=3
     */
    @GetMapping("/courses")
    public ApiResponse<?> getCoursesByClient(
            @RequestParam("clientId") Long clientId
    ) {
        return ApiResponse.success(
        		dashboardService.getCoursesByClient(clientId)
        );
    }
    
    /**
     * 5️. 관리자 - 과정 상세 KPI
     * GET /api/admin/dashboard/course/overview?clientId=3&courseId=AI101
     */
    @GetMapping("/course-overview")
    public ApiResponse<?> getCourseOverview(
            @RequestParam("clientId") Long clientId,
            @RequestParam("courseId") String courseId
    ) {
        return ApiResponse.success(
                dashboardService.getCourseOverview(clientId, courseId)
        );
    }
    
    /**
     * 6️.관리자 - 과정 상세 이벤트 로그
     * GET /api/admin/dashboard/course/events?clientId=3&courseId=AI101
     */
    @GetMapping("/course-events")
    public ApiResponse<?> getCourseEvents(
            @RequestParam("clientId") Long clientId,
            @RequestParam("courseId") String courseId,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        return ApiResponse.success(
                dashboardService.getCourseEvents(clientId, courseId, size)
        );
    }
    
 
	
	 
	 @GetMapping("/trend/7days")
	 public ApiResponse<?> get7DayTrend(
	         @RequestParam("clientId") Long clientId
	 ) {
	     return ApiResponse.success(
	             dashboardService.get7DayTrend(clientId)
	     );
	 }

	 @GetMapping("/trend/compare")
	 public ApiResponse<?> getTodayVsYesterday(
	         @RequestParam("clientId") Long clientId
	 ) {
	     return ApiResponse.success(
	             dashboardService.getTodayVsYesterday(clientId)
	     );
	 }
    
	 
}
