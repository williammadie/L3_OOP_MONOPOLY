package fr.pantheonsorbonne.miage.game.monopoly.player;

/**
 * This class produces exceptions which can be used when an unexpected network
 * command is received. "Unexpected" generally means this command doesn't have
 * sense at this particular moment.
 */
public class UnexpectedCommandException extends RuntimeException {

    public UnexpectedCommandException(String commandName) {
        super("Unexpected GameCommand: " + commandName);
    }

}
