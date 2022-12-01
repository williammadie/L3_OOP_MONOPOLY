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

    


    







}
