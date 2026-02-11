package com.gocle.lxp.service;

import com.gocle.lxp.domain.LoginUser;
import com.gocle.lxp.domain.User;

public interface AuthService {

    /**
     * 관리자 / 기관담당자 통합 로그인
     */
    LoginUser validateLogin(String loginId, String password);

	/**
	 * ⚠️ 필요 시 관리자 조회용 (거의 안 씀)
	 */
	User findByUserId(String userId);
}
