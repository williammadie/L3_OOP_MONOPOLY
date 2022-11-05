package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.Random;

public class DoubleDice {
    private int value;
    private boolean hasDoubleValue;

    public DoubleDice() {
        Random rand = new Random();
        int firstDiceValue = rand.nextInt(6);
        int secondDiceValue = rand.nextInt(6);
        this.value = firstDiceValue + secondDiceValue;
        this.hasDoubleValue = firstDiceValue == secondDiceValue;
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasDoubleValue() {
        return hasDoubleValue;
    }
}
