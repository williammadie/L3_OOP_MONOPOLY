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
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AlwaysBuy;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Strategy;

public class Player {
    protected String name;
    private List<Property> properties;
    private Strategy strategy;
    protected int pawnPosition;
    private int rank;
    private int balance;
    private boolean isJailed;

    public Player(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.pawnPosition = 0;
        this.rank = -1;
        this.balance = 0;
        this.isJailed = false;
        this.strategy = new AlwaysBuy();
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

    public boolean isBankrupt() {
        return balance <= 0 && properties.isEmpty();
    }


    public void addMoney(int price) {
        balance += price;
    }

    public void removeMoney(int price) {
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

    public void addProperty(Property p) {
        if (p.isVacant() && balance >= p.getPrice()) {
            this.balance -= p.getPrice();
            this.properties.add(p);
            p.setOwner(this);
        }
    }

    public void removeProperty(Property p) {
        this.properties.remove(p);
        p.setOwner(null);
    }

    public int countPlayerHouses() {
        return this.properties.stream().map(Property::getHouseNumber).mapToInt(Integer::intValue).sum();
    }

    public void pay(int moneyAmount, Player moneyReceiver) {
        this.removeMoney(moneyAmount);
        moneyReceiver.addMoney(moneyAmount);
    }

    public void makeChoice(GameAction possibleAction) {
        this.strategy.makeChoice(possibleAction, this);
    }

    public void getStartingBonus() {
        if (this.isJailed)
            return;
        this.addMoney(StartingPoint.MONEY_GIFT_AMOUNT);
    }

    public void movePawnTo(int cellId) {
        if (cellId < this.pawnPosition && cellId != 0)
            getStartingBonus();

        System.out.println(this.getName() + " moves to cell n°" + cellId);
        this.pawnPosition = cellId;
    }

    public void movePawnOf(int numberOfCells) {
        int nextPawnPosition = (this.pawnPosition + numberOfCells) % Board.BOARD_LENGTH;
        movePawnTo(nextPawnPosition);
    }

    public String toString() {
        return "{name:" + this.name + ", strategy:" + this.strategy + ", balance:" + this.balance + ", pawnPosition:"
                + this.pawnPosition + ", properties:" + this.properties + "}";
    }
}
