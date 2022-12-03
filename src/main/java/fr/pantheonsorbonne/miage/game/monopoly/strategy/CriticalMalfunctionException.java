package fr.pantheonsorbonne.miage.game.monopoly.strategy;

/**
 * This class produces exceptions in critical situations where current game
 * cannot continues because of a very corrupted context. (For instance, if a
 * player cannot buy/sell anymore due to a potential internal exception).
 */
public class CriticalMalfunctionException extends RuntimeException {

    public CriticalMalfunctionException(String message) {
        super(message);
    }

}
