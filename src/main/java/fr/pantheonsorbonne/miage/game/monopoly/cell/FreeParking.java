package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class FreeParking extends AbstractSpecial {

  public FreeParking(String name) {
    super(name);
  }

  @Override
  public void trigger(Player player) {
    // Nothing happens
  }

}
