package com.gocle.lxp.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskUserDto {
    private String actorId;
    private String risk;
    private String reason;
    private String lastActivity;
}
