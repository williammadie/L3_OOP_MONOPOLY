package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GameLogic {

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

    public static boolean isBuildingAuthorized(Cell cell, Player player) {
        boolean isBuildingAuthorized = false;
        if (cell.isBuildable) {
            if (doPlayerHasAllCellOfColor(player, cell.getColor())) {
                int houseNumber = cell.getHouseNumber()
                if (houseNumber < Cell.MAX_HOUSE_NUMBER) {
                    if (doOtherCellOfColorHaveAtLeast(cell.getColor(), houseNumber)) {
                        isBuildingAuthorized = true;
                    }
                }
            }  
        }
        return isBuildingAuthorized;
    }

    public static boolean doPlayerHasAllCellOfColor(Player player, Color color) {
        return false;
    }

    public static boolean doOtherCellOfColorHaveAtLeast(Color color, int requiredMinimumHouseNumber) {
        return false;
    }
}
