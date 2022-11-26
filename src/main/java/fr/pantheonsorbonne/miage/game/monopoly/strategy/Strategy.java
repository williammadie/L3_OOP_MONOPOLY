package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;

import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;

public interface Strategy {

    default void makeChoice(GameAction gameAction, Player player) {
        switch (gameAction) {
            case BUY_CELL:
                if (doBuyCell(player)) {
                    Cell currentCell = Board.getCellWithId(player.getPawnPosition());
                    try {
                        currentCell.buyCell(player);
                    } catch (CellCannotBeBoughtException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case SELL_CELL:
                if (player.getProperties().isEmpty()) {
                    break;
                }
                player.getProperties().get(0).sellCell(player);
                break;

            case BUY_HOUSE:
                if (doBuyHouse(player)) {
                    for (Property property : player.getProperties()) {
                        try {
                            property.buyHouse(player);
                            break;
                        } catch (CellCannotBeBuiltException e) {
                            continue;
                        }
                    }
                }
                break;

            case SELL_HOUSE:
                for (Property property : player.getProperties()) {
                    if (player.getProperties().isEmpty() || property.getHouseNumber() == 0) {
                        continue;
                    }
                    try {
                        property.sellHouse(player);
                        break;
                    } catch (CellCannotBeBuiltException e) {
                        continue;
                    }

                }
                break;

            default:
                break;
        }
    }

    boolean doBuyCell(Player player);

    boolean doBuyHouse(Player player);

}