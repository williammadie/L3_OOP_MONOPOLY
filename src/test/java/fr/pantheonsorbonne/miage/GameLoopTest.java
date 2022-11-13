package fr.pantheonsorbonne.miage;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class GameLoopTest {


    @BeforeEach
    void init() {
        List<Player> playersInSession = Arrays.asList(
                new Player(GameLogic.getUniquePlayerName()),
                new Player(GameLogic.getUniquePlayerName()),
                new Player(GameLogic.getUniquePlayerName()));
        Deque<Player> players = GameLogic.determinePlayersOrder(playersInSession);
    }

    void testPlayerOrder() {
        
    }
}
