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

    public int handleBuyCell(Player player) {
        if (!doBuyCell(player))
            return -1;

        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        try {
            currentCell.buyCell(player);
            return currentCell.getCellId();
        } catch (CellCannotBeBoughtException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(
                    "Cannot buy cell n°" + player.getPawnPosition() + " for an unknown reason");
        }
    }

    public int handleSellCell(Player player) {
        if (player.getProperties().isEmpty())
            return -1;

        try {
            Cell cellToSell = player.getProperties().get(0);
            cellToSell.sellCell(player);
            return cellToSell.getCellId();
        } catch (CellCannotBeSoldException e1) {
            e1.printStackTrace();
            throw new UnsupportedOperationException("Cannot sell cell n°" + player.getPawnPosition() + " for an unknown reason");
        }
    }

    public int handleBuyHouse(Player player) {
        if (!doBuyHouse(player))
            return -1;

        for (Property property : player.getProperties()) {
            if (tryToBuyHouse(player, property))
                return property.getCellId();
        }
        return -1;
    }

    public int handleSellHouse(Player player) {
        for (Property property : player.getProperties()) {
            if (tryToSellHouse(player, property))
                return property.getCellId();
        }
        return -1;
    }

    private boolean tryToBuyHouse(Player player, Property property) {
        try {
            property.buyHouse(player);
            return true;
        } catch (CellCannotBeBuiltException e) {
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
        } catch (CellCannotBeBuiltException e) {
            return false;
        }
    }

    public boolean doBuyCell(Player player) {
        return true;
    }

    public boolean doBuyHouse(Player player) {
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}