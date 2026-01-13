package com.gocle.lxp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

import com.gocle.lxp.dto.apikey.ApiKeyListResponse;
import com.gocle.lxp.dto.apikey.ApiKeyRotateSource;

@Mapper
public interface ApiKeyAdminMapper {

    int insertApiKey(
        @Param("clientId") Long clientId,
        @Param("apiKey") String apiKey,
        @Param("allowedDomains") String allowedDomains,
        @Param("rateLimitPerMin") Integer rateLimitPerMin,
        @Param("expiresAt") LocalDateTime expiresAt
    );

    int disableApiKey(@Param("apiKeyId") Long apiKeyId);
    
    List<ApiKeyListResponse> selectApiKeyList();
    
    ApiKeyRotateSource selectForRotate(@Param("apiKeyId") Long apiKeyId);
    
    List<ApiKeyListResponse> selectApiKeysByClient(
            @Param("clientId") Long clientId
    );
}
