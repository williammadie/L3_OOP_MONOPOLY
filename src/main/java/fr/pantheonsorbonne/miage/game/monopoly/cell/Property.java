package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class Property extends Cell {
    public static final double SELLING_PRICE_COEFFICIENT = 0.75;
    private static final int NO_HOUSE = 0;
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

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    public int getHouseNumber() {
        return NO_HOUSE;
    }

    private void payRent(Player player) {
        System.out.println(player.getName() + " has to pay " + this.getRentValue() + "Eur to " + this.owner.getName());
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

        player.removeMoney(price);
        System.out.println(player.getName() + " buys cell " + super.name + " for " + this.price + "Eur");
        player.addProperty(this);
    }

    @Override
    public void sellCell(Player player) throws CellCannotBeSoldException {
        if (!this.owner.equals(player))
            throw new CellCannotBeSoldException(
                    "Cell " + super.name + " does not belong to player " + player.getName());

        double sellingPrice = this.price * Property.SELLING_PRICE_COEFFICIENT;
        player.addMoney((int) sellingPrice);
        System.out.println(player.getName() + " sells " + this.name + " for " + sellingPrice + "Eur");
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
