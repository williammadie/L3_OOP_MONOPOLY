package fr.pantheonsorbonne.miage.game.monopoly.player;

/**
 * This class produces exceptions which can be used in a desynchronized context.
 * A Desynchronized context is obtained when the local player attribute values
 * are different than the distant player attribute values.
 */
public class DesynchronizedPlayerException extends RuntimeException {

    public DesynchronizedPlayerException() {
        super("Distant player and local player might be desynchronized");
    }

}
