package com.gocle.lxp.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompletionContext {

    private String contentType;      // VIDEO, HTML, QUIZ
    private double maxProgress;      // VIDEO_PROGRESS 최고값
    private double scrollPercent;    // HTML_SCROLL 최고값
    private int score;               // QUIZ 점수
    private long staySeconds;        // 체류시간
}
