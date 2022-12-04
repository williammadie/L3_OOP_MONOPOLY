package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import java.util.NoSuchElementException;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuyException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuildException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotSellException;

/**
 * This class allows the player to play with different strategies. All strategies must extends this basic class.
 */
public abstract class Strategy {

    public void makeChoice(GameAction possibleAction, Player player) {
        switch (possibleAction) {
            case BUY_CELL:
                handleBuyCell(player);
                break;

            case SELL_CELL:
                handleSellCell(player);
                break;

            case BUY_HOUSE:
                handleBuyHouse(player);
                break;

            case SELL_HOUSE:
                handleSellHouse(player);
                break;

            default:
                throw new NoSuchElementException("Player choice required for unexpected case: " + possibleAction);
        }
    }

    public final int handleBuyCell(Player player) {
        if (!doBuyCell(player))
            return -1;

        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        try {
            currentCell.buyCell(player);
            return currentCell.getCellId();
        } catch (CannotBuyException e) {
            e.printStackTrace();
            throw new CriticalMalfunctionException(
                    "Cannot buy cell n°" + player.getPawnPosition() + " for an unknown reason");
        }
    }

    public final int handleSellCell(Player player) {
        if (player.getProperties().isEmpty())
            return GameAction.ABORT_ACTION.value;

        try {
            Cell cellToSell = player.getProperties().get(0);
            cellToSell.sellCell(player);
            return cellToSell.getCellId();
        } catch (CannotSellException e1) {
            e1.printStackTrace();
            throw new CriticalMalfunctionException(
                    "Cannot sell cell n°" + player.getPawnPosition() + " for an unknown reason");
        }
    }

    public final int handleBuyHouse(Player player) {
        for (Property property : player.getProperties()) {
            if (tryToBuyHouse(player, property))
                return property.getCellId();
        }
        return GameAction.ABORT_ACTION.value;
    }

    public final int handleSellHouse(Player player) {
        for (Property property : player.getProperties()) {
            if (tryToSellHouse(player, property))
                return property.getCellId();
        }
        return GameAction.ABORT_ACTION.value;
    }

    private boolean tryToBuyHouse(Player player, Property property) {
        try {
            property.buyHouse(player);
            return true;
        } catch (CannotBuildException e) {
            return false;
        }
    }

    private boolean tryToSellHouse(Player player, Property property) {
        if (player.getProperties().isEmpty() || property.getHouseNumber() == 0) {
            return false;
        }

        try {
            property.sellHouse(player);
            return true;
        } catch (CannotBuildException e) {
            return false;
        }
    }

    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        return this.calculateBuyingWish(player, currentCell.getColor()) > 40;
    }

    /**
     * This function calculates an indicator which represents the will of player to
     * buy a cell. It returns an int between 0 (VERY UNLIKELY TO BUY) and 100 (VERY
     * LIKELY TO BUY) included. By default, it will return 100 for stations and
     * public services. For properties, the value will depend on the number of
     * adversaries already present on the group of colors.
     * 
     * @param player
     * @param color
     * @return a mark between 0 and 100 (both included) which represent the desire to buy
     */
    public int calculateBuyingWish(Player player, Color color) {

        if (color.equals(Color.COLORLESS))
            return 100;

        if (player.getProperties().size() < 3) {
            return Board.getNumberOfAdversaryOwnersForColor(player, color) > 0 ? 0 : 100;
        }

        return (player.getOwnedPropertyNumberWithColor(color) + 1 / Board.getExistingCellNumberWithColor(color)) * 100;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}