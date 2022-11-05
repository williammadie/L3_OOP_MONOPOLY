package fr.pantheonsorbonne.miage.game.monopoly;

public class Player {
    private int rank;
    private int id;

    public Player(int id) {
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "{id:" + this.id + ", rank:" + this.rank + "}";
    }
}
