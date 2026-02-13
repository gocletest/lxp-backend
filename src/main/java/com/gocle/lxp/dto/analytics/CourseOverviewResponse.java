package com.gocle.lxp.dto.analytics;

import lombok.Data;

@Data
public class CourseOverviewResponse {

    private Integer totalCourses;
    private Integer activeCourses;
    private Double avgProgress;
    private Integer todayLearners;

}
