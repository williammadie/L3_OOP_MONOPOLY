package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.Player;

public abstract class Property extends Cell {
    private int price;

    protected Property(String name, int price) {
        super(name);
        this.price = price;
    }

    public boolean canBeBought() {
        return true;
    }

    public abstract boolean isBuildable();

    public abstract int getRentValue(List<Player> players);

}
