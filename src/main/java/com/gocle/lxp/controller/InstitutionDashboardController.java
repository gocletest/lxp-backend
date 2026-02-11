package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.EventDto;
import com.gocle.lxp.security.AuthUtil;
import com.gocle.lxp.service.InstitutionDashboardService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/institution/dashboard")
@RequiredArgsConstructor
public class InstitutionDashboardController {

    private final InstitutionDashboardService service;

    /**
     * 기관 대시보드 Overview
     */
    @GetMapping("/overview")
    public ApiResponse<?> overview() {
        return ApiResponse.success(service.getOverview());
    }

    /**
     * 기관 과정 목록
     */
    @GetMapping("/courses")
    public ApiResponse<?> courses() {
        return ApiResponse.success(service.getCourses());
    }

    /**
     * 🔥 과정별 최근 이벤트
     * GET /api/institution/dashboard/courses/{courseId}/events
     */
    @GetMapping("/courses/{courseId}/events")
    public ApiResponse<List<Map<String, Object>>> getEvents(
            @PathVariable("courseId") String courseId
    ) {
        Long clientId = AuthUtil.getClientId();
        return ApiResponse.success(
                service.getEvents(clientId, courseId)
        );
    }
}

