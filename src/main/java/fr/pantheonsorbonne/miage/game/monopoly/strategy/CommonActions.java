package fr.pantheonsorbonne.miage.game.monopoly.strategy;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class CommonActions implements Strategy{
    

    @Override
    public boolean doBuyHouse(Player player) {
        return true;
    }

}
