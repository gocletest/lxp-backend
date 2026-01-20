package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUsageDailyByEndpointResponse {

    private String date;      // yyyy-MM-dd
    private String endpoint;  // /api/lxp/events
    private Long count;       // 해당 일자 + endpoint 호출 수
}
