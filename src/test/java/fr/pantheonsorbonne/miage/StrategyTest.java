package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.MonopolyGame;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;


public class StrategyTest {
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
    void handleBuyCellTest (){
        Property property = (Property) Board.getCellWithId(7);
        property.handleBuyCell(adversary);
        System.out.println(player.getProperties());
        assertFalse(player.getProperties().contains(property));

    }

    @AfterEach
    void tearDown() {
        Board.reset();
    }
}