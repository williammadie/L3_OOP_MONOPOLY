package fr.pantheonsorbonne.miage.game.monopoly;

public abstract class Player {
    private int pawnPosition;
    private int rank;
    private int id;
	private int balance;
    private ArrayList<Property> properties;


    protected Player(int id) {
        this.id = id;
        this.properties= new ArrayList<>();
        this.pawnPosition=0;
        this.rank=-1;
        this.balance=0;

    }

    public int rollDoubleDice() {
        return new DoubleDice().getValue();
    }

    public void rollDoubleDiceForRanking() {
        this.rank = rollDoubleDice();
        this.pawnPosition=this.pawnPosition+this.rank;
        if(this.pawnPosition>41){
            this.pawnPosition=this.pawnPosition-41;
        }
    }

    public int getRank() {
        return this.rank;
    }

    public int getPawnOf() {
        return this.pawnPosition;
    }

    public int getId() {
        return this.id;
    }

	public int getCredit(){
		return (balance);
	}

    public boolean isBankRupt(){
		return balance==0;
	}

    public void addMoney(int price){
		balance+=price;
	}

    public void removeMoney(int price){
		balance-=price;
	}
   
	public void addProperty(Property p){
		if ( p.canBeBought() && balance>=p.getRentValue() ){
			balance-=p.getPrix();
		}
	}

    public void removeProperty(Porperty p){


    }

    public String toString() {
        return "{id:" + this.id + ", rank:" + this.rank + "}";
    }
}
