package com.gocle.lxp.mapper;

import com.gocle.lxp.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT id, name FROM user WHERE id = #{id}")
    User findById(Long id);
}
