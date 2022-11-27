package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.MonopolyGame;
import fr.pantheonsorbonne.miage.game.monopoly.player.NetworkPlayer;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.model.Game;

public class MonopolyHost {
    public static void main(String[] args) {
        PlayerFacade playerFacade = (PlayerFacade) Facade.getFacade();
        HostFacade hostFacade = (HostFacade) Facade.getFacade();
        List<Player> playersInSession = new ArrayList<>();
        hostFacade.waitReady();

        Player host = new Player("host");
        playerFacade.createNewPlayer("host");        
        playersInSession.add(host);

        // Launch a new game until the programs is stopped
        for (;;) {
            Game game = hostFacade.createNewGame("monopoly-room-1");
            hostFacade.waitForExtraPlayerCount(2);
            playersInSession.addAll(game.getPlayers().stream().map(playerName -> new NetworkPlayer(playerName, playerFacade, game)).toList());
            playTheGame(playerFacade, game, playersInSession);
        }
    }

    private static void playTheGame(PlayerFacade playerFacade, Game game, List<Player> playersInSession) {
        MonopolyGame monopolyGame = new MonopolyGame(playersInSession);
        Deque<Player> players = GameLogic.determinePlayersOrder(playersInSession);
        
        // Gameloop
        do {
            Player currentPlayer = players.poll();

            if (!currentPlayer.isBankrupt()) {
                monopolyGame.nextTour(currentPlayer);
                System.out
                        .println("Player " + currentPlayer.getId() + " is playing! " + currentPlayer.toString() + "\n");
                players.add(currentPlayer);
            } else {
                System.out.println("Player " + currentPlayer.getId() + " is bankrupt!");
            }
        } while (players.size() > 1);
    }
}
