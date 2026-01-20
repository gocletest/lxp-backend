package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/funnel")
    public ApiResponse<?> funnel() throws Exception {
        return ApiResponse.success(analyticsService.getFunnel());
    }

    @GetMapping("/risk-users")
    public ApiResponse<?> riskUsers(
            @RequestParam(defaultValue = "5") int limit
    ) throws Exception {
        return ApiResponse.success(analyticsService.getRiskUsers(limit));
    }
    
    @GetMapping("/overview")
    public ApiResponse<?> overview() throws Exception {
        return ApiResponse.success(analyticsService.getOverview());
    }
}
