package com.gocle.lxp.service;

import com.gocle.lxp.dto.apikey.ClientUsageStatResponse;
import com.gocle.lxp.dto.apikey.ClientUsageDailyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByEndpointResponse;
import com.gocle.lxp.dto.apikey.ClientTrafficAnomalyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByApiKeyResponse;
import com.gocle.lxp.mapper.ClientUsageStatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientUsageStatService {

    private final ClientUsageStatMapper mapper;

    /** Client 전체 통합 통계 */
    public List<ClientUsageStatResponse> getClientStats(
            LocalDateTime from, LocalDateTime to) {

        return mapper.countByClient(from, to);
    }

    /** Client + 일자별 통계 */
    public List<ClientUsageDailyResponse> getDailyStats(
            LocalDateTime from, LocalDateTime to) {

        return mapper.countDailyByClient(from, to);
    }

    /** Client + Endpoint별 통계 */
    public List<ClientUsageByEndpointResponse> getStatsByEndpoint(
            Long clientId, LocalDateTime from, LocalDateTime to) {

        return mapper.countByEndpoint(clientId, from, to);
    }

    /** Client + API Key별 통계 */
    public List<ClientUsageByApiKeyResponse> getStatsByApiKey(
            Long clientId, LocalDateTime from, LocalDateTime to) {

        return mapper.countByApiKey(clientId, from, to);
    }
    
    public List<ClientTrafficAnomalyResponse> detectAnomaly(
            LocalDateTime from,
            LocalDateTime to,
            double threshold) {

        return mapper.detectClientAnomaly(from, to, threshold);
    }



}
