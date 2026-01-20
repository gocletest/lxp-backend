package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientUsageByApiKeyResponse {

    private Long apiKeyId;
    private String apiKey;
    private String endpoint;
    private Long count;
}
