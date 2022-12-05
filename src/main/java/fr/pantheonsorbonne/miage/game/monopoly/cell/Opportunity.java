package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Opportunity extends AbstractSpecial {
    private static final int OPPORTUNITY_MIN_MONEY_AMOUNT = 10;
    private static final int OPPORTUNITY_MAX_MONEY_AMOUNT = 100;

    public Opportunity(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        switch (GameLogic.getRandomNumberBetween(0, 3)) {
            case 0:
                int bonusAmount = GameLogic.getRandomNumberBetween(OPPORTUNITY_MIN_MONEY_AMOUNT,
                        OPPORTUNITY_MAX_MONEY_AMOUNT);
                player.addMoneySafe(bonusAmount);
                player.getInfoLogger().append(player.getName() + " receives a bonus of " + bonusAmount + "Eur\n");
                break;
            case 1:
                int penaltyAmount = GameLogic.getRandomNumberBetween(OPPORTUNITY_MIN_MONEY_AMOUNT,
                        OPPORTUNITY_MAX_MONEY_AMOUNT);
                player.removeMoneySafe(penaltyAmount);
                player.getInfoLogger().append(player.getName() + " pays a penalty of " + penaltyAmount + "Eur\n");
                break;
            case 2:
                player.movePawnTo(Jail.JAIL_CELL_ID);
                player.setIsJailed(true);
                player.getInfoLogger().append(player.getName() + " is jailed!\n");
                break;
            default:
                int delta = GameLogic.getRandomNumberBetween(10, 40);
                player.movePawnOf(delta);
                player.getInfoLogger().append(player.getName() + " moves his/her pawn of " + delta + " cells!\n");
        }
    }

}
