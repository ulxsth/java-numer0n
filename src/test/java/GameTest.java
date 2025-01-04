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
}