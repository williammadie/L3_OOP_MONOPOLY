package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class Cell {
    public static final int MAX_HOUSE_NUMBER = 4;
    private static int cellIdCounter = 0;
    protected String name;
    protected int cellId;

    protected Cell(String name) {
        this.name = name;
        this.cellId = cellIdCounter++;
    }

    public int getCellId() {
        return this.cellId;
    }

    public String getName() {
        return this.name;
    }

    public abstract void trigger(Player player);

    public abstract void buyCell(Player player) throws CellCannotBeBoughtException;

    public abstract void sellCell(Player player) throws CellCannotBeBoughtException;

    public abstract void buyHouse(Player player) throws CellCannotBeBuiltException;

    public abstract void sellHouse(Player player) throws CellCannotBeBuiltException;

    @Override
    public String toString() {
        return "{" + name + "}";
    }
}
