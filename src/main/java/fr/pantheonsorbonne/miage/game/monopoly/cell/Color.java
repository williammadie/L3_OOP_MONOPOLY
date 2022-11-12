package fr.pantheonsorbonne.miage.game.monopoly.cell;

public enum Color {
    BROWN(2),
    LIGHT_BLUE(3),
    PINK(3),
    ORANGE(3),
    RED(3),
    YELLOW(3),
    GREEN(3),
    DEEP_BLUE(2),
    COLORLESS(-1);

    public final int terrainNumber;

    private Color(int terrainNumber) {
        this.terrainNumber = terrainNumber;
    }
}
