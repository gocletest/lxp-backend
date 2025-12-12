package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.common.MessageUtil;
import com.gocle.lxp.domain.User;
import com.gocle.lxp.dto.LoginRequest;
import com.gocle.lxp.security.JwtUtil;
import com.gocle.lxp.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MessageUtil messageUtil;

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil, MessageUtil messageUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.messageUtil = messageUtil;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {

        User user = authService.validateUser(request.getUserId(), request.getPassword());

        if (user == null) {
            return ApiResponse.error(401, messageUtil.get("auth.login.fail"));
        }

        // JWT 생성
        String token = jwtUtil.generateToken(user.getUserId());

        return ApiResponse.success(
        		messageUtil.get("auth.login.success"),
                new LoginResponse(token, user)
        );
    }
    
    @GetMapping("/me")
    public ApiResponse<?> me(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ApiResponse.error(401, messageUtil.get("auth.token.missing"));
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        if (userId == null || !jwtUtil.validateToken(token)) {
            return ApiResponse.error(401, messageUtil.get("auth.token.invalid"));
        }

        User user = authService.findByUserId(userId);
        if (user == null) {
            return ApiResponse.error(401, messageUtil.get("auth.user.notfound"));
        }

        return ApiResponse.success("success", user);
    }

    
    record LoginResponse(String token, User user) {}
}

