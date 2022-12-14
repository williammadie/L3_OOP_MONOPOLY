package fr.pantheonsorbonne.miage.game;

import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyColorOnly;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;

public class MonopolyStandAlone {
    public static void main(String[] args) {
        List<Player> playersInSession = Arrays.asList(
                new Player(GameLogic.generateUniquePlayerName(), new Hybrid()),
                new Player(GameLogic.generateUniquePlayerName(), new BuyAbovePrice()),
                new Player(GameLogic.generateUniquePlayerName(), new BuyColorOnly(Color.ORANGE)));

        // Gameloop
        Player winner = GameLogic.playTheGame(playersInSession,null,null);

        System.out.println("Player " + winner.getName() + " wins the game!");
    }
}
