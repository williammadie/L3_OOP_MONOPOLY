package fr.pantheonsorbonne.miage.game.monopoly;

public class Cell {
    public static final int MAX_HOUSE_NUMBER = 4;
    private String name;
    private CellType type;
    private int price;
    private Color color;

    public Cell(String name, CellType type, int price, Color color) {
        this(name, type, price);
        this.color = color;
    }

    public Cell(String name, CellType type, int price) {
        this(name, type);
        this.price = price;
    }

    public Cell(String name, CellType type) {
        this.name = name;
        this.type = type;
    }

    public Color getColor() {
        return this.color;
    }

    public String toString() {
        return "{" + name + " " + type + " " + price + " " + color + "}";
    }
}
