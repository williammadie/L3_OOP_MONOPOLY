package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.javafaker.Faker;

import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;
import fr.pantheonsorbonne.miage.game.monopoly.player.PlayerRankComparator;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.Hybrid;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyAbovePrice;
import fr.pantheonsorbonne.miage.game.monopoly.strategy.BuyColorOnly;
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

    public static Color getRandomColor() {
        return Color.values()[getRandomNumberBetween(0, Color.values().length - 1)];
    }

    public static Strategy inputStrategy(Player player) {
        int selectedNumber;
        for (;;) {
            System.out.println("Please select a strategy number for player " + player.getName());
            System.out.println("0.__________________________________AlwaysBuy");
            System.out.println("1.__________________________________BuyAbovePrice");
            System.out.println("2.__________________________________BuyOrangeRedOnly");
            System.out.println("3.__________________________________Hybrid");

            try (Scanner sc = new Scanner(System.in)) {
                selectedNumber = sc.nextInt();
            }

            if (0 <= selectedNumber && selectedNumber <= 3)
                break;
            System.out.println("Incorrect output! Please try again:");
        }
        return selectStrategy(selectedNumber);
    }

    public static Strategy getRandomStrategy() {
        int choice = getRandomNumberBetween(0, 3);
        return selectStrategy(choice);
    }

    public static Strategy selectStrategy(int selectedNumber) {
        Strategy selectedStrategy;
        switch (selectedNumber) {
            case 0:
                System.out.println("AlwaysBuy Strategy selected");
                selectedStrategy = new Hybrid();
                break;
            case 1:
                System.out.println("BuyAbovePrice Strategy selected");
                selectedStrategy = new BuyAbovePrice();
                break;
            case 2:
                System.out.println("BuyColorOnly Strategy selected");
                selectedStrategy = new BuyColorOnly(getRandomColor(), getRandomColor());
                break;
            case 3:
                System.out.println("Hybrid strategy selected");
                selectedStrategy = new Hybrid();
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

    /**
     * This handles Guest network commands. Each and every command received by the
     * Guest class will be processed by this function.
     * 
     * @param facade      the playerFacade which allows to send game commands via a
     *                    network
     * @param currentGame the network game object which stores current game
     *                    information for network messaging
     * @param command     the command to process
     * @param me          the player on which the command will be processed
     */
    public static void executeGameCommand(PlayerFacade facade, Game currentGame, GameCommand command, Player me) {
        GameAction action = GameAction.valueOf(command.name());
        switch (action) {
            case CHECK_BALANCE:
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.CHECK_BALANCE.name(),
                                Integer.toString(me.getBalance())));
                break;

            case GET_STRATEGY:
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.GET_STRATEGY.name(),
                                me.getStrategy().getClass().getSimpleName()));
                break;

            case MOVE_PAWN_TO:
                me.movePawnTo(Integer.parseInt(command.body()));
                break;

            case BUY_CELL:
                String idCell = command.body();
                if (Integer.parseInt(idCell) != me.getPawnPosition())
                    throw new IllegalStateException(
                            "Local and distant pawn positions vary" + idCell + " " + me.getPawnPosition());

                int cellToBuyId = me.getStrategy().handleBuyCell(me);
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.BUY_CELL.name(),
                                Integer.toString(cellToBuyId)));
                break;

            case SELL_CELL:
                int cellToSellId = me.getStrategy().handleSellCell(me);
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.SELL_CELL.name(), Integer.toString(cellToSellId)));
                break;
            case BUY_HOUSE:
                int cellToBuyHouseId = me.getStrategy().handleBuyHouse(me);
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.BUY_HOUSE.name(),
                                Integer.toString(cellToBuyHouseId)));
                break;

            case SELL_HOUSE:
                int cellToSellHouseId = me.getStrategy().handleSellHouse(me);
                facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                        new GameCommand(GameAction.SELL_HOUSE.name(), Integer.toString(cellToSellHouseId)));
                break;

            case SEND_MONEY_TO: // cashAmount, targetedPlayerName
                String cashAmount;
                if (!command.body().contains(",")) {
                    cashAmount = command.body();
                    facade.sendGameCommandToPlayer(currentGame, currentGame.getHostName(),
                            new GameCommand("SEND_MONEY", command.body()));
                } else {
                    String[] params = command.body().split(",");
                    cashAmount = params[0];
                    String targetedPlayerName = params[1];
                    facade.sendGameCommandToPlayer(currentGame, targetedPlayerName,
                            new GameCommand("SEND_MONEY", cashAmount));
                }
                me.removeMoney(Integer.parseInt(cashAmount));
                break;

            case SEND_MONEY:
                me.addMoney(Integer.parseInt(command.body()));
                break;
            case END_TURN:
                me.refreshTurnsCounter();
                break;
            case SHOW_INFO:
                System.out.println(command.body());
                break;
            default:
                throw new NoSuchElementException("Unexpected game action: " + action);
            
        }
    }

    /**
     * This handles both local and network games. It takes all registered players
     * and make them play until all of them except one go bankrupt.
     * 
     * @param playersInSession
     * @return the winner of the game
     */
    public static Player playTheGame(List<Player> playersInSession, Game currentGame, PlayerFacade playerFacade) {
        MonopolyGame monopolyGame = new MonopolyGame(playersInSession);
        Deque<Player> players = GameLogic.determinePlayersOrder(playersInSession);

        // Strategy Assignement
        for (Player player : players) {
            if (player.getStrategy() == null)
                player.createStrategy();
        }

        // Gameloop
        do {
            Player currentPlayer = players.poll();

            if (!currentPlayer.isBankrupt()) {
                monopolyGame.nextTour(currentPlayer);
                players.add(currentPlayer);
            } else {
                currentPlayer.declareGameOver("loser");
            }
            currentPlayer.refreshTurnsCounter();
            if(currentGame != null && currentPlayer.getName().equals(currentGame.getHostName())){
                playerFacade.sendGameCommandToAll(currentGame,new GameCommand(GameAction.SHOW_INFO.name(), currentPlayer.getInfoLogger().toString()));
            }
            currentPlayer.printPlayerActions();
        } while (players.size() > 1);

        return players.pollFirst();
    }
}
