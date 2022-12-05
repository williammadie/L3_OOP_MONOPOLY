package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

class GameLoopTest {

    private List<Player> playersInSession;
    private Deque<Player> players;

    @BeforeEach
    void setUp() {
        this.playersInSession = Arrays.asList(
                new Player(GameLogic.getUniquePlayerName()),
                new Player(GameLogic.getUniquePlayerName()),
                new Player(GameLogic.getUniquePlayerName()));
        this.players = GameLogic.determinePlayersOrder(playersInSession);
    }

    @Test
    void testForDuplicatesInPlayerRanks() {
        int nbDifferentRanks = this.players.stream().map(Player::getRank).collect(Collectors.toSet()).size();
        assertEquals(this.players.size(), nbDifferentRanks);
    }
}
