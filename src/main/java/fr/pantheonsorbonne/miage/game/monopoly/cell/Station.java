package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.Arrays;
import java.util.List;

public class Station extends Property {
    private static final List<Integer> STATIONS_CELL_ID = Arrays.asList(5, 15, 25, 35);

    public Station(String name, int price) {
        super(name, price, Color.COLORLESS);
    }

    @Override
    public int getRentValue() {
        int numberOfStationsOwned = (int) this.owner.getProperties().stream()
                .filter(x -> STATIONS_CELL_ID.contains(x.getCellId())).count();
        System.out.println(this.owner.getName() + " has " + numberOfStationsOwned + " stations");
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

}
