package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameLogic {
    public static final Random RAND = new Random();

    private GameLogic() {
    }

    public static Deque<Player> determinePlayersOrder(List<Player> players) {
        for (Player player : players) {
            player.rollDoubleDiceForRanking();
        }
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
