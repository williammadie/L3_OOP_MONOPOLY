package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.HashSet;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class Terrain extends Property {
    private int[] rent;
    private int houseNumber;

    public Terrain(String name, int price, Color color, int[] rent) {
        super(name, price, color);
        this.color = color;
        this.rent = rent;
        this.houseNumber = 0;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    public void buildNewHouse() {
        this.houseNumber++;
    }

    public int getHousePrice() {
        switch (this.color) {
            case BROWN:
            case LIGHT_BLUE:
                return 50;
            case PINK:
            case ORANGE:
                return 100;
            case RED:
            case YELLOW:
                return 150;
            default:
                return 200;
        }
    }

    @Override
    public int getRentValue() {
        if (doPlayerHasAllTerrainOfColor(this.owner, this.color)) {
            return rent[this.houseNumber++];
        } else {
            return rent[0];
        }

    }

    public boolean isBuildable() {
        if (!doPlayerHasAllTerrainOfColor(this.owner, this.color))
            return false;

        if (this.houseNumber == Cell.MAX_HOUSE_NUMBER)
            return false;

        return doOtherCellsOfColorHaveAtLeast(this.houseNumber);
    }

    private static boolean doPlayerHasAllTerrainOfColor(Player player, Color color) {
        int propertyNumberWithGivenColor = (int) player.getProperties().stream()
                .map(property -> property.getColor())
                .filter(propertyColor -> propertyColor == color).count();
        switch (color) {
            case BROWN:
            case DEEP_BLUE:
                return propertyNumberWithGivenColor == 2;
            default:
                return propertyNumberWithGivenColor == 3;
        }
    }

    public boolean doOtherCellsOfColorHaveAtLeast(int requiredMinimumHouseNumber) {
        boolean result = true;
        for (Property property : this.owner.getProperties(this.color)) {
            Terrain terrain = (Terrain) property;
            if (terrain.getHouseNumber() != requiredMinimumHouseNumber)
                result = false;
        }
        return result;
    }
}
