package fr.pantheonsorbonne.miage.game;

import java.util.HashSet;
import java.util.Set;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.MonopolyGame;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.model.Game;

public class MonopolyHost {
    public static void main(String[] args) {
        PlayerFacade playerFacade = (PlayerFacade) Facade.getFacade();
        HostFacade hostFacade = (HostFacade) Facade.getFacade();
        Set<Player> playersInSession = new HashSet<>();
        hostFacade.waitReady();

        Player host = new Player(GameLogic.getUniquePlayerName());
        playerFacade.createNewPlayer(host.getId());        
        playersInSession.add(host);

        // Launch a new game until the programs is stopped
        for (;;) {
            Game game = hostFacade.createNewGame("monopoly-room-1");
            hostFacade.waitForExtraPlayerCount(2);
            playersInSession.addAll(game.getPlayers().stream().map(Player::new).toList());
            playTheGame(playerFacade, game);
            System.out.println(game.getHostName());
            System.out.println(game.getPlayers());
            System.exit(0);
        }
    }

    private static void playTheGame(PlayerFacade playerFacade, Game game) {
        return;
    }
}
