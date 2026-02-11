package com.gocle.lxp.domain;

import com.gocle.lxp.domain.User;
import com.gocle.lxp.domain.InstitutionUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공 후 공통 사용자 정보
 * - 관리자(users)
 * - 기관관리자(institution_user)
 */
@Getter
@AllArgsConstructor
public class LoginUser {

    /** 로그인 ID */
    private String userId;

    /** 역할: ADMIN | INSTITUTION_ADMIN */
    private String role;

    /** 기관 ID (ADMIN은 null) */
    private Long clientId;

    /** 사용자 이름 */
    private String name;

    /** 관리자 로그인 */
    public static LoginUser admin(User user) {
        return new LoginUser(
            user.getUserId(),
            "ADMIN",
            null,
            user.getName()
        );
    }

    /** 기관 담당자 로그인 */
    public static LoginUser institutionAdmin(InstitutionUser institutionUser) {
        return new LoginUser(
            institutionUser.getLoginId(),
            "INSTITUTION_ADMIN",
            institutionUser.getClientId(),
            institutionUser.getUserName()
        );
    }
}
