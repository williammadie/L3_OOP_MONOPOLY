package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Tax extends Special {
    private final int taxAmount;

    protected Tax(String name, int taxAmount) {
        super(name);
        this.taxAmount = taxAmount;
    }

    @Override
    public void trigger(Player player) {
        player.removeMoneySafe(taxAmount);
    }
    
}
