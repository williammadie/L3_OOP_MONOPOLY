package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Strategy;

public class Player {
    protected String name;
    private List<Property> properties;
    private Strategy strategy;
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
        this.strategy = new Hybrid();
    }

    public Player(String name, Strategy strategy) {
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

    public List<Property> getProperties() {
        return this.properties;
    }

    public Set<Property> getProperties(Color color) {
        return this.properties.stream().filter(property -> property.getColor() == color).collect(Collectors.toSet());
    }

    public boolean getIsJailed() {
        return this.isJailed;
    }

    public void setIsJailed(boolean state) {
        this.isJailed = state;
    }

    public Strategy getStrategy() {
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

    public void addProperty(Property p) {
        if (p.isVacant()) {
            this.properties.add(p);
            p.setOwner(this);
        }
    }

    public void removeProperty(Property p) {
        this.properties.remove(p);
        p.setOwner(null);
    }

    public int getOwnedPropertyNumberWithColor(Color color) {
        return (int) this.getProperties().stream()
                .map(Property::getColor)
                .filter(propertyColor -> propertyColor == color).count();
    }

    public int countPlayerHouses() {
        return this.properties.stream().map(Property::getHouseNumber).mapToInt(Integer::intValue).sum();
    }

    public void pay(int moneyAmount, Player moneyReceiver) {
        this.removeMoneySafe(moneyAmount);
        moneyReceiver.addMoneySafe(moneyAmount);
    }

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

        this.infoLogger.append(this.getName() + " moves to cell n°" + cellId+ "\n");
        this.pawnPosition = cellId;
    }

    public void getStartingBonus(boolean isSafe) {
        if (this.isJailed || this.turnsPlayed > 50)
            return;
        
        this.infoLogger.append("New turn! " + this.getName() + " receives " + StartingPoint.MONEY_GIFT_AMOUNT + "Eur (" + this.turnsPlayed + ")\n");
        if (isSafe)
            this.addMoneySafe(StartingPoint.MONEY_GIFT_AMOUNT);
        else
            this.addMoney(StartingPoint.MONEY_GIFT_AMOUNT);
    }

    public int calculateBuyingWish(Player player, Color color) {

        if (color.equals(Color.COLORLESS))
            return 100;

        if (player.getProperties().size() < 3) {
            return Board.getNumberOfAdversaryOwnersForColor(player, color) > 0 ? 0 : 100;
        }

        return (getOwnedPropertyNumberWithColor(color) + 1 / Board.getExistingCellNumberWithColor(color)) * 100;
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


    public StringBuilder getInfoLogger(){
        return this.infoLogger;
    }

    public void printPlayerActions(){
        System.out.print(this.infoLogger.toString());
        infoLogger.setLength(0);
    }

    public String toString() {
        return "{name:" + this.name + ", strategy:" + this.strategy + ", balance:" + this.balance + ", pawnPosition:"
                + this.pawnPosition + ", properties:" + this.properties + "}";
    }
}
