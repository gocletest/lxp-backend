package com.gocle.lxp.service.impl;

import com.gocle.lxp.domain.LoginUser;
import com.gocle.lxp.domain.User;
import com.gocle.lxp.domain.InstitutionUser;
import com.gocle.lxp.mapper.AuthMapper;
import com.gocle.lxp.mapper.InstitutionUserMapper;
import com.gocle.lxp.service.AuthService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final InstitutionUserMapper institutionUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthMapper authMapper,
            InstitutionUserMapper institutionUserMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.authMapper = authMapper;
        this.institutionUserMapper = institutionUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 관리자 / 기관관리자 통합 로그인
     */
    @Override
    public LoginUser validateLogin(String userId, String password) {

        /* 1️⃣ 관리자 로그인 (users) */
        User admin = authMapper.findByUserId(userId);
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return LoginUser.admin(admin);
        }

        /* 2️⃣ 기관관리자 로그인 (institution_user) */
        InstitutionUser iu =
            institutionUserMapper.findByLoginId(userId);

        if (iu != null && passwordEncoder.matches(password, iu.getPassword())) {
            return LoginUser.institutionAdmin(iu);
        }

        return null;
    }

    /**
     * ⚠️ 필요 시 관리자 조회용 (거의 안 씀)
     */
    @Override
    public User findByUserId(String userId) {
        return authMapper.findByUserId(userId);
    }
}
