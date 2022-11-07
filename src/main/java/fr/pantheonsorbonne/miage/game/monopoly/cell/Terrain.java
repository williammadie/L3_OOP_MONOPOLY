package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.Color;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class Terrain extends Property {
    private Color color;
    private int[] rent;
    private int houseNumber;

    public Terrain(String name, int price, Color color, int[] rent) {
        super(name, price);
        this.color = color;
        this.rent = rent;
        this.houseNumber = 0;
    }

    public Color getColor() {
        return this.color;
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
    public int getRentValue(List<Player> players) {
        Player cellOwner = GameLogic.findOwnerOfCell(this.getCellId(), players);

        if (GameLogic.doPlayerHasAllCellOfColor(cellOwner, this.color)) {
            return rent[this.houseNumber++];
        } else {
            return rent[0];
        }

    }

    @Override
    public boolean isBuildable() {
        return true;
    }

}
