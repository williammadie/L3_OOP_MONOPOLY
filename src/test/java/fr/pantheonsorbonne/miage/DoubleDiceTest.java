package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.DoubleDice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class DoubleDiceTest {
    
    private DoubleDice doubleDice;

    @BeforeEach
    void init() {
        doubleDice = new DoubleDice();
    }
    @RepeatedTest(10)
    void testDiceValues() {
        assertTrue(2 <= doubleDice.getValue() && doubleDice.getValue() <= 12);
    }
}
