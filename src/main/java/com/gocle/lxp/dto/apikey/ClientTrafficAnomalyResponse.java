package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientTrafficAnomalyResponse {

    private Long clientId;
    private String clientCode;
    private String clientName;

    private Long totalCount;
    private Double averageCount;
    private Double ratio;   // total / average
}
