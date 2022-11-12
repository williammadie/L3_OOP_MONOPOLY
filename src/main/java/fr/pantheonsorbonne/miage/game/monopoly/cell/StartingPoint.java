package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class StartingPoint extends Cell {
    public static final int MONEY_GIFT_AMOUNT = 200;

    public StartingPoint(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        player.getStartingBonus();
    }

}
