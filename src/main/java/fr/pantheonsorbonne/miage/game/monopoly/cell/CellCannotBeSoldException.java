package fr.pantheonsorbonne.miage.game.monopoly.cell;

public class CellCannotBeSoldException extends Exception {
    public CellCannotBeSoldException(String errorMessage) {
        super(errorMessage);
    }

}
