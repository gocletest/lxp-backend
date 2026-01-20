package com.gocle.lxp.service;

import com.gocle.lxp.dto.apikey.ApiKeyCreateRequest;
import com.gocle.lxp.dto.apikey.ApiKeyListResponse;
import com.gocle.lxp.dto.apikey.ApiKeyRotateSource;
import com.gocle.lxp.mapper.ApiKeyAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiKeyAdminService {

    private final ApiKeyAdminMapper apiKeyAdminMapper;

    /**
     * API Key 발급 (1회 노출)
     */
    public String issueApiKey(ApiKeyCreateRequest req) {

        String apiKey =
                UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().substring(0, 16);

        apiKeyAdminMapper.insertApiKey(
            req.getClientId(),
            apiKey,
            req.getAllowedDomains(),
            req.getRateLimitPerMin(),
            req.getExpiresAt()
        );
        return apiKey; // ⚠️ 반드시 1회만 반환
    }

    public void disableApiKey(Long apiKeyId) {
        apiKeyAdminMapper.disableApiKey(apiKeyId);
    }
    
    public List<ApiKeyListResponse> getApiKeyList() {

        List<ApiKeyListResponse> list =
                apiKeyAdminMapper.selectApiKeyList();

        // 🔐 마스킹 처리
        list.forEach(item ->
            item.setMaskedApiKey(maskApiKey(item.getMaskedApiKey()))
        );

        return list;
    }

    private String maskApiKey(String apiKey) {

        if (apiKey == null || apiKey.length() < 10) {
            return "****";
        }

        return apiKey.substring(0, 4)
                + "****"
                + apiKey.substring(apiKey.length() - 4);
    }
    
    /**
     * API Key 재발급 (rotate)
     */
    @Transactional
    public String rotateApiKey(Long apiKeyId) {

        // 1. 기존 설정 조회
        ApiKeyRotateSource source =
                apiKeyAdminMapper.selectForRotate(apiKeyId);

        if (source == null) {
            throw new IllegalStateException("Active API Key not found");
        }

        // 2. 기존 Key 비활성화
        apiKeyAdminMapper.disableApiKey(apiKeyId);

        // 3. 신규 Key 생성
        String newApiKey =
                UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().substring(0, 16);

        // 4. 신규 Key 저장 (기존 정책 유지)
        apiKeyAdminMapper.insertApiKey(
            source.getClientId(),
            newApiKey,
            source.getAllowedDomains(),
            source.getRateLimitPerMin(),
            source.getExpiresAt()
        );

        // 5. 평문 1회 반환
        return newApiKey;
    }
    
    public List<ApiKeyListResponse> getApiKeysByClient(Long clientId) {

        List<ApiKeyListResponse> list =
                apiKeyAdminMapper.selectApiKeysByClient(clientId);

        // 🔐 마스킹 처리
        list.forEach(item ->
            item.setMaskedApiKey(maskApiKey(item.getMaskedApiKey()))
        );

        return list;
    }
}
