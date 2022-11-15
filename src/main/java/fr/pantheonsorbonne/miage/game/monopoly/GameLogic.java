package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.player.PlayerRankComparator;

public class GameLogic {
    public static final Random RAND = new Random();

    private GameLogic() {
    }

    public static Deque<Player> determinePlayersOrder(List<Player> players) {
        do {
            for (Player player : players) {
                player.rollDoubleDiceForRanking();
            }
        } while (players.stream().map(Player::getRank).collect(Collectors.toSet()).size() != players.size());
        
        players.sort(new PlayerRankComparator().reversed());
        ArrayDeque<Player> playersQueue = new ArrayDeque<>();
        playersQueue.addAll(players);
        return playersQueue;
    }

    public static int getRandomNumberBetween(int minIncluded, int maxIncluded) {
        return GameLogic.RAND.nextInt(maxIncluded - minIncluded + 1) + minIncluded;
    }

    public static String getUniquePlayerName() {
        StringBuilder playerName = new StringBuilder();
        playerName.append("P-");
        playerName.append(UUID.randomUUID().toString());
        return playerName.toString();
    }
}
