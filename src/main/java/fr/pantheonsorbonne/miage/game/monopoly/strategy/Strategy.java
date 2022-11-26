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
            if (player.getProperties().isEmpty())
                    return;
                   player.makeChoice(GameAction.SELL_CELL);  
            // vend la 1ere de la liste des props 
                break; 
            
            case BUY_HOUSE:
            if (doBuyHouse(player)) {
                Cell currentCell = Board.getCellWithId(player.getPawnPosition());
                try {
                    currentCell.buyHouse(player);
                } catch (CellCannotBeBuiltException e) {
                    e.printStackTrace();
                }
            }
            // Il construit tt le temps
            // il a le choix d'acheter une maison sur un des terrains qu'il a, le do buy house peut s'appliquer 
                break;

            case SELL_HOUSE:
            if(player.getBalance() <= 0 && !player.getProperties().isEmpty()){                
                    player.removeHouse();            
            }
            // il vend une maison que si il est obligé  
            // parcourir la liste des proprietés : si prop qui a des maisons je vends 
                break;

            default:
                break;
        } 
    }



    boolean doBuyCell(Player player);

    boolean doBuyHouse(Player player);

   

}