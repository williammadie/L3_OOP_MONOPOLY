package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class MonopolyGuest {
    public static void main(String[] args) {
        PlayerFacade facade = Facade.getFacade();
        facade.waitReady();
        // set our palyer name
        final String playerName = "distant_player";
        facade.createNewPlayer(playerName);
        System.out.println("I am: " + playerName);
        // wait until we are able to join a new game
        Game currentGame = facade.autoJoinGame("monopoly-room-1");
        Player me = new Player(playerName);
        // Boucle de décision

        /*
         * Pour chaque cas du switch, on aura:
         * 1. Actualiser l'état du joueur
         * 2. Envoyer une commande en conséquence
         */
        for (;;) {
            System.out.println(me.getId() + " is playing:");
            System.out.println(me.toString());
            GameCommand command = facade.receiveGameCommand(currentGame);
            GameAction action = GameAction.valueOf(command.name());
            System.out.println(action);
            switch (action) {

                case MOVE_PAWN_TO: // index
                    me.movePawnTo(Integer.parseInt(command.body()));
                    break;

                case BUY_CELL: // index
                    String idCell = command.body();
                    facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                            new GameCommand(GameAction.SEND_MONEY.name()));
                    GameCommand confirmCommand = facade.receiveGameCommand(currentGame);
                    if (GameAction.valueOf(confirmCommand.name()).equals(GameAction.ABORT_ACTION))
                        break;
                    Cell currentCell = Board.getCellWithId(Integer.parseInt(idCell));
                    try {
                        currentCell.buyCell(me);
                    } catch (CellCannotBeBoughtException e) {
                        e.printStackTrace();
                    }
                    break;

                case SELL_CELL: // index
                    Cell cellToSell = me.getProperties()
                            .get(GameLogic.getRandomNumberBetween(0, me.getProperties().size() - 1));
                    int cellToSellId = cellToSell.getCellId();
                    facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                            new GameCommand(GameAction.SELL_CELL.name(), Integer.toString(cellToSellId)));
                    break;
                case BUY_HOUSE: // index
                    System.out.println("in progress..");
                    break;

                case SELL_HOUSE: // index
                    System.out.println("in progress..");
                    break;

                case SEND_MONEY_TO: // cashAmount, targetedPlayerName
                    String[] params = command.body().split(",");
                    String cashAmount = params[0];
                    String targetedPlayerName = params[1];
                    facade.sendGameCommandToPlayer(currentGame, targetedPlayerName,
                            new GameCommand("SEND_MONEY", cashAmount));
                    break;

                case SEND_MONEY: // cashAmount
                    me.addMoney(Integer.parseInt(command.body()));
                    break;
                default:
                    break;
            }
        }

    }
}
