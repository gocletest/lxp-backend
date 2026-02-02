package com.gocle.lxp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

@Mapper
public interface AdminDashboardMapper {

    Map<String, Object> selectPlatformOverview();

    List<Map<String, Object>> selectClientHealth();
}
