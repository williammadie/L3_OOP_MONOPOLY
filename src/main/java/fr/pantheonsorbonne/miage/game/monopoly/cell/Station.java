package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.Arrays;
import java.util.List;

public class Station extends AbstractProperty {
    private static final List<Integer> STATIONS_CELL_ID = Arrays.asList(5, 15, 25, 35);

    public Station(String name, int price) {
        super(name, price, Color.COLORLESS);
    }

    @Override
    public int getRentValue() {
        int numberOfStationsOwned = (int) this.owner.getProperties().stream()
                .filter(x -> STATIONS_CELL_ID.contains(x.getCellId())).count();
        int rentAmount;
        switch (numberOfStationsOwned) {
            case 1:
                rentAmount = 25;
                break;
            case 2:
                rentAmount = 50;
                break;
            case 3:
                rentAmount = 100;
                break;
            default:
                rentAmount = 200;
                break;
        }
        return rentAmount;
    }

}
