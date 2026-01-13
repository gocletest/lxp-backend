package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUsageDailyResponse {

    private Long clientId;
    private String clientCode;
    private String clientName;

    private String date;     // yyyy-MM-dd
    private Long count;      // 해당 일자의 총 호출 수
}
