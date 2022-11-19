package fr.pantheonsorbonne.miage.game.monopoly.player;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;

public interface Strategy {

    default void makeChoice(GameAction gameAction, Player player) {
       switch (gameAction) {
            case BUY_CELL:
                if (doBuyCell(player)) {
                    Cell currentCell = Board.getCellWithId(this.getPawnPosition());
                    try {
                        currentCell.buyCell(player);
                    } catch (CellCannotBeBoughtException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case SELL_CELL:
                break;       
       } 
    }

    int getPawnPosition();

    boolean doBuyCell(Player player);

    boolean doSellCell(Player player);

    boolean doBuyHouse(Player player);

    boolean doSellHouse(Player player);

}
