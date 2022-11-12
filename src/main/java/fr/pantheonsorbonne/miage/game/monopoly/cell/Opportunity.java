package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Opportunity extends Cell {
    private static final int OPPORTUNITY_MIN_MONEY_AMOUNT = 50;
    private static final int OPPORTUNITY_MAX_MONEY_AMOUNT = 500;

    public Opportunity(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        switch (GameLogic.getRandomNumberBetween(0, 3)) {
            case 0:
                player.addMoney(
                        GameLogic.getRandomNumberBetween(OPPORTUNITY_MIN_MONEY_AMOUNT, OPPORTUNITY_MAX_MONEY_AMOUNT));
                break;
            case 1:
                player.removeMoney(
                        GameLogic.getRandomNumberBetween(OPPORTUNITY_MIN_MONEY_AMOUNT, OPPORTUNITY_MAX_MONEY_AMOUNT));
                break;
            case 2:
                player.movePawnTo(Jail.JAIL_CELL_ID);
                player.setIsJailed(true);
                break;
            default:
                player.movePawnOf(GameLogic.getRandomNumberBetween(10, 40));
        }
    }

}
