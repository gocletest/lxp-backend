package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiClientCreateRequest {
    private String clientCode;
    private String clientName;
}
