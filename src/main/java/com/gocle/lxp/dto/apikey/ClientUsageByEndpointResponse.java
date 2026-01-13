package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientUsageByEndpointResponse {

    private Long clientId;
    private String endpoint;
    private Long count;
}
