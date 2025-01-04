package com.ulxsth.strategy;

import com.ulxsth.CallResult;

public class CallNumberStrategy {
    public static CallResult call(String number, String callNumber) throws IllegalArgumentException {
        int eat = getEat(number, callNumber);
        int bite = getBite(number, callNumber) - eat;
        boolean isCorrect = eat == number.length();
        return new CallResult(eat, bite, isCorrect);
    }

    private static int getEat(String number, String callNumber) {
        if(number.length() != callNumber.length()) {
            throw new IllegalArgumentException("The length of the numbers must be the same.");
        }

        int eat = 0;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == callNumber.charAt(i)) {
                eat++;
            }
        }
        return eat;
    }

    private static int getBite(String number, String callNumber) {
        int bite = 0;
        for (int i = 0; i < number.length(); i++) {
            String c = String.valueOf(callNumber.charAt(i));
            if(number.contains(c)) {
                bite++;
            }
        }
        return bite;
    }
}
