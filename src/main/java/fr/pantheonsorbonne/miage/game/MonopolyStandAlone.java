package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.Game;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.Player;

public class MonopolyStandAlone {
    public static void main(String[] args) {
        List<Player> playersInSession = Arrays.asList(
                new Player(0),
                new Player(1),
                new Player(2));
        Game game = new Game(playersInSession);

        Deque<Player> players = GameLogic.determinePlayersOrder(playersInSession);

        // Gameloop
        do {
            Player currentPlayer = players.poll();

            if (!currentPlayer.isBankrupt()) {
                game.nextTour(currentPlayer);
                players.add(currentPlayer);
            } else {
                System.out.println("Player" + currentPlayer.getId() + " is bankrupt!");
            }
        } while (players.size() > 1);

        System.out.println("Player" + players.poll().getId() + " wins the game!");
    }
}
