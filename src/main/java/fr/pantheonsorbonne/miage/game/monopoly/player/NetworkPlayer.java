package fr.pantheonsorbonne.miage.game.monopoly.player;

import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class NetworkPlayer extends Player {

    private PlayerFacade playerFacade;
    private Game game;

    public NetworkPlayer(String id, PlayerFacade playerFacade, Game game) {
        super(id);
        this.playerFacade = playerFacade;
        this.game = game;
    }

    @Override
    public void makeChoice(GameAction possibleAction) {
        switch (possibleAction) {
            case BUY_HOUSE:
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.BUY_HOUSE.name(), Integer.toString(this.pawnPosition)));
                break;
            case BUY_CELL:
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.BUY_CELL.name(), Integer.toString(this.pawnPosition)));

                GameCommand command = playerFacade.receiveGameCommand(game);
                try {
                    if (!command.name().equals(GameAction.SEND_MONEY.name()))
                        break;

                    if (command.body().equals(Integer.toString(this.pawnPosition))) {
                        Cell currentCell = Board.getCellWithId(this.pawnPosition);
                        currentCell.buyCell(this);
                        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                                new GameCommand(GameAction.CONFIRM_ACTION.name()));
                    }
                } catch (CellCannotBeBoughtException e) {
                    playerFacade.sendGameCommandToPlayer(game, this.getName(),
                            new GameCommand(GameAction.ABORT_ACTION.name()));
                }

                break;
            default:
                System.out.println("in progress...");
                break;
        }
    }

    @Override
    public void addMoney(int price) {
        super.addMoney(price);
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.SEND_MONEY.name(), Integer.toString(price)));
    }

    @Override
    public void movePawnTo(int cellId) {
        super.movePawnTo(cellId);
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.MOVE_PAWN_TO.name(), Integer.toString(cellId)));
    }

}
