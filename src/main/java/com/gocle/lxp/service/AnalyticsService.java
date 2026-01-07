package com.gocle.lxp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocle.lxp.dto.analytics.FunnelDto;
import com.gocle.lxp.dto.analytics.RiskUserDto;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${elasticsearch.index.learning-log}")
    private String index;

    /* =========================
       1. Learning Funnel
       ========================= */
    public FunnelDto getFunnel() throws Exception {

        String body = """
        {
          "size": 0,
          "aggs": {
            "launched": { "filter": { "term": { "verb": "launched" } } },
            "started":  { "filter": { "term": { "verb": "started" } } },
            "completed":{ "filter": { "term": { "verb": "completed" } } }
          }
        }
        """;

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        var res = restClient.performRequest(req);
        JsonNode aggs = objectMapper.readTree(res.getEntity().getContent())
                .path("aggregations");

        long launched = aggs.path("launched").path("doc_count").asLong();
        long started  = aggs.path("started").path("doc_count").asLong();
        long completed= aggs.path("completed").path("doc_count").asLong();

        FunnelDto dto = new FunnelDto();
        dto.setLaunched(launched);
        dto.setStarted(started);
        dto.setCompleted(completed);

        FunnelDto.Conversion c = new FunnelDto.Conversion();
        c.setLaunchToStart(launched == 0 ? 0 : started * 100.0 / launched);
        c.setStartToComplete(started == 0 ? 0 : completed * 100.0 / started);
        dto.setConversion(c);

        return dto;
    }

    /* =========================
       2. Drop-off Risk Users
       ========================= */
    public List<RiskUserDto> getRiskUsers(int limit) throws Exception {

        String body = """
        {
          "size": 0,
          "query": {
            "range": { "created_at": { "gte": "now-30d/d" } }
          },
          "aggs": {
            "by_user": {
              "terms": { "field": "actor_id", "size": 100 },
              "aggs": {
                "started": { "filter": { "term": { "verb": "started" } } },
                "completed": { "filter": { "term": { "verb": "completed" } } },
                "last_activity": { "max": { "field": "created_at" } }
              }
            }
          }
        }
        """;

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        var res = restClient.performRequest(req);
        JsonNode buckets = objectMapper.readTree(res.getEntity().getContent())
                .path("aggregations").path("by_user").path("buckets");

        List<RiskUserDto> result = new ArrayList<>();
        Instant cutoff = Instant.now().minus(7, ChronoUnit.DAYS);

        for (JsonNode b : buckets) {
            long started = b.path("started").path("doc_count").asLong();
            long completed = b.path("completed").path("doc_count").asLong();
            String last = b.path("last_activity").path("value_as_string").asText(null);

            if (started > 0 && completed == 0 && last != null) {
                Instant lastTime = Instant.parse(last);
                if (lastTime.isBefore(cutoff)) {
                    result.add(new RiskUserDto(
                        b.path("key").asText(),
                        "HIGH",
                        "started but not completed",
                        last
                    ));
                }
            }

            if (result.size() >= limit) break;
        }

        return result;
    }
    
    public Map<String, Object> getOverview() throws Exception {

        String body = """
        {
          "size": 0,
          "aggs": {
            "total_users": {
              "cardinality": { "field": "actor_id" }
            },
            "total_courses": {
              "cardinality": { "field": "object_id" }
            },
            "active_today": {
              "filter": {
                "range": { "created_at": { "gte": "now/d" } }
              },
              "aggs": {
                "users": {
                  "cardinality": { "field": "actor_id" }
                }
              }
            },
            "learning_in_progress": {
              "terms": { "field": "actor_id", "size": 10000 },
              "aggs": {
                "started": { "filter": { "term": { "verb": "started" } } },
                "completed": { "filter": { "term": { "verb": "completed" } } }
              }
            }
          }
        }
        """;

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        var res = restClient.performRequest(req);
        JsonNode root = objectMapper.readTree(res.getEntity().getContent());
        JsonNode aggs = root.path("aggregations");

        long totalUsers = aggs.path("total_users").path("value").asLong();
        long totalCourses = aggs.path("total_courses").path("value").asLong();
        long activeToday = aggs.path("active_today").path("users").path("value").asLong();

        // learningInProgress 계산
        long inProgress = 0;
        for (JsonNode b : aggs.path("learning_in_progress").path("buckets")) {
            long started = b.path("started").path("doc_count").asLong();
            long completed = b.path("completed").path("doc_count").asLong();
            if (started > 0 && completed == 0) {
                inProgress++;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalUsers", totalUsers);
        result.put("activeToday", activeToday);
        result.put("totalCourses", totalCourses);
        result.put("learningInProgress", inProgress);

        return result;
    }

}
