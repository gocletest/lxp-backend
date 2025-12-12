package com.gocle.lxp.mapper;

import com.gocle.lxp.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    User findByUserId(@Param("userId") String userId);
}
