package com.gocle.lxp.service.impl;

import com.gocle.lxp.domain.User;
import com.gocle.lxp.mapper.AuthMapper;
import com.gocle.lxp.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    public AuthServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public User validateUser(String userId, String password) {

        User user = authMapper.findByUserId(userId);

        if (user == null) {
            return null;
        }

        // 여기에 실제로는 비밀번호 암호화 비교 필요
        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
    
    @Override
    public User findByUserId(String userId) {
        return authMapper.findByUserId(userId);
    }
}
