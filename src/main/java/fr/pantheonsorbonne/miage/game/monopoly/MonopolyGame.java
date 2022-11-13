package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class MonopolyGame {

        private Cell[] board;
        private List<Player> players;

        public MonopolyGame(List<Player> players) {
                this.board = Board.getGameBoard();
                this.players = players;
                sendMoneyToAllPlayers(1500);
        }

        public void nextTour(Player player) {
                // Building Phase
                player.makeChoice(GameAction.BUY_HOUSE);

                // Player Turn Phase
                player.movePawnOf(player.rollDoubleDice());
                Cell playerCurrentCell = Board.getCellWithId(player.getPawnPosition());
                playerCurrentCell.trigger(player);
        }

        private void sendMoneyToAllPlayers(int moneyAmount) {
                this.players.forEach(player -> player.addMoney(moneyAmount));
        }
}
