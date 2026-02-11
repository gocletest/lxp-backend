package com.gocle.lxp.domain;

import lombok.Getter;

@Getter
public class InstitutionUser {

    private Long institutionUserId;
    private Long clientId;

    private String loginId;
    private String userName;
    private String password;

    private String role;   // INSTITUTION_ADMIN / USER
    private String status; // ACTIVE / INACTIVE
}
