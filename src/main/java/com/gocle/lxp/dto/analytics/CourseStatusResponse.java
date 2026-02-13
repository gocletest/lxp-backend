package com.gocle.lxp.dto.analytics;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseStatusResponse {

    private Long id;
    private String courseName;
    private Integer totalLearnerCount;
    private Integer todayLearnerCount;
    private Double avgProgress;
    private LocalDate startDate;
    private LocalDate endDate;

}
