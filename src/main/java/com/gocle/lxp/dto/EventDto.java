package com.gocle.lxp.dto;

import lombok.Data;

@Data
public class EventDto {

    private String eventType;   // e.g. course_started, lesson_completed
    private String user;        // 사용자명 or userId
    private String time;        // ISO or yyyy-MM-dd HH:mm:ss
}
