package com.gocle.lxp.policy;

import com.gocle.lxp.domain.CompletionContext;

public interface CompletionPolicy {

    boolean isCompleted(CompletionContext context);

    double calculateProgress(CompletionContext context);
}
