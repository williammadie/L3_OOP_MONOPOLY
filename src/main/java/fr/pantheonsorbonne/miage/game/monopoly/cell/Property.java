package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public abstract class Property extends Cell {
    private int price;
    protected Player owner;
    protected Color color;

    protected Property(String name, int price, Color color) {
        super(name);
        this.price = price;
        this.color = color;
        this.owner = null;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean isVacant() {
        return this.owner == null;
    }

    public Color getColor() {
        return this.color;
    }

    private void payRent(Player player) {
        player.pay(this.getRentValue(), this.owner);
    } 

    public abstract int getRentValue();

    public abstract boolean isBuildable();

    @Override
    public void trigger(Player player) {
        if (this.isVacant()) {
            player.play(this.getCellId(), GameAction.BUY_CELL);
        } else {
            this.payRent(player);
        }
    }

}
