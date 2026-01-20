package com.gocle.lxp.dto.institution;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class InstitutionUserDto {

    private Long institutionUserId;
    private Long clientId;
    
    private String clientName;
    private String clientCode;

    private String loginId;
    private String userName;   
    private String password;

    private String role;       // INSTITUTION_ADMIN / USER
    private String status;     // ACTIVE / INACTIVE

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
