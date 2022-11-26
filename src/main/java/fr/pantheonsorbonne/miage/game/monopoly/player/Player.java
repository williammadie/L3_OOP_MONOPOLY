package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;

public class Player {
    protected String id;
    private List<Property> properties;
    protected int pawnPosition;
    private int rank;
    private int balance;
    private boolean isJailed;

    public Player(String id) {
        this.id = id;
        this.properties = new ArrayList<>();
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
            this.makeChoice(GameAction.SELL_CELL);
            this.makeChoice(GameAction.SELL_HOUSE);
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


    public void pay(int moneyAmount, Player moneyReceiver) {
        this.removeMoney(moneyAmount);
        moneyReceiver.addMoney(moneyAmount);
    }

    public void makeChoice(GameAction possibleAction) {
        switch (possibleAction) {
            case BUY_HOUSE:
                // Build whenever he can
                if (properties.isEmpty())
                    return;
                int randInt = GameLogic.getRandomNumberBetween(0, properties.size()-1);
                Property property = this.properties.get(randInt);
                try {
                    property.buyHouse(this);
                } catch (CellCannotBeBuiltException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case BUY_CELL:
                // Buy whenever he can
                Cell currentCell = Board.getCellWithId(this.getPawnPosition());
                try {
                    currentCell.buyCell(this);
                } catch (CellCannotBeBoughtException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case SELL_CELL:
                // Sell the first cell in properties
                try {
                    this.properties.get(0).sellCell(this);
                } catch (CellCannotBeBoughtException e) {
                    e.printStackTrace();
                }
        }
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
        return "{id:" + this.id + ", rank:" + this.rank + ", balance:" + this.balance + ", pawnPosition:" + this.pawnPosition + ", properties:" + this.properties + "}";
    }
}
