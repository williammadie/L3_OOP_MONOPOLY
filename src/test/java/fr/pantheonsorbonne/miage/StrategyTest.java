package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuyException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AlwaysBuy;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyColorOnly;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;

class StrategyTest {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    void setUp() throws CannotBuyException {
        this.p1 = new Player("p1", new AlwaysBuy());
        this.p2 = new Player("p2", new BuyAbovePrice());
        this.p3 = new Player("p3", new BuyColorOnly(Color.ORANGE, Color.LIGHT_BLUE));
        this.p4 = new Player("p4", new Hybrid());
        this.p1.addMoney(20000);
        this.p2.addMoney(20000);
        this.p3.addMoney(20000);
        this.p4.addMoney(20000);
    }

    @Test
    void handleBuyCellTestTerrain() {
        this.p3.movePawnTo(9);
        Property property = (Property) Board.getCellWithId(9);
        this.p3.getStrategy().handleBuyCell(p3);
        assertTrue(p3.getProperties().contains(property));
    }

    @Test
    void handleBuyCellTestStation() {
        this.p3.movePawnTo(5);
        Property property = (Property) Board.getCellWithId(5);
        this.p3.getStrategy().handleBuyCell(p3);
        assertTrue(p3.getProperties().contains(property));
    }

    @Test
    void handleBuyCellTestBrownTerrain() {
        this.p3.movePawnTo(1);
        Property property = (Property) Board.getCellWithId(9);
        this.p3.getStrategy().handleBuyCell(p3);
        assertFalse(p3.getProperties().contains(property));
    }

    @Test
    void handleBuyCellTestAbovePrice() {
        this.p2.movePawnTo(34);
        Property property = (Property) Board.getCellWithId(34);
        this.p2.getStrategy().handleBuyCell(p2);
        assertTrue(p2.getProperties().contains(property));
    }

    @Test
    void handleBuyCellTestHybrid() {
        this.p3.movePawnTo(9);
        Property property = (Property) Board.getCellWithId(9);
        this.p3.getStrategy().handleBuyCell(p3);
        assertTrue(p3.getProperties().contains(property));
    }

    @Test
    void handleSellCellTest() {
        this.p1.movePawnTo(9);
        Property property = (Property) Board.getCellWithId(9);
        this.p1.getStrategy().handleBuyCell(p1);
        this.p1.getStrategy().handleSellCell(p1);
        assertFalse(p1.getProperties().contains(property));
    }

    @Test
    void handleBuyHouseTest() {
        this.p1.movePawnTo(9);
        int returnCode = this.p1.getStrategy().handleBuyHouse(p1);
        assertEquals(GameAction.ABORT_ACTION.value, returnCode);

        this.p1.getStrategy().handleBuyCell(p1);
        this.p1.movePawnTo(6);
        this.p1.getStrategy().handleBuyCell(p1);
        this.p1.movePawnTo(8);
        this.p1.getStrategy().handleBuyCell(p1);

        returnCode = this.p1.getStrategy().handleBuyHouse(p1);
        assertNotEquals(GameAction.ABORT_ACTION.value, returnCode);
        assertEquals(1, this.p1.countPlayerHouses());
    }

    @Test
    void handleSellHouseTest() {
        this.handleBuyHouseTest();
        int returnCode = this.p1.getStrategy().handleSellHouse(p1);
        assertNotEquals(GameAction.ABORT_ACTION.value, returnCode);
        assertEquals(0, this.p1.countPlayerHouses());
    }

    @AfterEach
    void tearDown() {
        Board.reset();
    }
}