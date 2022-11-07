package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class Station extends Property {
    private static final int[] STATIONS_CELL_ID = new int[] { 5, 15, 25, 35 };

    public Station(String name, int price) {
        super(name, price);
    }

    @Override
    public int getRentValue(List<Player> players) {
        Player[] stationOwners = new Player[] {
                GameLogic.findOwnerOfCell(STATIONS_CELL_ID[0], players),
                GameLogic.findOwnerOfCell(STATIONS_CELL_ID[1], players),
                GameLogic.findOwnerOfCell(STATIONS_CELL_ID[2], players),
                GameLogic.findOwnerOfCell(STATIONS_CELL_ID[3], players)
        };
        Player ownerOfCurrentStation = GameLogic.findOwnerOfCell(super.getCellId(), players);
        int numberOfStationsOwned = (int) Arrays.stream(stationOwners).filter(x -> x.equals(ownerOfCurrentStation))
                .count();
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
