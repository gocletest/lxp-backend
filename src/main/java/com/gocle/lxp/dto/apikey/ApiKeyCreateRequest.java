package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiKeyCreateRequest {

    private Long clientId;
    private String allowedDomains;
    private Integer rateLimitPerMin;
    private LocalDateTime expiresAt;
}
