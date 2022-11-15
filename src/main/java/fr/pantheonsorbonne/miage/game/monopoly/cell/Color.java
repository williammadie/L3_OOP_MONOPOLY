package fr.pantheonsorbonne.miage.game.monopoly.cell;

public enum Color {
    BROWN(2, 50),
    LIGHT_BLUE(3, 50),
    PINK(3, 100),
    ORANGE(3, 100),
    RED(3, 150),
    YELLOW(3, 150),
    GREEN(3, 200),
    DEEP_BLUE(2, 200),
    COLORLESS(-1, -1);

    public final int terrainNumber;
    public final int housePrice;

    private Color(int terrainNumber, int housePrice) {
        this.terrainNumber = terrainNumber;
        this.housePrice = housePrice;
    }
}
