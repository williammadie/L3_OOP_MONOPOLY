package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class GoToJail extends Cell {
    private static final int JAIL_CELL_ID = 10;
    public GoToJail(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        player.movePawnTo(JAIL_CELL_ID);
        player.setIsJailed(true);
    }
    
}
