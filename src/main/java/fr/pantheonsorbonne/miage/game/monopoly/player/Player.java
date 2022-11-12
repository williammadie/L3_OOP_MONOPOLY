package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;

public class Player {
    private String id;
    private Set<Property> properties;
    private int pawnPosition;
    private int rank;
    private int balance;
    private boolean isJailed;

    public Player(String id) {
        this.id = id;
        this.properties = new HashSet<>();
        this.pawnPosition = 0;
        this.rank = -1;
        this.balance = 0;
        this.isJailed = false;
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

    public String getId() {
        return this.id;
    }

    public int getCredit() {
        return balance;
    }

    public Set<Property> getProperties() {
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
        return balance == 0 && properties.isEmpty();
    }

    public void addMoney(int price) {
        balance += price;
    }

    public void removeMoney(int price) {
        balance -= price;
    }

    public void addProperty(Property p) {
        if (p.isVacant() && balance >= p.getPrice()) {
            this.balance -= p.getPrice();
        }
    }

    public void removeProperty(Property p) {
        this.properties.remove(p);
    }

    public void pay(int moneyAmount, Player moneyReceiver) {
        this.removeMoney(moneyAmount);
        moneyReceiver.addMoney(moneyAmount);
    }

    public void play(int cellId, GameAction buyCell) {
    }

    public void movePawnOf(int numberOfCells) {
        int nextPawnPosition = (this.pawnPosition + numberOfCells) % Board.BOARD_LENGTH;
        movePawnTo(nextPawnPosition);
    }

    public void movePawnTo(int cellId) {
        if (cellId < this.pawnPosition && cellId != 0)
            getStartingBonus();

        this.pawnPosition = cellId;
    }

    public void getStartingBonus() {
        if (this.isJailed)
            return;

        this.addMoney(StartingPoint.MONEY_GIFT_AMOUNT);
    }

    public String toString() {
        return "{id:" + this.id + ", rank:" + this.rank + "}";
    }
}
