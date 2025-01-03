package com.ulxsth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Game {
    @Getter private int turn;
    @NonNull private GameConfig config;
    @NonNull @Getter private Player firstPlayer;
    @NonNull @Getter private Player secondPlayer;

    public void nextTurn() {
    }

    public void resetTurn() {
    }

    public CallResultAndCorrectness call(@NonNull Player firstPlayer, String number) {
        return null;
    }
}
