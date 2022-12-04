package fr.pantheonsorbonne.miage.game.monopoly.player;

import java.util.NoSuchElementException;

import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.GameAction;
import fr.pantheonsorbonne.miage.game.monopoly.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AlwaysBuy;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyColorOnly;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuyException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotSellException;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CannotBuildException;
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
        switch (possibleAction) {
            case BUY_HOUSE:
                this.triggerBuyHouse();
                break;

            case BUY_CELL:
                this.triggerBuyCell();
                break;

            case SELL_HOUSE:
                this.triggerSellHouse();
                break;

            case SELL_CELL:
                this.triggerSellCell();
                break;

            default:
                throw new NoSuchElementException("Unknown case: " + possibleAction);
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
            throw new UnexpectedCommandException(command.name());
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
            throw new UnexpectedCommandException(command.name());

        int distantBalance = Integer.parseInt(command.body());
        return distantBalance == this.getBalance();
    }

    @Override
    public void declareGameOver(String status) {
        super.declareGameOver(status);
        if (status.equals("winner"))
            playerFacade.sendGameCommandToPlayer(game, this.getName(),
                    new GameCommand(GameAction.GAME_OVER.name(), "winner"));
        else
            playerFacade.sendGameCommandToPlayer(game, this.getName(),
                    new GameCommand(GameAction.GAME_OVER.name(), "loser"));
    }

    @Override
    public void refreshTurnsCounter() {
        super.refreshTurnsCounter();
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.END_TURN.name()));
    }

<<<<<<< HEAD
    @Override 
    public void printPlayerActions(){
        playerFacade.sendGameCommandToAll(game, new GameCommand(GameAction.SHOW_INFO.name(),this.infoLogger.toString()));
        super.printPlayerActions();
    }

    private void handleBuyHouse() {
=======
    @Override
    public void createStrategy() {
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.GET_STRATEGY.name()));
        GameCommand command = playerFacade.receiveGameCommand(game);
        String strategyName = command.body();
        int selectedNumber;
        switch (strategyName) {
            case AlwaysBuy.IDENTIFIER:
                selectedNumber = 0;
                break;
            case BuyAbovePrice.IDENTIFIER:
                selectedNumber = 1;
                break;
            case BuyColorOnly.IDENTIFIER:
                selectedNumber = 2;
                break;
            case Hybrid.IDENTIFIER:
                selectedNumber = 3;
                break;
            default:
                throw new IllegalArgumentException("Unexpected case: " + strategyName);
        }
        super.strategy = GameLogic.selectStrategy(selectedNumber);
    }

    private void triggerBuyHouse() {
>>>>>>> doc-fix
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.BUY_HOUSE.name(), Integer.toString(this.pawnPosition)));
        GameCommand command = playerFacade.receiveGameCommand(game);

        if (!command.name().equals(GameAction.BUY_HOUSE.name()))
            throw new UnexpectedCommandException(command.name());

        int cellToBuyHouseId = Integer.parseInt(command.body());
        if (cellToBuyHouseId == GameAction.ABORT_ACTION.value)
            return;

        Cell cellToBuyHouse = Board.getCellWithId(cellToBuyHouseId);
        try {
            cellToBuyHouse.buyHouse(this);
        } catch (CannotBuildException e1) {
            e1.printStackTrace();
            throw new DesynchronizedPlayerException();
        }
    }

    private void triggerBuyCell() {
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.BUY_CELL.name(), Integer.toString(this.pawnPosition)));

        GameCommand command = playerFacade.receiveGameCommand(game);
        if (!command.name().equals(GameAction.BUY_CELL.name()))
            throw new UnexpectedCommandException(command.name());

        if (Integer.parseInt(command.body()) == GameAction.ABORT_ACTION.value)
            return;

        try {
            Cell currentCell = Board.getCellWithId(this.pawnPosition);
            currentCell.buyCell(this);
        } catch (CannotBuyException e) {
            e.printStackTrace();
            throw new DesynchronizedPlayerException();
        }
    }

    private void triggerSellHouse() {
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.SELL_HOUSE.name(), Integer.toString(this.pawnPosition)));
        GameCommand command = playerFacade.receiveGameCommand(game);

        if (!command.name().equals(GameAction.SELL_HOUSE.name()))
            throw new UnexpectedCommandException(command.name());

        if (Integer.parseInt(command.body()) == GameAction.ABORT_ACTION.value)
            return;

        int cellToSellHouseId = Integer.parseInt(command.body());
        Cell cellToSellHouse = Board.getCellWithId(cellToSellHouseId);
        try {
            cellToSellHouse.sellHouse(this);
        } catch (CannotBuildException e) {
            throw new DesynchronizedPlayerException();
        }
    }

    private void triggerSellCell() {
        playerFacade.sendGameCommandToPlayer(game, this.getName(),
                new GameCommand(GameAction.SELL_CELL.name(), Integer.toString(this.pawnPosition)));
        GameCommand command = playerFacade.receiveGameCommand(game);

        if (!command.name().equals(GameAction.SELL_CELL.name()))
            throw new UnexpectedCommandException("Unexpected command: " + command.name());

        if (Integer.parseInt(command.body()) == GameAction.ABORT_ACTION.value)
            return;

        int cellToSellCellId = Integer.parseInt(command.body());
        Cell cellToSellCell = Board.getCellWithId(cellToSellCellId);
        try {
            cellToSellCell.sellCell(this);
        } catch (CannotSellException e) {
            throw new DesynchronizedPlayerException();
        }
    }

}
