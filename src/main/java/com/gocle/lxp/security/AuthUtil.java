package com.gocle.lxp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    private AuthUtil() {}

    public static CustomUserDetails getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }
        return (CustomUserDetails) auth.getPrincipal();
    }

    public static String getRole() {
        return getUser().getRole();
    }

    public static Long getClientId() {
        return getUser().getClientId();
    }

    public static boolean isAdmin() {
        return "ADMIN".equals(getRole());
    }

    public static boolean isInstitutionAdmin() {
        return "INSTITUTION_ADMIN".equals(getRole());
    }
}
