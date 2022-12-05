package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class AbstractSpecial extends AbstractCell {

    protected AbstractSpecial(String name) {
        super(name);
    }

    public void buyCell(Player player) throws CannotBuyException {
        throw new CannotBuyException("Cannot buy the cell " + super.name);
    }

    public void sellCell(Player player) throws CannotSellException {
        throw new CannotSellException("Cannot sell the cell " + super.name);
    }

    public void buyHouse(Player player) throws CannotBuildException {
        throw new CannotBuildException("Cannot build on cell " + super.name);
    }

    public void sellHouse(Player player) throws CannotBuildException {
        throw new CannotBuildException("Cannot sell house on cell " + super.name);
    }

    public void reset() {
    }
}
