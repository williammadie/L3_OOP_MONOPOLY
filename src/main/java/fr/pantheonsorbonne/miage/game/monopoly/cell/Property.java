package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class Property extends Cell {
    protected int price;
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

    public void setOwner(Player newOwner) {
        this.owner = newOwner;
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
        System.out.println(player.getId() + " has to pay " + this.getRentValue() + "Eur to " + this.owner);
        player.pay(this.getRentValue(), this.owner);
    }

    public abstract int getRentValue();

    @Override
    public void trigger(Player player) {
        if (this.isVacant()) {
            if (player.getBalance() < this.getPrice())
                return;

            player.makeChoice(GameAction.BUY_CELL);
        } else if (!this.getOwner().equals(player)) {
            this.payRent(player);
        }
    }

    @Override
    public void buyCell(Player player) throws CellCannotBeBoughtException {
        if (player.getBalance() < this.price)
            throw new CellCannotBeBoughtException("Player doesn't have required amount for buying.");

        if (!this.isVacant())
            throw new CellCannotBeBoughtException("Cell is already occupied.");

        System.out.println(player.getId() + " buys cell " + super.name);
        player.addProperty(this);
    }

    @Override
    public void sellCell(Player player) throws CellCannotBeBoughtException {
        if (!this.owner.equals(player))
            throw new CellCannotBeBoughtException("Cannot sell the cell " + super.name);

        player.removeProperty(this);
    }

    @Override
    public void buyHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot build on cell " + super.name);
    }

    @Override
    public void sellHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot sell house on cell " + super.name);
    }

    @Override
    public void reset() {
        this.owner = null;
    }

}
