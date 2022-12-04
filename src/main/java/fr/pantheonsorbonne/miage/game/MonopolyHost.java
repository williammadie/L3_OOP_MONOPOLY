package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.NetworkPlayer;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.model.Game;

public class MonopolyHost {
    private static final int NB_MAX_PLAYERS = 4;
    public static void main(String[] args) {
        PlayerFacade playerFacade = Facade.getFacade();
        HostFacade hostFacade = Facade.getFacade();
        List<Player> playersInSession = new ArrayList<>();
        hostFacade.waitReady();

        Player host = new Player(GameLogic.generateUniquePlayerName(), GameLogic.getRandomStrategy());
        playerFacade.createNewPlayer(host.getName());        
        playersInSession.add(host);

        // Launch a new game until the programs is stopped
        for (;;) {
            Game game = hostFacade.createNewGame("monopoly-room-2");
            hostFacade.waitForExtraPlayerCount(NB_MAX_PLAYERS);
            playersInSession.addAll(game.getPlayers().stream().map(playerName -> new NetworkPlayer(playerName, playerFacade, game)).toList());
            Player winner = GameLogic.playTheGame(playersInSession);
            winner.declareGameOver("winner");
        }
    }
}
