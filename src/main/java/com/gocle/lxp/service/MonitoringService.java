package com.gocle.lxp.service;

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
public class MonitoringService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${elasticsearch.index.learning-log}")
    private String index;

    /* ===================================================
       🔥 진입부 (관리자 / 기관 공통)
       =================================================== */

    public Map<String, Object> getRealtimeData(Long clientId) throws Exception {

        if (clientId == null) {
            return getRealtimeAll();
        }

        Map<String, Object> result = new HashMap<>();

        result.put("todayTotal", getTodayTotal(clientId));
        result.put("currentTps", getCurrentTps(clientId));
        result.put("traffic", getTrend5m(clientId));
        result.put("distribution", getEventTypeDistribution(clientId));

        return result;
    }

    public Map<String, Object> getRealtimeAll() throws Exception {

        Map<String, Object> result = new HashMap<>();

        result.put("todayTotal", getTodayTotal(null));
        result.put("currentTps", getCurrentTps(null));
        result.put("traffic", getTrend5m(null));
        result.put("distribution", getEventTypeDistribution(null));

        return result;
    }

    /* ===================================================
       1️⃣ 오늘 총 이벤트 (한국시간 기준)
       =================================================== */

    private long getTodayTotal(Long clientId) throws Exception {

        String filter = buildClientFilter(clientId);

        String body = """
        {
          "size": 0,
          "track_total_hits": true,
          "query": {
            "bool": {
              "must": [
                %s
                {
                  "range": {
                    "occurred_at": {
                      "gte": "now/d",
                      "time_zone": "Asia/Seoul"
                    }
                  }
                }
              ]
            }
          }
        }
        """.formatted(filter);

        return executeCount(body);
    }

    /* ===================================================
       2️⃣ 현재 TPS (최근 30초 기준)
       =================================================== */

    private double getCurrentTps(Long clientId) throws Exception {

        String filter = buildClientFilter(clientId);

        String body = """
        {
          "size": 0,
          "query": {
            "bool": {
              "must": [
                %s
                {
                  "range": {
                    "occurred_at": {
                      "gte": "now-30s"
                    }
                  }
                }
              ]
            }
          }
        }
        """.formatted(filter);

        long count = executeCount(body);

        return count / 30.0;
    }

    /* ===================================================
       3️⃣ 최근 5분 추이 (한국시간 고정)
       =================================================== */

    private List<Map<String, Object>> getTrend5m(Long clientId) throws Exception {

        String filter = buildClientFilter(clientId);

        String body = """
        {
          "size": 0,
          "query": {
            "bool": {
              "must": [
                %s
                {
                  "range": {
                    "occurred_at": {
                      "gte": "now-5m"
                    }
                  }
                }
              ]
            }
          },
          "aggs": {
            "per_10s": {
              "date_histogram": {
                "field": "occurred_at",
                "fixed_interval": "10s",
                "time_zone": "Asia/Seoul",
                "min_doc_count": 0
              }
            }
          }
        }
        """.formatted(filter);

        JsonNode buckets = executeAgg(body, "per_10s");

        List<Map<String, Object>> list = new ArrayList<>();

        for (JsonNode bucket : buckets) {
            Map<String, Object> row = new HashMap<>();
            row.put("time", bucket.path("key_as_string").asText());
            row.put("count", bucket.path("doc_count").asLong());
            list.add(row);
        }

        return list;
    }

    /* ===================================================
       4️⃣ 최근 5분 이벤트 분포
       =================================================== */

    private List<Map<String, Object>> getEventTypeDistribution(Long clientId) throws Exception {

        String filter = buildClientFilter(clientId);

        String body = """
        {
          "size": 0,
          "query": {
            "bool": {
              "must": [
                %s
                {
                  "range": {
                    "occurred_at": {
                      "gte": "now-5m"
                    }
                  }
                }
              ]
            }
          },
          "aggs": {
            "event_types": {
              "terms": {
                "field": "event_type.keyword",
                "size": 10
              }
            }
          }
        }
        """.formatted(filter);

        JsonNode buckets = executeAgg(body, "event_types");

        List<Map<String, Object>> list = new ArrayList<>();

        for (JsonNode bucket : buckets) {
            Map<String, Object> row = new HashMap<>();
            row.put("type", bucket.path("key").asText());
            row.put("count", bucket.path("doc_count").asLong());
            list.add(row);
        }

        return list;
    }

    /* ===================================================
       🔹 공통 유틸
       =================================================== */

    private String buildClientFilter(Long clientId) {

        if (clientId == null) {
            return "";
        }

        return """
            { "term": { "client_id": %d } },
        """.formatted(clientId);
    }

    private long executeCount(String body) throws Exception {

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        JsonNode root = objectMapper
                .readTree(restClient.performRequest(req).getEntity().getContent());

        return root.path("hits").path("total").path("value").asLong(0);
    }

    private JsonNode executeAgg(String body, String aggName) throws Exception {

        Request req = new Request("POST", "/" + index + "/_search");
        req.setJsonEntity(body);

        return objectMapper
                .readTree(restClient.performRequest(req).getEntity().getContent())
                .path("aggregations")
                .path(aggName)
                .path("buckets");
    }
}
