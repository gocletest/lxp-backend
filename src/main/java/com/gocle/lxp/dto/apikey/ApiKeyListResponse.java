package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiKeyListResponse {

    private Long apiKeyId;
    private String maskedApiKey;

    private Long clientId;
    private String clientCode;
    private String clientName;

    private Integer enabled;
    private Integer rateLimitPerMin;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
