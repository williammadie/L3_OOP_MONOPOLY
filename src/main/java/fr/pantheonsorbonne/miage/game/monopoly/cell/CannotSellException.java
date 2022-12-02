package fr.pantheonsorbonne.miage.game.monopoly.cell;

/**
 * This class produces exceptions which can be used when a sale cannot be done
 * for any reason.
 */
public class CannotSellException extends Exception {

    public CannotSellException(String errorMessage) {
        super(errorMessage);
    }

}
