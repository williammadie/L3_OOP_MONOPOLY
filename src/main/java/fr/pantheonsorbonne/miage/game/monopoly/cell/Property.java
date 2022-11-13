package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

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

    public int getPrice() {
        return this.price;
    }

    private void payRent(Player player) {
        player.pay(this.getRentValue(), this.owner);
    }

    public abstract int getRentValue();

    public abstract boolean isBuildable();

    @Override
    public void trigger(Player player) {
        if (this.isVacant()) {
            player.makeChoice(GameAction.BUY_CELL);
        } else {
            this.payRent(player);
        }
    }

    @Override
    public void buyCell(Player player) throws CellCannotBeBoughtException {
        if (player.getBalance() < this.price)
            throw new CellCannotBeBoughtException("Player doesn't have required amount for buying.");

        this.owner = player;
    }

    @Override
    public void sellCell(Player player) throws CellCannotBeBoughtException {
        throw new CellCannotBeBoughtException("Cannot sell the cell " + super.name);
    }

    @Override
    public void buyHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot build on cell " + super.name);
    }

    @Override
    public void sellHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot sell house on cell " + super.name);
    }

}
