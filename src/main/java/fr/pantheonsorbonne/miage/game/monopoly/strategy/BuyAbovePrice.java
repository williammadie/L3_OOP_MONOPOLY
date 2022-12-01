package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class BuyAbovePrice extends CommonActions {

    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());

        int prix = currentCell.getPrice();
        return prix >= 100;
    }

}
