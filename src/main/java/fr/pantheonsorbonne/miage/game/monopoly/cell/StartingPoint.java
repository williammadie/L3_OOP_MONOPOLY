package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class StartingPoint extends Cell {
    private static final int MONEY_GIFT_AMOUNT = 200;

    public StartingPoint(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        player.addMoney(MONEY_GIFT_AMOUNT);
    }

}
