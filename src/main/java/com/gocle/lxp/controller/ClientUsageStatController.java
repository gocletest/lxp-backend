package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.apikey.ClientUsageStatResponse;
import com.gocle.lxp.dto.apikey.ClientUsageDailyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByEndpointResponse;
import com.gocle.lxp.dto.apikey.ClientTrafficAnomalyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByApiKeyResponse;
import com.gocle.lxp.service.ClientUsageStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/clients")
@RequiredArgsConstructor
public class ClientUsageStatController {

    private final ClientUsageStatService service;

    /**
     * ✅ Client 단위 통합 사용량 통계 (기존 유지)
     * GET /api/admin/clients/stats
     */
    @GetMapping("/stats")
    public ApiResponse<List<ClientUsageStatResponse>> stats(
            @RequestParam(name = "from") OffsetDateTime from,
            @RequestParam(name = "to") OffsetDateTime to
    ) {
        return ApiResponse.success(
            "Client usage stats",
            service.getClientStats(from, to)
        );
    }

    /**
     * ✅ Client + 일자별 사용량 통계 (차트용)
     * GET /api/admin/clients/stats/daily
     */
    @GetMapping("/stats/daily")
    public ApiResponse<List<ClientUsageDailyResponse>> daily(
            @RequestParam(name = "from") OffsetDateTime from,
            @RequestParam(name = "to") OffsetDateTime to
    ) {
        return ApiResponse.success(
            "Client daily usage stats",
            service.getDailyStats(from, to)
        );
    }

    /**
     * ✅ 특정 Client의 Endpoint별 사용량 통계
     * GET /api/admin/clients/{clientId}/stats/endpoints
     */
    @GetMapping("/{clientId}/stats/endpoints")
    public ApiResponse<List<ClientUsageByEndpointResponse>> byEndpoint(
            @PathVariable("clientId") Long clientId,
            @RequestParam(name = "from") OffsetDateTime from,
            @RequestParam(name = "to") OffsetDateTime to
    ) {
        return ApiResponse.success(
            "Client usage by endpoint",
            service.getStatsByEndpoint(clientId, from, to)
        );
    }

    /**
     * ✅ 특정 Client의 API Key별 사용량 통계
     * GET /api/admin/clients/{clientId}/stats/apikeys
     */
    @GetMapping("/{clientId}/stats/apikeys")
    public ApiResponse<List<ClientUsageByApiKeyResponse>> byApiKey(
            @PathVariable("clientId") Long clientId,
            @RequestParam(name = "from") OffsetDateTime from,
            @RequestParam(name = "to") OffsetDateTime to
    ) {
        return ApiResponse.success(
            "Client usage by api key",
            service.getStatsByApiKey(clientId, from, to)
        );
    }
    
    /**
     * Client 이상 트래픽 감지
     * GET /api/admin/clients/stats/anomaly
     */
    @GetMapping("/stats/anomaly")
    public ApiResponse<List<ClientTrafficAnomalyResponse>> anomaly(
            @RequestParam OffsetDateTime from,
            @RequestParam OffsetDateTime to,
            @RequestParam(defaultValue = "3") double threshold
    ) {
        return ApiResponse.success(
            "Client traffic anomaly",
            service.detectAnomaly(from, to, threshold)
        );
    }

}
