package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.Comparator;

public class PlayerRankComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getRank(), p2.getRank());
    }
}
