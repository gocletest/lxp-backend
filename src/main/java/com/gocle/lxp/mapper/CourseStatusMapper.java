package com.gocle.lxp.mapper;

import com.gocle.lxp.dto.analytics.CourseDetailResponse;
import com.gocle.lxp.dto.analytics.CourseOverviewResponse;
import com.gocle.lxp.dto.analytics.CourseStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseStatusMapper {

    CourseOverviewResponse selectOverview(@Param("clientId") Long clientId);

    CourseOverviewResponse selectOverviewAll();

    List<CourseStatusResponse> selectCourseList(@Param("clientId") Long clientId);

    List<CourseStatusResponse> selectCourseListAll();
    
    CourseDetailResponse selectCourseDetail(@Param("courseId") Long courseId);

}
