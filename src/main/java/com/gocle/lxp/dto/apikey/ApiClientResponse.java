package com.gocle.lxp.dto.apikey;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiClientResponse {

    /** Client PK */
    private Long clientId;

    /** Client 코드 (예: OKLMS) */
    private String clientCode;

    /** Client 이름 */
    private String clientName;

    /** 상태 (ACTIVE / INACTIVE) */
    private String status;

    /** 생성일 */
    private LocalDateTime createdAt;
}
