package com.gocle.lxp.controller;

import com.gocle.lxp.common.ApiResponse;
import com.gocle.lxp.common.MessageUtil;
import com.gocle.lxp.domain.LoginUser;
import com.gocle.lxp.dto.LoginRequest;
import com.gocle.lxp.security.JwtUtil;
import com.gocle.lxp.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final MessageUtil messageUtil;

    public AuthController(
            AuthService authService,
            JwtUtil jwtUtil,
            MessageUtil messageUtil
    ) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.messageUtil = messageUtil;
    }

    /**
     * 관리자 / 기관관리자 공통 로그인
     */
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {

        LoginUser loginUser =
            authService.validateLogin(
                request.getUserId(),
                request.getPassword()
            );

        if (loginUser == null) {
            return ApiResponse.error(
                401,
                messageUtil.get("auth.login.fail")
            );
        }

        String token = jwtUtil.generateToken(
            loginUser.getUserId(),
            loginUser.getRole(),
            loginUser.getClientId()
        );

        return ApiResponse.success(
            messageUtil.get("auth.login.success"),
            new LoginResponse(token, loginUser)
        );
    }

    /**
     * 로그인 사용자 정보 (JWT 기준)
     */
    @GetMapping("/me")
    public ApiResponse<?> me(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ApiResponse.error(
                401,
                messageUtil.get("auth.token.missing")
            );
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return ApiResponse.error(
                401,
                messageUtil.get("auth.token.invalid")
            );
        }

        // 🔥 DB 조회 없음 (핵심)
        LoginUser loginUser = jwtUtil.extractLoginUser(token);

        return ApiResponse.success("success", loginUser);
    }

    record LoginResponse(String token, LoginUser user) {}
}
