package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.javafaker.Faker;

import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.CellCannotBeBoughtException;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.player.PlayerRankComparator;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.AlwaysBuy;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyOrangeOnly;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Strategy;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class GameLogic {
    public static final Random RAND = new Random();

    private GameLogic() {
    }

    public static Deque<Player> determinePlayersOrder(List<Player> players) {
        do {
            for (Player player : players) {
                player.rollDoubleDiceForRanking();
            }
        } while (players.stream().map(Player::getRank).collect(Collectors.toSet()).size() != players.size());

        players.sort(new PlayerRankComparator().reversed());
        ArrayDeque<Player> playersQueue = new ArrayDeque<>();
        playersQueue.addAll(players);
        return playersQueue;
    }

    public static int getRandomNumberBetween(int minIncluded, int maxIncluded) {
        return GameLogic.RAND.nextInt(maxIncluded - minIncluded + 1) + minIncluded;
    }

    public static String getUniquePlayerName() {
        StringBuilder playerName = new StringBuilder();
        playerName.append("P-");
        playerName.append(UUID.randomUUID().toString());
        return playerName.toString();
    }

    public static String generateUniquePlayerName() {
        return new Faker().name().firstName();
    }

    public static Strategy selectStrategy(Player player) {
        int selectedNumber;
        Strategy selectedStrategy;
        for (;;) {
            System.out.println("Please select a strategy number for player " + player.getName());
            System.out.println("0.__________________________________AlwaysBuy");
            System.out.println("1.__________________________________BuyAbovePrice");
            System.out.println("2.__________________________________BuyOrangeOnly");

            try (Scanner sc = new Scanner(System.in)) {
                selectedNumber = sc.nextInt();
            }

            if (0 <= selectedNumber && selectedNumber <= 2)
                break;
            System.out.println("Incorrect output! Please try again:");
        }

        switch (selectedNumber) {
            case 0:
                System.out.println("AlwaysBuy Strategy selected");
                selectedStrategy = new AlwaysBuy();
                break;
            case 1:
                System.out.println("BuyAbovePrice Strategy selected");
                selectedStrategy = new BuyAbovePrice();
                break;
            case 2:
                System.out.println("BuyOrangeOnly Strategy selected");
                selectedStrategy = new BuyOrangeOnly();
                break;
            default:
                throw new UnknownError("Selected Strategy Number equals " + selectedNumber);
        }

        return selectedStrategy;
    }

    public static void handleGameOver(String status) throws IllegalArgumentException {
        if (status.equals("winner"))
            System.out.println("You won the game!");
        else if (status.equals("loser"))
            System.out.println("You went bankrupt!");
        else
            throw new IllegalArgumentException("Status can only be winner or loser");
    }

    public static void executeGameCommand(PlayerFacade facade, Game currentGame, GameCommand command, Player me) {
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

    public static Player playTheGame(List<Player> playersInSession) {
        MonopolyGame monopolyGame = new MonopolyGame(playersInSession);
        Deque<Player> players = GameLogic.determinePlayersOrder(playersInSession);

        // Gameloop
        do {
            Player currentPlayer = players.poll();

            if (!currentPlayer.isBankrupt()) {
                monopolyGame.nextTour(currentPlayer);
                players.add(currentPlayer);
            } else {
                System.out.println("Player " + currentPlayer.getName() + " went bankrupt!\n");
            }
        } while (players.size() > 1);

        return players.pollFirst();
    }
}
