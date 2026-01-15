package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.dto.apikey.ApiClientCreateRequest;
import com.gocle.lxp.dto.apikey.ApiClientResponse;
import com.gocle.lxp.dto.apikey.ApiKeyListResponse;
import com.gocle.lxp.service.ApiClientService;
import com.gocle.lxp.service.ApiKeyAdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/clients")
@RequiredArgsConstructor
public class ClientAdminController {

    private final ApiClientService service;
    private final ApiKeyAdminService apiKeyAdminService;


    @GetMapping
    public ApiResponse<List<ApiClientResponse>> list() {
        return ApiResponse.success("Client list", service.getClients());
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody ApiClientCreateRequest req) {
        service.create(req);
        return ApiResponse.success("Client created", null);
    }

    @PatchMapping("/{clientId}")
    public ApiResponse<Void> update(
            @PathVariable("clientId") Long clientId,
            @RequestBody ApiClientCreateRequest req) {
        service.update(clientId, req);
        return ApiResponse.success("Client updated", null);
    }
    
    @PatchMapping("/{clientId}/code")
    public ApiResponse<Void> updateCode(
            @PathVariable("clientId") Long clientId,
            @RequestBody ApiClientCreateRequest req) {
        service.updateCode(clientId, req);
        return ApiResponse.success("Client updated", null);
    }

    @PatchMapping("/{clientId}/status")
    public ApiResponse<Void> changeStatus(
            @PathVariable("clientId") Long clientId,
            @RequestParam String status) {
        service.changeStatus(clientId, status);
        return ApiResponse.success("Client status changed", null);
    }
    
    @GetMapping("/{clientId}")
    public ApiResponse<ApiClientResponse> detail(@PathVariable("clientId") Long clientId) {
        return ApiResponse.success("Client detail",service.getClient(clientId));
    }
    
    @GetMapping("/{clientId}/apikeys")
    public ApiResponse<List<ApiKeyListResponse>> apiKeys(
            @PathVariable("clientId") Long clientId
    ) {
        return ApiResponse.success(
            "Client api keys",
            apiKeyAdminService.getApiKeysByClient(clientId)
        );
    }
}
