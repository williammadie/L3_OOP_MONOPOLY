package fr.pantheonsorbonne.miage.game;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.MonopolyGame;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AlwaysBuy;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyOrangeOnly;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;

public class MonopolyStandAlone {
    public static void main(String[] args) {
        List<Player> playersInSession = Arrays.asList(
                new Player(GameLogic.generateUniquePlayerName(), new AlwaysBuy()),
                new Player(GameLogic.generateUniquePlayerName(), new BuyAbovePrice()),
                new Player(GameLogic.generateUniquePlayerName(), new BuyOrangeOnly()));

        // Gameloop
        Player winner = GameLogic.playTheGame(playersInSession);

        System.out.println("Player " + winner.getName() + " wins the game!");
    }
}
