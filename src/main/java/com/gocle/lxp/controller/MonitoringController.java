package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.security.AuthUtil;
import com.gocle.lxp.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;

    /**
     * 실시간 모니터링 데이터
     * - 관리자: clientId 파라미터 사용
     * - 기관관리자: 본인 기관 자동
     */
    @GetMapping("/realtime")
    public ApiResponse<?> getRealtime(
            @RequestParam(name = "clientId", required = false) Long clientId
    ) throws Exception {

        String role = AuthUtil.getRole();

        // 🔵 관리자
        if ("ADMIN".equals(role)) {

            if (clientId == null) {
                return ApiResponse.success(
                        monitoringService.getRealtimeAll()
                );
            }

            return ApiResponse.success(
                    monitoringService.getRealtimeData(clientId));
        }

        // 🟢 기관담당자
        Long myClientId = AuthUtil.getClientId();

        return ApiResponse.success(
                monitoringService.getRealtimeData(myClientId));
    }

}
