package com.ulxsth.integration;

import com.ulxsth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameIntegrationTest {
    private Game game;

    @BeforeEach
    void setUp() {
        GameConfig config = new GameConfig(true, 3); // 3桁の番号、0を含むルール
        Player player1 = new Player("123"); // Player 1 番号
        Player player2 = new Player("456"); // Player 2 番号
        game = new Game(config, player1, player2); // ゲームの初期化
    }

    @Test
    void testGameFlow() {
        // ターン1: Player 1 が Player 2 にコールする
        String callNumberPlayer1 = "135";
        CallResult resultPlayer1 = game.call(PlayerIdentify.SECOND, callNumberPlayer1);
        assertEquals(0, resultPlayer1.getEat(), "Player 1's Eat count should be 0.");
        assertEquals(1, resultPlayer1.getBite(), "Player 1's Bite count should be 1.");

        // ターン2: Player 2 が Player 1 にコールする
        String callNumberPlayer2 = "243";
        CallResult resultPlayer2 = game.call(PlayerIdentify.FIRST, callNumberPlayer2);
        assertEquals(1, resultPlayer2.getEat(), "Player 2's Eat count should be 1.");
        assertEquals(1, resultPlayer2.getBite(), "Player 2's Bite count should be 1.");

        // 正しい結果が返っているかを確認した上で次のターンに進む
        game.nextTurn();
        assertEquals(1, game.getTurn(), "Game turn should now be incremented to 1.");

        // ターン3: Player 1 が正解をコールする
        callNumberPlayer1 = "456";
        resultPlayer1 = game.call(PlayerIdentify.SECOND, callNumberPlayer1);
        assertEquals(3, resultPlayer1.getEat(), "Player 1's Eat count should be 3 (winning condition).");
        assertEquals(0, resultPlayer1.getBite(), "Player 1's Bite count should remain 0.");

        // 勝者確認
        assertTrue(game.isFinished(), "Game should be finished after the correct call.");
        assertEquals(PlayerIdentify.FIRST, game.getWinner(), "Player 1 should be the winner.");
    }
}