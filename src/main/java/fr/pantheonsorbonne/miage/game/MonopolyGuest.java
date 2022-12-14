package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class MonopolyGuest {
    public static void main(String[] args) {
        PlayerFacade facade = Facade.getFacade();
        facade.waitReady();
        // set our player name
        final String playerName = GameLogic.generateUniquePlayerName();
        facade.createNewPlayer(playerName);
        System.out.println("I am: " + playerName);
        // wait until we are able to join a new game
        Game currentGame = facade.autoJoinGame("monopoly-room-1");
        Player me = new Player(playerName, GameLogic.getRandomStrategy());

        // Decision loop
        for (;;) {
            GameCommand command = facade.receiveGameCommand(currentGame);
            if (!command.name().equals(GameAction.GAME_OVER.name())) {
                GameLogic.executeGameCommand(facade, currentGame, command, me);
            } else {
                GameLogic.handleGameOver(command.body());
                break;
            }
        }
        System.exit(0);
    
    }

}
