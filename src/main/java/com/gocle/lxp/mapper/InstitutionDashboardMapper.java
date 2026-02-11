package com.gocle.lxp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gocle.lxp.dto.EventDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface InstitutionDashboardMapper {

    Map<String, Object> selectInstitutionOverview(
            @Param("clientId") Long clientId
    );

    List<Map<String, Object>> selectInstitutionCourses(
            @Param("clientId") Long clientId
    );
    

    List<Map<String, Object>> selectCourseEvents(
            @Param("clientId") Long clientId,
            @Param("courseId") String courseId
        );
}
