package com.gocle.lxp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDashboardMapper {

	/* ============================================
    	1. 플랫폼 전체 KPI
    ============================================ */
	 Map<String, Object> selectPlatformOverview();
	
	 /* ============================================
	    2. 기관 Health
	    ============================================ */
	 List<Map<String, Object>> selectClientHealth();
	
	 /* ============================================
	    3. 관리자 - 과정 상세 KPI
	    ============================================ */
	 Map<String, Object> selectCourseOverview(
	         @Param("clientId") Long clientId,
	         @Param("courseId") String courseId
	 );
	
	 /* ============================================
	    4. 관리자 - 과정 이벤트 로그
	    ============================================ */
	 List<Map<String, Object>> selectCourseEvents(
	         @Param("clientId") Long clientId,
	         @Param("courseId") String courseId,
	         @Param("size") int size
	 );
	 
	 List<Map<String, Object>> selectCourseTodayEventStats(
	         @Param("clientId") Long clientId
	 );
	
	 
	 List<Map<String, Object>> selectClientEventTrend7Days(
	         @Param("clientId") Long clientId
	 );
	 
	 List<Map<String, Object>> select7DayTrendWithAvg(Long clientId);

	 Map<String, Object> selectTodayVsYesterday(Long clientId);

	
}
