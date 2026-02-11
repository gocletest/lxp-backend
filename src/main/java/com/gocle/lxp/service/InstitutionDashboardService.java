package com.gocle.lxp.service;

import com.gocle.lxp.dto.EventDto;
import com.gocle.lxp.mapper.InstitutionDashboardMapper;
import com.gocle.lxp.security.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InstitutionDashboardService {

    private final InstitutionDashboardMapper dashboardMapper;

    /* ============================================
    	기관 Overview
    ============================================ */
	 public Map<String, Object> getOverview() {
	     Long clientId = AuthUtil.getClientId();
	     return dashboardMapper.selectInstitutionOverview(clientId);
	 }
	
	 /* ============================================
	    기관 과정 목록 (관리자 재사용)
	    ============================================ */
	 public List<Map<String, Object>> getCourses(Long clientId) {
	     return dashboardMapper.selectInstitutionCourses(clientId);
	 }
	
	 /* ============================================
	    기관 로그인 기준 과정 목록
	    ============================================ */
	 public List<Map<String, Object>> getCourses() {
	     Long clientId = AuthUtil.getClientId();
	     return dashboardMapper.selectInstitutionCourses(clientId);
	 }
	
	 /* ============================================
	    과정 이벤트 로그
	    ============================================ */
	 public List<Map<String, Object>> getEvents(Long clientId, String courseId) {
	     return dashboardMapper.selectCourseEvents(clientId, courseId);
	 }
}
