package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

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
            // vend la 1ere de la liste des props : si la liste est vide et pas de prop on fait rien
                break; 
            
            case BUY_HOUSE:
            // Il construit tt le temps
            // il a le choix d'acheter une maison sur un des terrains qu'il a, le do buy house peut s'appliquer 
                break;

            case SELL_HOUSE:
            // il vend une maison que si il est obligé donc do sell house ne va servir à rien 
            // parcourir la liste des proprietés : si prop qui a des maisons je vends 
                break;

            default:
                break;
       } 
    }



    boolean doBuyCell(Player player);

    boolean doBuyHouse(Player player);

    boolean doSellHouse(Player player);

}
