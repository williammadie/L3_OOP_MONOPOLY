package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class AlwaysBuy extends Strategy {

    @Override
    public boolean doBuyCell(Player player) {
        return true;
    }

}
