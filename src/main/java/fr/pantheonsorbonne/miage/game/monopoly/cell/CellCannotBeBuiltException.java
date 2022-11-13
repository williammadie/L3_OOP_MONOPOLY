package fr.pantheonsorbonne.miage.game.monopoly.cell;

public class CellCannotBeBuiltException extends Exception {
    public CellCannotBeBuiltException(String errorMessage) {
        super(errorMessage);
    }

}
