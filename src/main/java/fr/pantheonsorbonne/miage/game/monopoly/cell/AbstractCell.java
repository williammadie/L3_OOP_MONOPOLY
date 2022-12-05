package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

/**
 * This class represents a single unit of the Monopoly board. There are 40 of
 * these units. Each one has its own values and specifications.
 */
public abstract class AbstractCell {
    public static final int MAX_HOUSE_NUMBER = 4;
    private static final int NO_PRICE = 0;
    private static int cellIdCounter = 0;
    protected String name;
    protected int cellId;

    protected AbstractCell(String name) {
        this.name = name;
        this.cellId = cellIdCounter++;
    }

    public int getCellId() {
        return this.cellId;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return Color.COLORLESS;
    }

    public int getPrice() {
        return NO_PRICE;
    }

    public Player getOwner() {
        return null;
    }

    /**
     * This is triggered each time a player lands on the cell.
     * 
     * @param player the player affected by the cell effects
     */
    public abstract void trigger(Player player);

    public abstract void buyCell(Player player) throws CannotBuyException;

    public abstract void sellCell(Player player) throws CannotSellException;

    public abstract void buyHouse(Player player) throws CannotBuildException;

    public abstract void sellHouse(Player player) throws CannotBuildException;

    /**
     * This resets cell attributes to their default value.
     */
    public abstract void reset();

    @Override
    public String toString() {
        return "{" + name + "}";
    }
}
