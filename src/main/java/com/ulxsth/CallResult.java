package com.ulxsth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CallResult {
    private int eat;
    private int bite;
    private boolean isCorrect;

    public CallResult withCorrect(boolean isCorrect) {
        return new CallResult(eat, bite, isCorrect);
    }
}