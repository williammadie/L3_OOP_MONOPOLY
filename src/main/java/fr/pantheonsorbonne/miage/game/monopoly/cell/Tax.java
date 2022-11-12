package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class Tax extends Cell {
    private final int taxAmount;

    protected Tax(String name, int taxAmount) {
        super(name);
        this.taxAmount = taxAmount;
    }

    @Override
    public void trigger(Player player) {
        player.removeMoney(taxAmount);
    }
    
}
