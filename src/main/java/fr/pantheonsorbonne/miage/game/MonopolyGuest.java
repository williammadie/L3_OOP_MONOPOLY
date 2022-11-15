package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class MonopolyGuest {
    public static void main(String[] args) {
        PlayerFacade facade = Facade.getFacade();
        facade.waitReady();
        // set our palyer name
        final String playerName = GameLogic.getUniquePlayerName();
        facade.createNewPlayer(playerName);
        System.out.println("I am: " + playerName);
        // wait until we are able to join a new game
        Game currentGame = facade.autoJoinGame("monopoly-room-1");

        // Boucle de décision

        /*
         * Pour chaque cas du switch, on aura:
         * 1. Actualiser l'état du joueur
         * 2. Envoyer une commande en conséquence
         */
        switch (action) {
            case MOVE_PAWN_TO: // index
                // actualise l'état du pion
                // player.setPawnPosition(params)
            case BUY_CELL:  // index
                // player.buy_cell(params)
                // player.makeDecision(action, params)
                    // 
            case SELL_CELL: // index
            
            case BUY_HOUSE: // index

            case SELL_HOUSE: // index

            case SEND_MONEY_TO: // cashAmount, playerName
                facade.sendGameCommandToPlayer(currentGame, playerName, new GameCommand("SEND_MONEY", cashAmount));

            case SEND_MONEY: // cashAmount

        }
    }
}
