package fr.pantheonsorbonne.miage.game.monopoly.cell;

/**
 * This class produces exceptions which can be used when a purchase cannot be
 * done for any reason.
 */
public class CannotBuyException extends Exception {

    public CannotBuyException(String errorMessage) {
        super(errorMessage);
    }

}
