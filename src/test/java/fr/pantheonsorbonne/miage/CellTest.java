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
import fr.pantheonsorbonne.miage.game.monopoly.cell.AbstractCell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuyException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuildException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;

class CellTest {

    private Player player;
    private Player adversary;

    @BeforeEach
    void setUp() throws CannotBuyException {
        this.player = new Player("testPlayer", new Hybrid());
        this.adversary = new Player("testAdversary");
        player.addMoney(20000);
        adversary.addMoney(20000);
        Board.getCellWithId(5).buyCell(adversary);
    }

    @Test
    void testTax1Trigger() {
        int previousBalanceAmount = player.getBalance();
        AbstractCell taxCell1 = Board.getCellWithId(4);
        taxCell1.trigger(player);
        assertEquals(previousBalanceAmount - 200, player.getBalance());
    }

    @Test
    void testTax2Trigger() {
        int previousBalanceAmount = player.getBalance();
        AbstractCell taxCell2 = Board.getCellWithId(38);
        taxCell2.trigger(player);
        assertEquals(previousBalanceAmount - 100, player.getBalance());
    }

    @Test
    void testStationVacantTrigger() {
        AbstractCell vacantStation = Board.getCellWithId(15);
        player.movePawnTo(15);
        vacantStation.trigger(player);
        assertTrue(player.getProperties().contains(vacantStation));
    }

    @Test
    void testStationOccupiedTrigger() {
        int previousBalanceAmount = player.getBalance();
        AbstractCell occupiedStation = Board.getCellWithId(4);
        occupiedStation.trigger(player);
        assertEquals(previousBalanceAmount - 200, player.getBalance());
    }

    @Test
    void testGoToJailTrigger() {
        AbstractCell goToJail = Board.getCellWithId(30);
        AbstractCell jail = Board.getCellWithId(10);
        goToJail.trigger(player);
        assertEquals(jail.getCellId(), player.getPawnPosition());
        assertTrue(player.getIsJailed());
    }

    @Test
    void testJailTrigger() {
        AbstractCell jail = Board.getCellWithId(10);
        jail.trigger(player);
        assertFalse(player.getIsJailed());
    }

    @Test
    void testLeaveJail() {
        AbstractCell goToJail = Board.getCellWithId(30);
        AbstractCell jail = Board.getCellWithId(10);
        goToJail.trigger(player);
        MonopolyGame game = new MonopolyGame(Arrays.asList(new Player[] { player, adversary }));
        game.nextTour(player);
        game.nextTour(player);
        game.nextTour(player);
        assertNotEquals(jail.getCellId(), player.getPawnPosition());
        assertFalse(player.getIsJailed());
    }

    @Test
    void testRentTerrainNoHouse() throws CannotBuyException {
        AbstractCell lastTerrain = Board.getCellWithId(39);
        AbstractCell beforeLastTerrain = Board.getCellWithId(37);
        lastTerrain.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - 50, player.getBalance());
        beforeLastTerrain.buyCell(adversary);
    }

    @Test
    void testRentTerrainFullColor() throws CannotBuyException {
        AbstractCell lastTerrain = Board.getCellWithId(39);
        AbstractCell beforeLastTerrain = Board.getCellWithId(37);
        lastTerrain.buyCell(adversary);
        beforeLastTerrain.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        lastTerrain.trigger(player);
        assertEquals(previousBalanceAmount - 100, player.getBalance());
    }

    @Test
    void testRentTerrainWithHouses() throws CannotBuyException, CannotBuildException {
        AbstractCell lastTerrain = Board.getCellWithId(39);
        AbstractCell beforeLastTerrain = Board.getCellWithId(37);
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
    void testRentStation() throws CannotBuyException {
        AbstractCell firstSation = Board.getCellWithId(5);
        AbstractCell secondStation = Board.getCellWithId(15);
        AbstractCell thirdSation = Board.getCellWithId(25);
        AbstractCell fourthStation = Board.getCellWithId(35);

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
    void testRentPublicService() throws CannotBuyException {
        AbstractCell firstPublicService = Board.getCellWithId(12);
        AbstractCell secondPublicService = Board.getCellWithId(28);

        firstPublicService.buyCell(adversary);
        int previousBalanceAmount = player.getBalance();
        firstPublicService.trigger(player);
        int rentValue = previousBalanceAmount - player.getBalance();
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