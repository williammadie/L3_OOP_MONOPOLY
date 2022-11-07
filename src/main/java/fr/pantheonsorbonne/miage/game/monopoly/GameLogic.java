package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Terrain;

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
        if (!cell.canBeBought())
            return false;
        Property buyableCell = (Property) cell;

        if (!buyableCell.isBuildable())
            return false;
        Terrain terrain = (Terrain) cell;

        if (doPlayerHasAllCellOfColor(player, terrain.getColor())) {
            int houseNumber = terrain.getHouseNumber();
            if (houseNumber < Cell.MAX_HOUSE_NUMBER) {
                if (doOtherCellOfColorHaveAtLeast(terrain.getColor(), houseNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean doPlayerHasAllCellOfColor(Player player, Color color) {
        HashSet<Property> playerProperties = player.getProperties();
        int propertyNumberWithGivenColor = (int) playerProperties.stream()
                .filter(property -> property.canBeBought())
                .map(property -> ((Terrain) property).getColor())
                .filter(propertyColor -> propertyColor == color).count();
        switch (color) {
            case BROWN:
            case DEEP_BLUE:
                return propertyNumberWithGivenColor == 2;
            default:
                return propertyNumberWithGivenColor == 3;
        }
    }

    public static boolean doOtherCellOfColorHaveAtLeast(Color color, int requiredMinimumHouseNumber) {
        // TODO
        return false;
    }

    public static Player findOwnerOfCell(int cellId, List<Player> players) {
        // TODO
        return null;
    }
}
