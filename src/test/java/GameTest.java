import com.ulxsth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        GameConfig config = new GameConfig(true, 3); // 0を含むルールで3桁の番号
        game = new Game(config, new Player("123"), new Player("456")); // 番号を仮に設定
    }

    @Test
    void testNextTurnIncrementsTurn() {
        game.nextTurn();
        assertEquals(1, game.getTurn(), "nextTurn() should increment the turn.");
    }

    @Test
    void testResetTurnResetsTurnToZero() {
        game.nextTurn();
        game.resetTurn();
        assertEquals(0, game.getTurn(), "resetTurn() should reset the turn to 0.");
    }

    @Test
    void testCallExactMatch() {
        CallResultAndCorrectness result = game.call(game.getFirstPlayer(), "123");
        assertEquals(3, result.result().eat());
        assertEquals(0, result.result().bite());
        assertTrue(result.isCorrect());
    }

    @Test
    void testCallPartialMatch() {
        CallResultAndCorrectness result = game.call(game.getSecondPlayer(), "124");
        assertEquals(2, result.result().eat());
        assertEquals(0, result.result().bite());
    }

    @Test
    void testCallNoMatch() {
        CallResultAndCorrectness result = game.call(game.getFirstPlayer(), "789");
        assertEquals(0, result.result().eat());
        assertEquals(0, result.result().bite());
    }

    @Test
    void testInvalidCallNumber_Chars() {
        InvalidCallNumberException exception = assertThrows(
                InvalidCallNumberException.class,
                () -> game.call(game.getFirstPlayer(), "abc"),
                "Invalid characters should throw an InvalidCallNumberException."
        );
        assertEquals(InvalidCallNumberReason.INVALID_CHARS, exception.getReason());
    }

    @Test
    void testInvalidCallNumber_ZeroRule() {
        // 0は許可されない設定に変更
        game = new Game(new GameConfig(false, 3), new Player("123"), new Player("456"));
        InvalidCallNumberException exception = assertThrows(
                InvalidCallNumberException.class,
                () -> game.call(game.getFirstPlayer(), "102"),
                "Using zero should throw an InvalidCallNumberException when zero is not allowed."
        );
        assertEquals(InvalidCallNumberReason.CANT_USE_ZERO, exception.getReason());
    }

    @Test
    void testInvalidCallNumber_WrongDigit() {
        InvalidCallNumberException exception = assertThrows(
                InvalidCallNumberException.class,
                () -> game.call(game.getFirstPlayer(), "12"),
                "Passing numbers with wrong digits should throw an InvalidCallNumberException."
        );
        assertEquals(InvalidCallNumberReason.INVALID_DIGIT, exception.getReason());
    }
}