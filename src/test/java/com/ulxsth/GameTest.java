package com.ulxsth;

import com.ulxsth.error.InvalidCallNumberException;
import com.ulxsth.error.InvalidCallNumberReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        GameConfig config = new GameConfig(true, 3); // 0を含むルールで3桁の番号
        game = new Game(config, new Player("123"), new Player("456")); // 番号を仮に設定
    }

    @Nested
    class NextTurn {
        @Test
        void testNextTurn_IncrementsTurn() {
            game.nextTurn();
            assertEquals(1, game.getTurn(), "nextTurn() should increment the turn.");
        }
    }

    @Nested
    class ResetTurn {
        @Test
        void testReset_TurnResetsTurnToZero() {
            game.nextTurn();
            game.resetTurn();
            assertEquals(0, game.getTurn(), "resetTurn() should reset the turn to 0.");
        }
    }

    @Nested
    class Call {

        @Nested
        class NormalCases {
            @Test
            void testCall() {
                // Arrange
                String number = "123";
                String callNumber = "243";

                // Act
                var result = game.call(PlayerIdentify.FIRST, callNumber);

                // Assert
                assertEquals(1, result.getEat(), "Expected 1 eat.");
                assertEquals(1, result.getBite(), "Expected 1 bite.");
            }
        }

        @Nested
        class AbnormalCases {
            @Test
            void testCall_InvalidDigit() {
                // Arrange
                String callNumber = "1234";

                // Act & Assert
                InvalidCallNumberException exception = assertThrows(InvalidCallNumberException.class,
                        () -> game.call(PlayerIdentify.FIRST, callNumber));

                assertEquals(InvalidCallNumberReason.INVALID_DIGIT, exception.getReason(),
                        "Expected an exception with reason INVALID_DIGIT.");
                assertEquals(callNumber, exception.getNumber(),
                        "Expected an exception with the invalid number.");
            }

            @Test
            void testCall_CantUseZero() {
                // Arrange
                Game game = new Game(
                        new GameConfig(false, 3),   // 0を含まないルールで3桁の番号
                        new Player("123"),
                        new Player("456")
                );
                String number = "123";
                String callNumber = "203";

                // Act & Assert
                InvalidCallNumberException exception = assertThrows(InvalidCallNumberException.class,
                        () -> game.call(PlayerIdentify.FIRST, callNumber));

                assertEquals(InvalidCallNumberReason.CANT_USE_ZERO, exception.getReason(),
                        "Expected an exception with reason CANT_USE_ZERO.");
                assertEquals(callNumber, exception.getNumber(),
                        "Expected an exception with the invalid number.");
            }

            @Test
            void testCall_InvalidChars() {
                // Arrange
                String number = "123";
                String callNumber = "2a3";

                // Act & Assert
                InvalidCallNumberException exception = assertThrows(InvalidCallNumberException.class,
                        () -> game.call(PlayerIdentify.FIRST, callNumber));

                assertEquals(InvalidCallNumberReason.INVALID_CHARS, exception.getReason(),
                        "Expected an exception with reason INVALID_CHARS.");
                assertEquals(callNumber, exception.getNumber(),
                        "Expected an exception with the invalid number.");
            }

            @Test
            void testCall_DuplicateDigit() {
                // Arrange
                String number = "123";
                String callNumber = "223";

                // Act & Assert
                InvalidCallNumberException exception = assertThrows(InvalidCallNumberException.class,
                        () -> game.call(PlayerIdentify.FIRST, callNumber));

                assertEquals(InvalidCallNumberReason.DUPLICATE_DIGIT, exception.getReason(),
                        "Expected an exception with reason DUPLICATE_DIGIT.");
                assertEquals(callNumber, exception.getNumber(),
                        "Expected an exception with the invalid number.");
            }
        }
    }
}