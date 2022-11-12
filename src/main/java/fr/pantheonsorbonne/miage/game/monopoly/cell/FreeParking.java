package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class FreeParking extends Cell {

    public FreeParking(String name) {
        super(name);
    }

    @Override
    public void trigger(Player player) {
      // Nothing happens
    }

}
