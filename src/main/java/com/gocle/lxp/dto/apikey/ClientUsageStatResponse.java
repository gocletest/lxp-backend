package com.gocle.lxp.dto.apikey;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUsageStatResponse {

    private Long clientId;
    private String clientCode;
    private String clientName;

    private Long apiKeyCount;   // 해당 Client의 API Key 수
    private Long totalCount;    // 전체 호출 수
    
    private List<ClientUsageByApiKeyResponse> apiKeys;
}
