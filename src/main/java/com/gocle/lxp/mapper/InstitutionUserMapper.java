package com.gocle.lxp.mapper;

import com.gocle.lxp.dto.institution.InstitutionUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InstitutionUserMapper {

    List<InstitutionUserDto> selectInstitutionUsers(
        @Param("clientId") Long clientId,
        @Param("clientName") String clientName,
        @Param("loginId") String loginId,
        @Param("userName") String userName,
        @Param("status") String status
    );

    InstitutionUserDto selectById(
        @Param("institutionUserId") Long institutionUserId
    );

    /** 🔥 기관 내 로그인ID 중복 체크 */
    int existsByClientIdAndLoginId(
        @Param("clientId") Long clientId,
        @Param("loginId") String loginId
    );

    int insertInstitutionUser(InstitutionUserDto user);

    int updateInstitutionUser(InstitutionUserDto user);

    int updateStatus(
        @Param("institutionUserId") Long institutionUserId,
        @Param("status") String status
    );

    int updatePassword(
        @Param("institutionUserId") Long institutionUserId,
        @Param("password") String password
    );

    int deleteById(
        @Param("institutionUserId") Long institutionUserId
    );
}



