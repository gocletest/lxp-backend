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
    private final InstitutionDashboardService institutionDashboardService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${elasticsearch.index.learning-log}")
    private String index;

    /* =====================================================
       1. 플랫폼 전체 KPI
       ===================================================== */
    public Map<String, Object> getOverview() {
        return dashboardMapper.selectPlatformOverview();
    }

    /* =====================================================
       2. 기관별 Health 상태
       ===================================================== */
    public List<Map<String, Object>> getClientHealth() {
        return dashboardMapper.selectClientHealth();
    }

    /* =====================================================
       3. 실시간 이벤트 로그 (ES)
       ===================================================== */
    public List<Map<String, Object>> getRecentEvents(int size, String lastSeenAt) throws Exception {

        String rangeQuery = (lastSeenAt == null) ? "" : """
            "query": {
              "range": {
                "created_at": {
                  "gt": "%s"
                }
              }
            },
        """.formatted(lastSeenAt);

        String body = """
        {
          %s
          "size": %d,
          "sort": [
            { "created_at": { "order": "desc" } }
          ]
        }
        """.formatted(rangeQuery, size);

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
            row.put("user", s.path("actor_id").asText("UNKNOWN_ACTOR"));
            row.put("eventType", s.path("event_type").asText());
            row.put("courseId", s.path("external_course_id").asText(null));
            row.put("lessonId", s.path("external_lesson_id").asText(null));
            result.add(row);
        }

        return result;
    }

    /* =====================================================
       4. 관리자 - 기관별 과정 목록
       ===================================================== */
    public List<Map<String, Object>> getCoursesByClient(Long clientId) {
        return institutionDashboardService.getCourses(clientId);
    }

    /* =====================================================
       5. 관리자 - 과정 상세 KPI
       ===================================================== */
    public Map<String, Object> getCourseOverview(Long clientId, String courseId) {
        return dashboardMapper.selectCourseOverview(clientId, courseId);
    }

    /* =====================================================
       6. 관리자 - 과정 상세 이벤트 로그
       ===================================================== */
    public List<Map<String, Object>> getCourseEvents(Long clientId, String courseId, int size) {
        return dashboardMapper.selectCourseEvents(clientId, courseId, size);
    }
    
    public List<Map<String, Object>> getCourseTodayEventStats(Long clientId) {
        return dashboardMapper.selectCourseTodayEventStats(clientId);
    }
    
    public List<Map<String, Object>> getClientEventTrend7Days(Long clientId) {
        return dashboardMapper.selectClientEventTrend7Days(clientId);
    }
    
    /* 전일 대비 */
    public Map<String, Object> getTodayVsYesterday(Long clientId) {
        return dashboardMapper.selectTodayVsYesterday(clientId);
    }

    /* 최근 7일 추이 */
    public List<Map<String, Object>> get7DayTrend(Long clientId) {
        return dashboardMapper.select7DayTrendWithAvg(clientId);
    }

}
