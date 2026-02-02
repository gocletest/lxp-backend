package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping("/overview")
    public ApiResponse<?> overview() {
        return ApiResponse.success(dashboardService.getOverview());
    }

    @GetMapping("/clients")
    public ApiResponse<?> clients() {
        return ApiResponse.success(dashboardService.getClientHealth());
    }

    @GetMapping("/events")
    public ApiResponse<?> events(
            @RequestParam(defaultValue = "20") int size
    ) throws Exception {
        return ApiResponse.success(dashboardService.getRecentEvents(size));
    }
}

