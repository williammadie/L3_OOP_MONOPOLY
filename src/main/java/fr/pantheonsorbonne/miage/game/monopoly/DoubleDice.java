package fr.pantheonsorbonne.miage.game.monopoly;

public class DoubleDice {
    private static final int MAX_DICE_VALUE = 6;
    private int value;
    private boolean hasDoubleValue;

    public DoubleDice() {
        int firstDiceValue = rollSingleDice();
        int secondDiceValue = rollSingleDice();
        this.value = firstDiceValue + secondDiceValue;
        this.hasDoubleValue = firstDiceValue == secondDiceValue;
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasDoubleValue() {
        return hasDoubleValue;
    }

    private static int rollSingleDice() {
        return GameLogic.getRandomNumberBetween(1, MAX_DICE_VALUE);
    }
}
