package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

/**
 * This strategy forces the player to buy every time it is possible.
 */
public class AlwaysBuy extends AbstractStrategy {
    public static final String IDENTIFIER = "AlwaysBuy";

    @Override
    public boolean doBuyCell(Player player) {
        return true;
    }

}
