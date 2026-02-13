package com.gocle.lxp.policy;

import com.gocle.lxp.domain.CompletionContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultCompletionPolicy implements CompletionPolicy {

    private static final double VIDEO_THRESHOLD = 90.0;
    private static final double HTML_SCROLL_THRESHOLD = 80.0;
    private static final long HTML_MIN_TIME = 120; // seconds
    private static final int QUIZ_MIN_SCORE = 60;

    @Override
    public boolean isCompleted(CompletionContext context) {

        switch (context.getContentType()) {

            case "VIDEO":
                return context.getMaxProgress() >= VIDEO_THRESHOLD;

            case "HTML":
                return context.getScrollPercent() >= HTML_SCROLL_THRESHOLD
                        && context.getStaySeconds() >= HTML_MIN_TIME;

            case "QUIZ":
                return context.getScore() >= QUIZ_MIN_SCORE;

            default:
                return false;
        }
    }

    @Override
    public double calculateProgress(CompletionContext context) {

        switch (context.getContentType()) {

            case "VIDEO":
                return Math.min(context.getMaxProgress(), 100.0);

            case "HTML":
                return Math.min(context.getScrollPercent(), 100.0);

            case "QUIZ":
                return context.getScore() >= QUIZ_MIN_SCORE ? 100.0 : 0.0;

            default:
                return 0.0;
        }
    }
}
