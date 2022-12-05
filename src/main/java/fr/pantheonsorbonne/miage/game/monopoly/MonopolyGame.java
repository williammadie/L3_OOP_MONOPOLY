package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.AbstractCell;
import fr.pantheonsorbonne.miage.game.monopoly.player.DesynchronizedPlayerException;
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
                player.getInfoLogger()
                                .append("\nTurn n°" + this.turnCounter + ": " + player.getName() + " is playing:\n");
                player.getInfoLogger().append(player.toString() + "\n");

                if (!player.isSynchronized())
                        throw new DesynchronizedPlayerException();
                // Building Phase
                player.makeChoice(GameAction.BUY_HOUSE);

                // Player Turn Phase
                if (!player.getIsJailed())
                        player.movePawnOf(player.rollDoubleDice());

                AbstractCell playerCurrentCell = Board.getCellWithId(player.getPawnPosition());
                player.getInfoLogger().append(playerCurrentCell.toString() + "\n");
                playerCurrentCell.trigger(player);
                this.turnCounter++;
        }

        private void sendMoneyToAllPlayers(int moneyAmount) {
                this.players.forEach(player -> player.addMoneySafe(moneyAmount));
        }
}
