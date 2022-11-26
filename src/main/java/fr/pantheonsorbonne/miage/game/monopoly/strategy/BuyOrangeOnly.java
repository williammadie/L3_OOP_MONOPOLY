package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class BuyOrangeOnly extends CommonActions{
    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        
        String color = currentCell.getColor();
        
    
    return color.equals("Orange");
    }

    @Override
    public boolean doBuyHouse(Player player) {
        // TODO Auto-generated method stub
        return false;
    }
}
