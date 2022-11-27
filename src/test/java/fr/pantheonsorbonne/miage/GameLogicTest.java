package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;

class GameLogicTest {

    @Test
    void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GameLogic.handleGameOver("test");
        });

        String expectedMessage = "Status can only be winner or loser";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}