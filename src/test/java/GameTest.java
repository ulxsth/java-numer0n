import com.ulxsth.*;
import com.ulxsth.error.InvalidCallNumberException;
import com.ulxsth.error.InvalidCallNumberReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        GameConfig config = new GameConfig(true, 3); // 0を含むルールで3桁の番号
        game = new Game(config, new Player("123"), new Player("456")); // 番号を仮に設定
    }

    @DisplayName("nextTurn()")
    @Nested
    class NextTurn {
        @Test
        void testNextTurnIncrementsTurn() {
            game.nextTurn();
            assertEquals(1, game.getTurn(), "nextTurn() should increment the turn.");
        }
    }

    @DisplayName("resetTurn()")
    @Nested
    class ResetTurn {
        @Test
        void testResetTurnResetsTurnToZero() {
            game.nextTurn();
            game.resetTurn();
            assertEquals(0, game.getTurn(), "resetTurn() should reset the turn to 0.");
        }
    }

    @DisplayName("call()")
    @Nested
    class Call {
        @Test
        void testCallExactMatch() {
            CallResult result = game.call(game.getFirstPlayer(), "123");
            assertEquals(3, result.getEat());
            assertEquals(0, result.getBite());
            assertTrue(result.isCorrect());
        }

        @Test
        void testCallPartialEat() {
            CallResult result = game.call(game.getSecondPlayer(), "124");
            assertEquals(2, result.getEat());
            assertEquals(0, result.getBite());
        }

        @Test
        void testCallPartialBite() {
            CallResult result = game.call(game.getFirstPlayer(), "321");
            assertEquals(0, result.getEat());
            assertEquals(3, result.getBite());
        }

        @Test
        void testCallNoMatch() {
            CallResult result = game.call(game.getFirstPlayer(), "789");
            assertEquals(0, result.getEat());
            assertEquals(0, result.getBite());
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
}