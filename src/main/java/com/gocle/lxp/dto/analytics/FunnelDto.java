package com.gocle.lxp.dto.analytics;

import lombok.Data;

@Data
public class FunnelDto {

    private long launched;
    private long started;
    private long completed;

    private Conversion conversion;

    @Data
    public static class Conversion {
        private double launchToStart;
        private double startToComplete;
    }
}
