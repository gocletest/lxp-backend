package com.gocle.lxp.mapper;

import com.gocle.lxp.dto.apikey.ClientUsageStatResponse;
import com.gocle.lxp.dto.apikey.ClientUsageDailyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByEndpointResponse;
import com.gocle.lxp.dto.apikey.ClientUsageDailyByEndpointResponse;
import com.gocle.lxp.dto.apikey.ClientTrafficAnomalyResponse;
import com.gocle.lxp.dto.apikey.ClientUsageByApiKeyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface ClientUsageStatMapper {

    List<ClientUsageStatResponse> countByClient(
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to
    );

    List<ClientUsageDailyResponse> countDailyByClient(
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to
    );

    List<ClientUsageByEndpointResponse> countByEndpoint(
        @Param("clientId") Long clientId,
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to
    );

    List<ClientUsageByApiKeyResponse> countByApiKey(
        @Param("clientId") Long clientId,
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to
    );
    
    // Client의 API Key 목록 + 사용량
    List<ClientUsageByApiKeyResponse> selectClientApiKeysWithUsage(
        @Param("clientId") Long clientId,
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to
    );
    
    List<ClientTrafficAnomalyResponse> detectClientAnomaly(
	    @Param("from") OffsetDateTime from,
	    @Param("to") OffsetDateTime to,
	    @Param("threshold") double threshold
	);
    
    List<ClientUsageDailyByEndpointResponse> countDailyByEndpoint(
	    @Param("clientId") Long clientId,
	    @Param("from") OffsetDateTime from,
	    @Param("to") OffsetDateTime to
	);

}

