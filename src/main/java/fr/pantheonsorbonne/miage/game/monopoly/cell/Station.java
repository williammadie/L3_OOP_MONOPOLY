package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.Arrays;

public class Station extends Property {
    private static final int[] STATIONS_CELL_ID = new int[] { 5, 15, 25, 35 };

    public Station(String name, int price) {
        super(name, price, Color.COLORLESS);
    }

    @Override
    public int getRentValue() {
        int numberOfStationsOwned = (int) this.owner.getProperties().stream()
                .filter(x -> Arrays.asList(STATIONS_CELL_ID).contains(x)).count();
        switch (numberOfStationsOwned) {
            case 1:
                return 25;
            case 2:
                return 50;
            case 3:
                return 100;
            default:
                return 200;
        }
    }

    @Override
    public boolean isBuildable() {
        return false;
    }

}
