package com.ulxsth.strategy;

public class DuplicateDigitStrategy {
    public static boolean hasDuplicateDigit(String number) {
        for (int i = 0; i < number.length(); i++) {
            for (int j = i + 1; j < number.length(); j++) {
                if (number.charAt(i) == number.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
