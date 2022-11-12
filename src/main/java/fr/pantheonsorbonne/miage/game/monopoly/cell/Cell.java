package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.Player;

public abstract class Cell {
    public static final int MAX_HOUSE_NUMBER = 4;
    private static int cellIdCounter = 0;
    private String name;
    protected int cellId;

    protected Cell(String name) {
        this.name = name;
        this.cellId = cellIdCounter++;
    }

    public int getCellId() {
        return this.cellId;
    }

    public abstract void trigger(Player player);

    @Override
    public String toString() {
        return "{" + name + "}";
    }
}