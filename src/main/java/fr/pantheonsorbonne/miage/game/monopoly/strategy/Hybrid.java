package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Hybrid extends Strategy {

    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        return player.calculateBuyingWish(player, currentCell.getColor()) > 40;
    }

}
