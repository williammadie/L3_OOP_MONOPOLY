package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.HashMap;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Jail extends Special {
    public static final int JAIL_CELL_ID = 10;
    private static final HashMap<Player, Integer> turnCounter = new HashMap<>();

    public Jail(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
        if (!player.getIsJailed())
            return;

        try {
            turnCounter.put(player, turnCounter.get(player) + 1);
        } catch (Exception e) {
            turnCounter.put(player, 1);
        }
        DoubleDice dice = new DoubleDice();
        int result = dice.getValue();
        if (dice.hasDoubleValue() || turnCounter.get(player) == 3) {
            player.movePawnOf(result);
            turnCounter.remove(player);
            player.setIsJailed(false);
        }
            
    }
    
}
