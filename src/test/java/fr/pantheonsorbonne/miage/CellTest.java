package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.MonopolyGame;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

class CellTest {

    private Player player;
    private Player adversary;

    @BeforeEach
    void setUp() throws CellCannotBeBoughtException {
        this.player = new Player("testPlayer");
        this.adversary = new Player("testAdversary");
        player.addMoney(20000);
        adversary.addMoney(20000);
        Board.getCellWithId(5).buyCell(adversary);
    }

    @Test
    void testTax1Trigger() {
        int previousBalanceAmount = player.getBalance();
        Cell taxCell1 = Board.getCellWithId(4);
        taxCell1.trigger(player);
        assertEquals(previousBalanceAmount - 200, player.getBalance());
    }

    @Test
    void testTax2Trigger() {
        int previousBalanceAmount = player.getBalance();
        Cell taxCell2 = Board.getCellWithId(38);
        taxCell2.trigger(player);
        assertEquals(previousBalanceAmount - 100, player.getBalance());
    }

    @Test
    void testStationVacantTrigger() {
        // TODO : Check that the player is created with Strategy ALWAYS_BUY
        Cell vacantStation = Board.getCellWithId(15);
        player.movePawnTo(15);
        vacantStation.trigger(player);
        assertTrue(player.getProperties().contains(vacantStation));
    }

    @Test
    void testStationOccupiedTrigger() {
        int previousBalanceAmount = player.getBalance();
        Cell occupiedStation = Board.getCellWithId(4);
        occupiedStation.trigger(player);
        assertEquals(previousBalanceAmount - 200, player.getBalance());
    }

    @Test
    void testGoToJailTrigger() {
        Cell goToJail = Board.getCellWithId(30);
        Cell jail = Board.getCellWithId(10);
        goToJail.trigger(player);
        assertEquals(jail.getCellId(), player.getPawnPosition());
        assertTrue(player.getIsJailed());
    }

    @Test
    void testJailTrigger() {
        Cell jail = Board.getCellWithId(10);
        jail.trigger(player);
        assertFalse(player.getIsJailed());
    }

    @Test
    void testLeaveJail() {
        Cell goToJail = Board.getCellWithId(30);
        Cell jail = Board.getCellWithId(10);
        goToJail.trigger(player);
        MonopolyGame game = new MonopolyGame(Arrays.asList(new Player[] { player, adversary }));
        game.nextTour(player);
        game.nextTour(player);
        game.nextTour(player);
        assertNotEquals(jail.getCellId(), player.getPawnPosition());
        assertFalse(player.getIsJailed());
    }

    @Test
    void testRentTerrainNoHouse() throws CellCannotBeBoughtException {
        Cell lastTerrain = Board.getCellWithId(39);
        Cell beforeLastTerrain = Board.getCellWithId(37);
        lastTerrain.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - 50, player.getBalance());
        beforeLastTerrain.buyCell(adversary);
    }

    @Test
    void testRentTerrainFullColor() throws CellCannotBeBoughtException {
        Cell lastTerrain = Board.getCellWithId(39);
        Cell beforeLastTerrain = Board.getCellWithId(37);
        lastTerrain.buyCell(adversary);
        beforeLastTerrain.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - 100, player.getBalance());
    }

    @Test
    void testRentTerrainWithHouses() throws CellCannotBeBoughtException, CellCannotBeBuiltException {
        Cell lastTerrain = Board.getCellWithId(39);
        Cell beforeLastTerrain = Board.getCellWithId(37);
        lastTerrain.buyCell(adversary);
        beforeLastTerrain.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();

        lastTerrain.buyHouse(adversary);
        beforeLastTerrain.buyHouse(adversary);
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - 200, player.getBalance());

        lastTerrain.buyHouse(adversary);
        beforeLastTerrain.buyHouse(adversary);
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - (200 + 600), player.getBalance());

        lastTerrain.buyHouse(adversary);
        beforeLastTerrain.buyHouse(adversary);
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - (200 + 600 + 1400), player.getBalance());

        lastTerrain.buyHouse(adversary);
        beforeLastTerrain.buyHouse(adversary);
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - (200 + 600 + 1400 + 1700), player.getBalance());
    }

    @Test
    void testRentStation() throws CellCannotBeBoughtException {
        Cell firstSation = Board.getCellWithId(5);
        Cell secondStation = Board.getCellWithId(15);
        Cell thirdSation = Board.getCellWithId(25);
        Cell fourthStation = Board.getCellWithId(35);

        int previousBalanceAmount = player.getBalance();
        firstSation.trigger(player);
        assertEquals(previousBalanceAmount - 25, player.getBalance());

        secondStation.buyCell(adversary);
        secondStation.trigger(player);
        assertEquals(previousBalanceAmount - (25 + 50), player.getBalance());

        thirdSation.buyCell(adversary);
        thirdSation.trigger(player);
        assertEquals(previousBalanceAmount - (25 + 50 + 100), player.getBalance());

        fourthStation.buyCell(adversary);
        fourthStation.trigger(player);
        assertEquals(previousBalanceAmount - (25 + 50 + 100 + 200), player.getBalance());
    }

    @RepeatedTest(10)
    void testRentPublicService() throws CellCannotBeBoughtException {
        Cell firstPublicService = Board.getCellWithId(12);
        Cell secondPublicService = Board.getCellWithId(28);

        firstPublicService.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        firstPublicService.trigger(player);
        int rentValue = previousBalanceAmount - player.getBalance();
        System.out.println(rentValue);
        assertTrue(2 * 4 <= rentValue && rentValue <= 12 * 4);

        secondPublicService.buyCell(adversary);
        previousBalanceAmount = player.getBalance();
        firstPublicService.trigger(player);
        rentValue = previousBalanceAmount - player.getBalance();
        assertTrue(2 * 10 <= rentValue && rentValue <= 12 * 10);
    }

    @AfterEach
    void tearDown() {
        Board.reset();
    }
}