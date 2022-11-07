package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class PublicService extends Property {
    private static final int ELECTRICITY_COMPAGNY_CELL_ID = 12;
    private static final int WATER_COMPAGNY_CELL_ID = 28;

    public PublicService(String name, int price) {
        super(name, price);
    }

    @Override
    public int getRentValue(List<Player> players) {
        Player electricityOwner = GameLogic.findOwnerOfCell(ELECTRICITY_COMPAGNY_CELL_ID, players);
        Player waterOwner = GameLogic.findOwnerOfCell(WATER_COMPAGNY_CELL_ID, players);
        int rentMultiplier = waterOwner.equals(electricityOwner) ? 4 : 10;
        return new DoubleDice().getValue() * rentMultiplier;
    }

    @Override
    public boolean isBuildable() {
        return false;
    }
}
