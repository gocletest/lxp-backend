package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.apikey.ApiKeyCreateRequest;
import com.gocle.lxp.dto.apikey.ApiKeyListResponse;
import com.gocle.lxp.service.ApiKeyAdminService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/apikeys")
@RequiredArgsConstructor
public class ApiKeyAdminController {

    private final ApiKeyAdminService apiKeyAdminService;

    /**
     * API Key 발급
     */
    @PostMapping
    public ApiResponse<String> issue(@RequestBody ApiKeyCreateRequest req) {
        return ApiResponse.success(
            "API Key issued",
            apiKeyAdminService.issueApiKey(req)
        );
    }

    /**
     * API Key 비활성화
     */
    @PatchMapping("/{apiKeyId}/disable")
    public ApiResponse<Void> disable(@PathVariable Long apiKeyId) {
        apiKeyAdminService.disableApiKey(apiKeyId);
        return ApiResponse.success(
            "API Key disabled",
            null
        );
    }
    
    @GetMapping
    public ApiResponse<List<ApiKeyListResponse>> list() {
        return ApiResponse.success(
            "API Key list",
            apiKeyAdminService.getApiKeyList()
        );
    }
    
    @PatchMapping("/{apiKeyId}/rotate")
    public ApiResponse<String> rotate(@PathVariable Long apiKeyId) {
        return ApiResponse.success(
            "API Key rotated",
            apiKeyAdminService.rotateApiKey(apiKeyId)
        );
    }

}
