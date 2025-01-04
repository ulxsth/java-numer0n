package com.ulxsth;

import com.ulxsth.error.InvalidCallNumberException;
import com.ulxsth.error.InvalidCallNumberReason;
import com.ulxsth.unit.strategy.CallStrategy;
import com.ulxsth.unit.strategy.DuplicateDigitStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Game {
    @Getter
    private int turn;
    @Getter
    private boolean isFinished;

    @NonNull
    private GameConfig config;
    @NonNull
    private Player firstPlayer;
    @NonNull
    private Player secondPlayer;

    public void nextTurn() {
        turn++;
    }

    public void resetTurn() {
        turn = 0;
    }

    public void finish() {
        isFinished = true;
    }

    public CallResult call(PlayerIdentify playerIdentify, String number) {
        if (number.length() != config.digit()) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.INVALID_DIGIT);
        }

        if(!number.matches("[0-9]+")) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.INVALID_CHARS);
        }

        if(!config.includeZero() && number.contains("0")) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.CANT_USE_ZERO);
        }

        if(DuplicateDigitStrategy.hasDuplicateDigit(number)) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.DUPLICATE_DIGIT);
        }

        String receiverNumber = switch (playerIdentify) {
            case FIRST -> firstPlayer.getNumber();
            case SECOND -> secondPlayer.getNumber();
        };

        return CallStrategy.call(receiverNumber, number);
    }
}
