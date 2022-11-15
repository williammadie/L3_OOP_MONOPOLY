package fr.pantheonsorbonne.miage.game.monopoly.cell;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;

public class PublicService extends Property {
    private static final int ELECTRICITY_COMPANY_CELL_ID = 12;
    private static final int WATER_COMPANY_CELL_ID = 28;

    public PublicService(String name, int price) {
        super(name, price, Color.COLORLESS);
    }

    @Override
    public int getRentValue() {
        boolean doAllPublicServiceBelongToOwner;
        if (this.cellId == ELECTRICITY_COMPANY_CELL_ID) {
            doAllPublicServiceBelongToOwner = this.owner.getProperties().stream()
                    .anyMatch(x -> x.getCellId() == WATER_COMPANY_CELL_ID);
        } else {
            doAllPublicServiceBelongToOwner = this.owner.getProperties().stream()
                    .anyMatch(x -> x.getCellId() == ELECTRICITY_COMPANY_CELL_ID);
        }   
        int rentMultiplier = doAllPublicServiceBelongToOwner ? 10 : 4;
        return new DoubleDice().getValue() * rentMultiplier;
    }
}
