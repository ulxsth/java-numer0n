package com.ulxsth;

import com.ulxsth.error.InvalidCallNumberException;
import com.ulxsth.error.InvalidCallNumberReason;
import com.ulxsth.strategy.CallStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Game {
    @Getter private int turn;

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

    public CallResult call(PlayerIdentify playerIdentify, String number) {
        if (number.length() != config.digit()) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.INVALID_DIGIT);
        }

        if(config.includeZero() && number.contains("0")) {
            throw new InvalidCallNumberException(number, InvalidCallNumberReason.CANT_USE_ZERO);
        }

        String receiverNumber = switch (playerIdentify) {
            case FIRST -> secondPlayer.getNumber();
            case SECOND -> firstPlayer.getNumber();
        };

        return CallStrategy.call(receiverNumber, number);
    }
}
