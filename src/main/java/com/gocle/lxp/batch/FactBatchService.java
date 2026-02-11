package com.gocle.lxp.batch;

import com.gocle.lxp.mapper.FactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FactBatchService {

    private final FactMapper factMapper;

    /**
     * FACT 일괄 배치
     * - 보통 하루 1회 (D-1 기준)
     */
    @Transactional
    public void runDailyFactBatch(Long clientId) {

        //String statDate = LocalDate.now().minusDays(1).toString();
        
        String statDate = LocalDate.now().toString();
        
        factMapper.upsertEventDaily(statDate, clientId);
        factMapper.upsertCourseDaily(statDate, clientId);
        factMapper.upsertUserSummary(clientId);
        factMapper.upsertLessonProgress(clientId);
    }
}
