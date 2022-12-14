package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuyException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuildException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.AbstractProperty;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

class PlayerTest {
    private Player player;
    private Player adversary;

    @BeforeEach
    void setUp() throws CannotBuyException {
        Board.reset();
        this.player = new Player("testPlayer");
        this.adversary = new Player("testAdversary");
        player.addMoney(20000);
        adversary.addMoney(20000);
    }

    @Test
    void testAddMoney() {
        int previousBalanceAmount = player.getBalance();
        player.addMoney(200);
        assertEquals(previousBalanceAmount + 200, player.getBalance());
    }

    @Test
    void testremoveMoney() {
        int previousBalanceAmount = player.getBalance();
        player.removeMoney(300);
        assertEquals(previousBalanceAmount - 300, player.getBalance());
    }

    @Test
    void testAddProperty() {
        AbstractProperty newProperty = (AbstractProperty) Board.getCellWithId(39);
        player.addProperty(newProperty);
        assertTrue(player.getProperties().contains(newProperty));
    }

    @Test
    void testRemoveProperty() {
        AbstractProperty otherProperty = (AbstractProperty) Board.getCellWithId(39);
        player.addProperty(otherProperty);
        player.removeProperty(otherProperty);
        assertFalse(player.getProperties().contains(otherProperty));
    }

    @Test
    void testGetPropertiesColor() {
        AbstractProperty newProperty = (AbstractProperty) Board.getCellWithId(39);
        AbstractProperty newProperty2 = (AbstractProperty) Board.getCellWithId(37);
        player.addProperty(newProperty);
        player.addProperty(newProperty2);

        assertTrue(player.getProperties(Color.ORANGE).isEmpty());
        List<AbstractProperty> expectedProperties = Arrays.asList(newProperty, newProperty2);
        List<AbstractProperty> actualProperties = player.getProperties(Color.DEEP_BLUE).stream().toList();
        assertEquals(expectedProperties.size(), actualProperties.size());
        assertTrue(expectedProperties.containsAll(actualProperties));
    }

    @Test
    void testIsBankRupt() {
        player.removeMoney(20000);
        player.getProperties().clear();
        assertTrue(player.isBankrupt());
    }

    @Test
    void testCountPlayerHouses() throws CannotBuildException {
        AbstractProperty p1 = (AbstractProperty) Board.getCellWithId(6);
        AbstractProperty p2 = (AbstractProperty) Board.getCellWithId(8);
        AbstractProperty p3 = (AbstractProperty) Board.getCellWithId(9);
        player.addProperty(p1);
        player.addProperty(p2);
        player.addProperty(p3);
        assertEquals(0, player.countPlayerHouses());
        p1.buyHouse(player);
        p2.buyHouse(player);
        p3.buyHouse(player);
        assertEquals(3, player.countPlayerHouses());
    }

    @Test
    void testPay() {
        int previousBalanceAmount = player.getBalance();
        int previousBalanceAmountReceiver = adversary.getBalance();
        player.pay(2000, adversary);
        assertEquals(previousBalanceAmount - 2000, player.getBalance());
        assertEquals(previousBalanceAmountReceiver + 2000, adversary.getBalance());
    }

    @Test
    void testMovePawnOf() {
        int previousPosition = player.getPawnPosition();
        player.movePawnOf(2);
        assertEquals(previousPosition + 2, player.getPawnPosition());
    }

    @Test
    void testMovePawnTo() {
        player.movePawnTo(6);
        assertEquals(6, player.getPawnPosition());

    }

    @Test
    void testgetStartingBonus() {
        int previousBalanceAmount = player.getBalance();
        player.getStartingBonus(false);
        assertEquals(previousBalanceAmount + 200, player.getBalance());
    }

    @AfterEach
    void tearDown() {
        Board.reset();
    }

}
