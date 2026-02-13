package com.gocle.lxp.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gocle.lxp.service.ApiClientService;

@Component
@RequiredArgsConstructor
@Slf4j
public class FactBatchScheduler {

    private final FactBatchService factBatchService;
    private final ApiClientService apiClientService;

    /**
     * FACT 배치
     * - 하루 1회
     * - 새벽 02:00
     */
    //@Scheduled(cron = "0 0 2 * * *")
    
    /**
     * FACT 배치
     * - 5분 주기 실행 (검증용)
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void run() {

        log.info("[FACT-Scheduler] Daily FACT batch started");

        try {
            List<Long> clientIds = apiClientService.selectActiveClientIds();

            for (Long clientId : clientIds) {
                try {
                	log.info("[FACT-Scheduler] Running batch for clientId={}", clientId);
                    factBatchService.runDailyFactBatch(clientId);
                } catch (Exception e) {
                    log.error("Client {} batch failed", clientId, e);
                }
            }

        } catch (Exception e) {
            log.error("[FACT-Scheduler] FACT batch failed", e);
        }

        log.info("[FACT-Scheduler] Daily FACT batch finished");
    }
}
