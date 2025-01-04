package com.ulxsth;

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

    public CallResult call(@NonNull Player receiver, String number) {
        return null;
    }
}
