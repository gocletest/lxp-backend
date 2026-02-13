package com.gocle.lxp.dto.analytics;

import lombok.Data;

@Data
public class CourseDetailResponse {

    private String courseName;
    private Long totalLearners;
    private Long completedCount;
    private Long todayActiveUsers;

}
