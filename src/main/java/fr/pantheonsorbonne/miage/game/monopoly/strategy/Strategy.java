package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import java.util.NoSuchElementException;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeSoldException;

public interface Strategy {

    default void makeChoice(GameAction possibleAction, Player player) {
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

    default void handleBuyCell(Player player) {
        if (!doBuyCell(player))
            return;

        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        try {
            currentCell.buyCell(player);
        } catch (CellCannotBeBoughtException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    default void handleSellCell(Player player) {
        if (player.getProperties().isEmpty())
            return;

        try {
            player.getProperties().get(0).sellCell(player);
        } catch (CellCannotBeSoldException e1) {
            e1.printStackTrace();
            System.exit(2);
        }
    }

    default void handleBuyHouse(Player player) {
        if (!doBuyHouse(player))
            return;

        for (Property property : player.getProperties()) {
            if (tryToBuyHouse(player, property))
                return;
        }
    }

    default void handleSellHouse(Player player) {
        for (Property property : player.getProperties()) {
            if (tryToSellHouse(player, property))
                return;
        }
    }

    default boolean tryToBuyHouse(Player player, Property property) {
        try {
            property.buyHouse(player);
            return true;
        } catch (CellCannotBeBuiltException e) {
            return false;
        }
    }

    default boolean tryToSellHouse(Player player, Property property) {
        if (player.getProperties().isEmpty() || property.getHouseNumber() == 0) {
            return false;
        }

        try {
            property.sellHouse(player);
            return true;
        } catch (CellCannotBeBuiltException e) {
            return false;
        }
    }

    boolean doBuyCell(Player player);

    boolean doBuyHouse(Player player);

}