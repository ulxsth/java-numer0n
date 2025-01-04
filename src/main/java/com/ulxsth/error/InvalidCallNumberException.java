package com.ulxsth.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidCallNumberException extends IllegalArgumentException {
    private String number;
    private InvalidCallNumberReason reason;
}
