package com.gocle.lxp.service;

import com.gocle.lxp.dto.analytics.CourseDetailResponse;
import com.gocle.lxp.dto.analytics.CourseOverviewResponse;
import com.gocle.lxp.dto.analytics.CourseStatusResponse;
import com.gocle.lxp.mapper.CourseStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseStatusService {

    private final CourseStatusMapper mapper;

    public CourseOverviewResponse getOverview(Long clientId) {
        return mapper.selectOverview(clientId);
    }

    public CourseOverviewResponse getOverviewAll() {
        return mapper.selectOverviewAll();
    }

    public List<CourseStatusResponse> getCourseList(Long clientId) {
        return mapper.selectCourseList(clientId);
    }

    public List<CourseStatusResponse> getCourseListAll() {
        return mapper.selectCourseListAll();
    }
    
    public CourseDetailResponse getCourseDetail(Long courseId) {
        return mapper.selectCourseDetail(courseId);
    }
}
