package com.gocle.lxp.mapper;

import com.gocle.lxp.dto.apikey.ClientUsageStatResponse;
import com.gocle.lxp.dto.apikey.ClientUsageDailyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByEndpointResponse;
import com.gocle.lxp.dto.apikey.ClientTrafficAnomalyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByApiKeyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ClientUsageStatMapper {

    List<ClientUsageStatResponse> countByClient(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    List<ClientUsageDailyResponse> countDailyByClient(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    List<ClientUsageByEndpointResponse> countByEndpoint(
        @Param("clientId") Long clientId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    List<ClientUsageByApiKeyResponse> countByApiKey(
        @Param("clientId") Long clientId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
    
    // Client의 API Key 목록 + 사용량
    List<ClientUsageByApiKeyResponse> selectClientApiKeysWithUsage(
        @Param("clientId") Long clientId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
    
    List<ClientTrafficAnomalyResponse> detectClientAnomaly(
	    @Param("from") LocalDateTime from,
	    @Param("to") LocalDateTime to
	);

}

