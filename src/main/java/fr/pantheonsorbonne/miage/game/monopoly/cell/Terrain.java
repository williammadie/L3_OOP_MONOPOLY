package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class Terrain extends AbstractProperty {
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

    /**
     * Depending on their location on the board, house prices will vary. This
     * returns the correct price for houses taking location into account.
     * 
     * @return
     */
    public int getHousePrice() {
        int housePrice;
        switch (this.color) {
            case BROWN:
            case LIGHT_BLUE:
                housePrice = 50;
                break;
            case PINK:
            case ORANGE:
                housePrice = 100;
                break;
            case RED:
            case YELLOW:
                housePrice = 150;
                break;
            default:
                housePrice = 200;
                break;
        }
        return housePrice;
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
        if (this.owner == null || !doPlayerHasAllTerrainOfColor(this.owner, this.color))
            return false;

        if (this.houseNumber >= AbstractCell.MAX_HOUSE_NUMBER)
            return false;

        return doOtherCellsOfColorHaveAtLeast(this.houseNumber);
    }

    private boolean isSoldable() {
        if (this.houseNumber == 0)
            return false;

        return doOtherCellsOfColorHaveAtMost(this.houseNumber);
    }

    private static boolean doPlayerHasAllTerrainOfColor(Player player, Color color) {
        int propertyNumberWithGivenColor = player.getOwnedPropertyNumberWithColor(color);
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
        for (AbstractProperty property : this.owner.getProperties(this.color)) {
            if (property.getHouseNumber() < minimumRequiredHouseNumber) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean doOtherCellsOfColorHaveAtMost(int maximumRequiredHouseNumber) {
        boolean result = true;
        for (AbstractProperty property : this.owner.getProperties(this.color)) {
            if (property.getHouseNumber() > maximumRequiredHouseNumber) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void buyHouse(Player player) throws CannotBuildException {
        if (player.getBalance() < this.color.housePrice)
            throw new CannotBuildException("Player doesn't have required amount for buying a house.");

        if (!this.isBuildable())
            throw new CannotBuildException("Cell " + super.name + " is unbuildable");

        player.removeMoney(getHousePrice());
        player.getInfoLogger().append(player.getName() + " buys a new house at cell " + super.name + "\n");
        this.buildNewHouse();
    }

    @Override
    public void sellHouse(Player player) throws CannotBuildException {
        if (this.houseNumber == 0)
            throw new CannotBuildException("Cannot sell house on cell " + super.name);

        if (!this.isSoldable())
            throw new CannotBuildException("Cell " + super.name + " is unsoldable");

        double sellingPrice = getHousePrice() * AbstractProperty.SELLING_PRICE_COEFFICIENT;
        player.addMoney((int) sellingPrice);
        player.getInfoLogger()
                .append(player.getName() + " sells a house at cell " + super.name + " for " + sellingPrice + "Eur\n");
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
