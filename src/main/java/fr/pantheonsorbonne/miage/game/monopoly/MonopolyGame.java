package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class MonopolyGame {

        private Cell[] board;
        private List<Player> players;
        private int turnCounter;

        public MonopolyGame(List<Player> players) {
                this.board = Board.getGameBoard();
                this.players = players;
                this.turnCounter = 9;
                sendMoneyToAllPlayers(1500);
        }

        public void nextTour(Player player) {
                System.out.println("\nTurn n°" + this.turnCounter + ": " + player.getId() + " is playing:");
                System.out.println(player.toString());
                // Building Phase
                player.makeChoice(GameAction.BUY_HOUSE);

                // Player Turn Phase
                player.movePawnOf(player.rollDoubleDice());
                Cell playerCurrentCell = Board.getCellWithId(player.getPawnPosition());
                System.out.println(playerCurrentCell.toString());
                playerCurrentCell.trigger(player);
                this.turnCounter++;
        }

        private void sendMoneyToAllPlayers(int moneyAmount) {
                this.players.forEach(player -> player.addMoney(moneyAmount));
        }
}
