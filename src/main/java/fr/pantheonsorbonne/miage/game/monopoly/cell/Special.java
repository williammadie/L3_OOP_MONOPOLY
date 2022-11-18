package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public abstract class Special extends Cell {

    protected Special(String name) {
        super(name);
    }

    public void buyCell(Player player) throws CellCannotBeBoughtException {
        throw new CellCannotBeBoughtException("Cannot buy the cell " + super.name);
    }

    public void sellCell(Player player) throws CellCannotBeBoughtException {
        throw new CellCannotBeBoughtException("Cannot sell the cell " + super.name);
    }

    public void buyHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot build on cell " + super.name);
    }

    public void sellHouse(Player player) throws CellCannotBeBuiltException {
        throw new CellCannotBeBuiltException("Cannot sell house on cell " + super.name);
    }

    public void reset() {
        return;
    }
}
