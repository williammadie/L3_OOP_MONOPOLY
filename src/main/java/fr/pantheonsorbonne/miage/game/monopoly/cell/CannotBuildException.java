package fr.pantheonsorbonne.miage.game.monopoly.cell;

/**
 * This class produces exceptions which can be used when a construction cannot
 * be done for any reason.
 */
public class CannotBuildException extends Exception {

    public CannotBuildException(String errorMessage) {
        super(errorMessage);
    }

}
