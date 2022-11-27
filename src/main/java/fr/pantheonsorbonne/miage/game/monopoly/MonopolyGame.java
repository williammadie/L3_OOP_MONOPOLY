package fr.pantheonsorbonne.miage.game.monopoly;

import java.rmi.UnexpectedException;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public class MonopolyGame {
        private List<Player> players;
        private int turnCounter;

        public MonopolyGame(List<Player> players) {
                this.players = players;
                this.turnCounter = 0;
                sendMoneyToAllPlayers(1500);
        }

        public void nextTour(Player player) {
                System.out.println("\nTurn n°" + this.turnCounter + ": " + player.getId() + " is playing:");
                System.out.println(player.toString());
                if (!player.getIsJailed())
                        player.movePawnOf(player.rollDoubleDice());

                
                // Building Phase
                try {
                        player.makeChoice(GameAction.BUY_HOUSE);
                } catch (UnexpectedException e) {
                        e.printStackTrace();
                        System.exit(2);
                }

                // Player Turn Phase
                

                Cell playerCurrentCell = Board.getCellWithId(player.getPawnPosition());
                System.out.println(playerCurrentCell.toString());
                playerCurrentCell.trigger(player);
                this.turnCounter++;
        }

        private void sendMoneyToAllPlayers(int moneyAmount) {
                this.players.forEach(player -> player.addMoney(moneyAmount));
        }
}
