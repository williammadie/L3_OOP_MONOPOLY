package fr.pantheonsorbonne.miage.game.monopoly.cell;

public class CellCannotBeBoughtException extends Exception {
    public CellCannotBeBoughtException(String errorMessage) {
        super(errorMessage);
    }

}
