package com.gocle.lxp.service;

import com.gocle.lxp.mapper.AdminDashboardMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final AdminDashboardMapper dashboardMapper;
    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${elasticsearch.index.learning-log}")
    private String index;

    /* =====================================================
       1. 플랫폼 전체 KPI (FACT + 운영 DB)
       ===================================================== */
    public Map<String, Object> getOverview() {
        return dashboardMapper.selectPlatformOverview();
    }

    /* =====================================================
       2. 기관별 Health 상태 (운영 + FACT)
       ===================================================== */
    public List<Map<String, Object>> getClientHealth() {
        return dashboardMapper.selectClientHealth();
    }

    /* =====================================================
       3. 실시간 이벤트 로그 (ES 전용)
       ===================================================== */
    public List<Map<String, Object>> getRecentEvents(int size) throws Exception {

        String body = """
        {
          "size": %d,
          "sort": [{ "created_at": "desc" }]
        }
        """.formatted(size);

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        JsonNode hits = objectMapper
                .readTree(restClient.performRequest(req).getEntity().getContent())
                .path("hits").path("hits");

        List<Map<String, Object>> result = new ArrayList<>();

        for (JsonNode h : hits) {
            JsonNode s = h.path("_source");
            Map<String, Object> row = new HashMap<>();
            row.put("time", s.path("created_at").asText());
            row.put("clientId", s.path("client_id").asLong());
            row.put("user", s.path("actor_id").asText());
            row.put("eventType", s.path("event_type").asText());
            row.put("courseId", s.path("external_course_id").asText(null));
            row.put("lessonId", s.path("external_lesson_id").asText(null));
            result.add(row);
        }

        return result;
    }
}
