package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

/**
 * This strategy allows the player to only buy the most expensive properties
 * located on the second part of the board (from red to deep blue)
 */
public class BuyAbovePrice extends Strategy {
    public static final String IDENTIFIER = "BuyAbovePrice";

    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());

        int prix = currentCell.getPrice();
        return prix >= 100 && super.doBuyCell(player);
    }

}
