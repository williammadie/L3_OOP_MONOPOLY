package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.AbstractProperty;
import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AbstractStrategy;

/**
 * This represent a local Monopoly player.
 */
public class Player {
    protected String name;
    private List<AbstractProperty> properties;
    protected AbstractStrategy strategy;
    protected int pawnPosition;
    private int rank;
    private int turnsPlayed;
    private int balance;
    private boolean isJailed;
    protected final StringBuilder infoLogger;

    public Player(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.pawnPosition = 0;
        this.rank = -1;
        this.turnsPlayed = 0;
        this.balance = 0;
        this.isJailed = false;
        this.infoLogger = new StringBuilder();
        this.strategy = null;
    }

    public Player(String name, AbstractStrategy strategy) {
        this(name);
        this.strategy = strategy;
    }

    public int rollDoubleDice() {
        return new DoubleDice().getValue();
    }

    public void rollDoubleDiceForRanking() {
        this.rank = rollDoubleDice();
    }

    public int getRank() {
        return this.rank;
    }

    /**
     * This randomly initializes the player strategy.
     */
    public void createStrategy() {
        this.strategy = GameLogic.getRandomStrategy();
    }

    public void refreshTurnsCounter() {
        this.turnsPlayed++;
    }

    public int getPawnPosition() {
        return this.pawnPosition;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return balance;
    }

    public List<AbstractProperty> getProperties() {
        return this.properties;
    }

    /**
     * This only returns the properties owned by the player with a given color.
     * 
     * @param color
     * @return a set of properties matching the given color
     */
    public Set<AbstractProperty> getProperties(Color color) {
        return this.properties.stream().filter(property -> property.getColor() == color).collect(Collectors.toSet());
    }

    public boolean getIsJailed() {
        return this.isJailed;
    }

    public void setIsJailed(boolean state) {
        this.isJailed = state;
    }

    public AbstractStrategy getStrategy() {
        return this.strategy;
    }

    public boolean isBankrupt() {
        return balance <= 0 && properties.isEmpty();
    }

    public final void addMoney(int price) {
        balance += price;
    }

    public void addMoneySafe(int price) {
        this.addMoney(price);
    }

    public final void removeMoney(int price) {
        while (this.getBalance() < price) {
            if (this.properties.isEmpty())
                break;

            if (countPlayerHouses() != 0)
                this.makeChoice(GameAction.SELL_HOUSE);
            else
                this.makeChoice(GameAction.SELL_CELL);
        }
        balance -= price;
    }

    public void removeMoneySafe(int price) {
        this.removeMoney(price);
    }

    public void addProperty(AbstractProperty p) {
        if (p.isVacant()) {
            this.properties.add(p);
            p.setOwner(this);
        }
    }

    public void removeProperty(AbstractProperty p) {
        this.properties.remove(p);
        p.setOwner(null);
    }

    /**
     * This returns the total number of player properties with a given color.
     * 
     * @param color
     * @return an integer representing the total number of player properties with a
     *         given color
     */
    public int getOwnedPropertyNumberWithColor(Color color) {
        return (int) this.getProperties().stream()
                .map(AbstractProperty::getColor)
                .filter(propertyColor -> propertyColor == color).count();
    }

    /**
     * This returns the total number of houses owned by the player.
     * 
     * @return the total number of houses owned by the player
     */
    public int countPlayerHouses() {
        return this.properties.stream().map(AbstractProperty::getHouseNumber).mapToInt(Integer::intValue).sum();
    }

    public void pay(int moneyAmount, Player moneyReceiver) {
        this.removeMoneySafe(moneyAmount);
        moneyReceiver.addMoneySafe(moneyAmount);
    }

    /**
     * This method is called when the player has to make a choice between doing an
     * action or doing nothing. It allows the player to use a strategy. For
     * instance, if the player has the opportunity to
     * buy a cell, this will be called with the parameter BUY_CELL.
     * 
     * @param possibleAction an action among BUY_CELL, SELL_CELL, BUY_HOUSE and
     *                       SELL_HOUSE
     */
    public void makeChoice(GameAction possibleAction) {
        this.strategy.makeChoice(possibleAction, this);
    }

    public void movePawnOf(int numberOfCells) {
        int nextPawnPosition = (this.pawnPosition + numberOfCells) % Board.BOARD_LENGTH;
        movePawnTo(nextPawnPosition);
    }

    public void movePawnTo(int cellId) {
        if (cellId < this.pawnPosition && cellId != 0)
            getStartingBonus(false);

        this.infoLogger.append(this.getName() + " moves to cell n°" + cellId + "\n");
        this.pawnPosition = cellId;
    }

    public void getStartingBonus(boolean isSafe) {
        if (this.isJailed || this.turnsPlayed > StartingPoint.NB_TURNS_WITH_START_BONUS)
            return;

        this.infoLogger
                .append("New turn! " + this.getName() + " receives " + StartingPoint.MONEY_GIFT_AMOUNT + "Eur\n");
        if (isSafe)
            this.addMoneySafe(StartingPoint.MONEY_GIFT_AMOUNT);
        else
            this.addMoney(StartingPoint.MONEY_GIFT_AMOUNT);
    }

    public boolean isSynchronized() {
        return true;
    }

    public void declareGameOver(String status) {
        if (status.equals("winner"))
            System.out.println(this.name + " wins the game!");
        else
            System.out.println(this.name + " went bankrupt!");
    }

    public StringBuilder getInfoLogger() {
        return this.infoLogger;
    }

    public void printPlayerActions() {
        System.out.print(this.infoLogger.toString());
        infoLogger.setLength(0);
    }

    public String toString() {
        return "{name:" + this.name + ", strategy:" + this.strategy + ", balance:" + this.balance + ", pawnPosition:"
                + this.pawnPosition + ", properties:" + this.properties + "}";
    }
}
