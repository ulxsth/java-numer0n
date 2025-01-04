package com.ulxsth.error;

import lombok.Getter;

@Getter
public class InvalidCallNumberException extends IllegalArgumentException {
    private String number;
    private InvalidCallNumberReason reason;
}
