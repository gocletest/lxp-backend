package com.gocle.lxp.service;

import com.gocle.lxp.domain.User;

public interface AuthService {
    User validateUser(String userId, String password);
    
    User findByUserId(String userId);
}
