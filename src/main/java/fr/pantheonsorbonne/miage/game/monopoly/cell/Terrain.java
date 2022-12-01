package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Terrain extends Property {
    private int[] rent;
    private int houseNumber;

    public Terrain(String name, int price, Color color, int[] rent) {
        super(name, price, color);
        this.color = color;
        this.rent = rent;
        this.houseNumber = 0;
    }

    @Override
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
            return rent[this.houseNumber + 1];
        } else {
            return rent[0];
        }

    }

    private boolean isBuildable() {
        if (this.owner == null)
            return false;

        if (!doPlayerHasAllTerrainOfColor(this.owner, this.color))
            return false;

        if (this.houseNumber >= Cell.MAX_HOUSE_NUMBER)
            return false;

        return doOtherCellsOfColorHaveAtLeast(this.houseNumber);
    }

    private boolean isSoldable() {
        if (this.houseNumber == 0)
            return false;

        return doOtherCellsOfColorHaveAtMost(this.houseNumber);
    }

    private static boolean doPlayerHasAllTerrainOfColor(Player player, Color color) {
        int propertyNumberWithGivenColor = (int) player.getProperties().stream()
                .map(Property::getColor)
                .filter(propertyColor -> propertyColor == color).count();
        switch (color) {
            case BROWN:
            case DEEP_BLUE:
                return propertyNumberWithGivenColor == 2;
            default:
                return propertyNumberWithGivenColor == 3;
        }
    }

    public boolean doOtherCellsOfColorHaveAtLeast(int minimumRequiredHouseNumber) {
        boolean result = true;
        for (Property property : this.owner.getProperties(this.color)) {
            if (property.getHouseNumber() < minimumRequiredHouseNumber) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean doOtherCellsOfColorHaveAtMost(int maximumRequiredHouseNumber) {
        boolean result = true;
        for (Property property : this.owner.getProperties(this.color)) {
            if (property.getHouseNumber() > maximumRequiredHouseNumber) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void buyHouse(Player player) throws CellCannotBeBuiltException {
        if (player.getBalance() < this.color.housePrice)
            throw new CellCannotBeBuiltException("Player doesn't have required amount for buying a house.");

        if (!this.isBuildable())
            throw new CellCannotBeBuiltException("Cell " + super.name + " is unbuildable");

        player.removeMoney(getHousePrice());
        System.out.println(player.getName() + " buys a new house at cell " + super.name);
        this.buildNewHouse();
    }

    @Override
    public void sellHouse(Player player) throws CellCannotBeBuiltException {
        if (this.houseNumber == 0)
            throw new CellCannotBeBuiltException("Cannot sell house on cell " + super.name);

        if (!this.isSoldable())
            throw new CellCannotBeBuiltException("Cell " + super.name + " is unsoldable");

        double sellingPrice = getHousePrice() * Property.SELLING_PRICE_COEFFICIENT;
        System.out.println("Sell House");
        player.addMoney((int) sellingPrice);
        System.out.println(player.getName() + " sells a house at cell " + super.name + " for " + sellingPrice + "Eur");
        this.houseNumber--;
    }

    @Override
    public void reset() {
        super.reset();
        this.houseNumber = 0;
    }

    @Override
    public String toString() {
        return "{" + name + "," + price + "Eur," + color + "," + houseNumber + "}";
    }

}
