package fr.pantheonsorbonne.miage.game.monopoly.player;

import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBuiltException;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

/**
 * This class allows Host to communicate with a distant player.
 */
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
        GameCommand command;
        switch (possibleAction) {
            case BUY_HOUSE:
                System.out.println("buy house starts");
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.BUY_HOUSE.name(), Integer.toString(this.pawnPosition)));
                command = playerFacade.receiveGameCommand(game);

                if (!command.name().equals(GameAction.BUY_HOUSE.name()))
                    throw new IllegalArgumentException("Unexpected command: " + command.name());

                int cellToBuyHouseId = Integer.parseInt(command.body());
                if (cellToBuyHouseId == -1)
                    break;

                Cell cellToBuyHouse = Board.getCellWithId(cellToBuyHouseId);
                try {
                    cellToBuyHouse.buyHouse(this);
                } catch (CellCannotBeBuiltException e1) {
                    e1.printStackTrace();
                    throw new IllegalStateException("Distant player and local player might be desynchronized");
                }
                System.out.println("buy house ends");
                break;

            case BUY_CELL:
                System.out.println("buy cell starts");
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.BUY_CELL.name(), Integer.toString(this.pawnPosition)));

                command = playerFacade.receiveGameCommand(game);
                if (!command.name().equals(GameAction.SEND_MONEY.name()))
                    throw new IllegalArgumentException("Unexpected command: " + command.name());
                else if (command.body().equals("-1"))
                    break;

                try {
                    Cell currentCell = Board.getCellWithId(this.pawnPosition);
                    currentCell.buyCell(this);
                } catch (CellCannotBeBoughtException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Distant player and local player might be desynchronized");
                }
                System.out.println(this.getBalance() + "Eur");
                break;

            case SELL_HOUSE:
                System.out.println("sell house starts");
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.SELL_HOUSE.name(), Integer.toString(this.pawnPosition)));
                System.out.println("sell house ends");
                break;

            case SELL_CELL:
                System.out.println("sell cell starts");
                playerFacade.sendGameCommandToPlayer(game, this.getName(),
                        new GameCommand(GameAction.SELL_CELL.name(), Integer.toString(this.pawnPosition)));
                System.out.println("sell cell ends");
                break;

            default:
                throw new IllegalArgumentException("Unknown case: " + possibleAction);
        }
    }

    @Override
    public void addMoneySafe(int price) {
        super.addMoneySafe(price);
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.SEND_MONEY.name(), Integer.toString(price)));
    }

    @Override
    public void removeMoneySafe(int price) {
        super.removeMoneySafe(price);
        StringBuilder sb = new StringBuilder();
        sb.append(price);
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.SEND_MONEY_TO.name(), Integer.toString(price)));
        
        GameCommand command = playerFacade.receiveGameCommand(game);

        if (!command.name().equals(GameAction.SEND_MONEY.name()))
            throw new IllegalArgumentException("Unexpected command: " + command.name());
    }

    @Override
    public void movePawnTo(int cellId) {
        super.movePawnTo(cellId);
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.MOVE_PAWN_TO.name(), Integer.toString(cellId)));
    }

    @Override
    public boolean isSynchronized() {
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.CHECK_BALANCE.name()));
        GameCommand command = playerFacade.receiveGameCommand(game);

        if (!command.name().equals(GameAction.CHECK_BALANCE.name()))
            throw new IllegalArgumentException("Unexpected command: " + command.name());

        int distantBalance = Integer.parseInt(command.body());
        return distantBalance == this.getBalance();
    }

}
