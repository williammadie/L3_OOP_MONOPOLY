package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

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

    @RepeatedTest(1000)
    void getRandomColorTest() {
        Color color = GameLogic.getRandomColor();
        assertTrue(Arrays.asList(Color.values()).contains(color));
    }

    @RepeatedTest(100)
    void generateUniquePlayerNameTest() {
        Player p1 = new Player(GameLogic.generateUniquePlayerName());
        Player p2 = new Player(GameLogic.generateUniquePlayerName());
        Player p3 = new Player(GameLogic.generateUniquePlayerName());
        Player p4 = new Player(GameLogic.generateUniquePlayerName());
        assertFalse(
                p1.getName().equals(p2.getName()) &&
                        p2.getName().equals(p3.getName()) &&
                        p3.getName().equals(p4.getName()) &&
                        p4.getName().equals(p1.getName()));
    }

    @Test
    void playTheGameTest() {
        Player p1 = new Player(GameLogic.generateUniquePlayerName());
        Player p2 = new Player(GameLogic.generateUniquePlayerName());
        Player p3 = new Player(GameLogic.generateUniquePlayerName());
        Player p4 = new Player(GameLogic.generateUniquePlayerName());
        List<Player> players = Arrays.asList(new Player[] { p1, p2, p3, p4 });
        Player winner = GameLogic.playTheGame(players);
        assertTrue(players.contains(winner));
    }
}