package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class StartingPoint extends AbstractSpecial {
    public static final int MONEY_GIFT_AMOUNT = 200;
    public static final int NB_TURNS_WITH_START_BONUS = 20;

    public StartingPoint(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        player.getStartingBonus(true);
    }

}
