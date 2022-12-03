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
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class PlayerTest {
    private Player player;
    private Player adversary;

    @BeforeEach
    void setUp() throws CellCannotBeBoughtException {
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
        Property newProperty = (Property) Board.getCellWithId(39);
        player.addProperty(newProperty);
        assertTrue(player.getProperties().contains(newProperty));
    }

    @Test
    void testRemoveProperty() {
        Property otherProperty = (Property) Board.getCellWithId(39);
        player.addProperty(otherProperty);
        player.removeProperty(otherProperty);
        assertFalse(player.getProperties().contains(otherProperty));
    }

    @Test
    void testGetPropertiesColor() {
        Property newProperty = (Property) Board.getCellWithId(39);
        Property newProperty2 = (Property) Board.getCellWithId(37);
        player.addProperty(newProperty);
        player.addProperty(newProperty2);

        assertTrue(player.getProperties(Color.ORANGE).isEmpty());
        List<Property> expectedProperties = Arrays.asList(newProperty, newProperty2);
        List<Property> actualProperties = player.getProperties(Color.DEEP_BLUE).stream().toList();
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
    void testCountPlayerHouses() throws CellCannotBeBuiltException {
        Property p1 = (Property) Board.getCellWithId(6);
        Property p2 = (Property) Board.getCellWithId(8);
        Property p3 = (Property) Board.getCellWithId(9);
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
        player.getStartingBonus();
        assertEquals(previousBalanceAmount + 200, player.getBalance());
    }

    @AfterEach
    void tearDown() {
        Board.reset();
    }

}
