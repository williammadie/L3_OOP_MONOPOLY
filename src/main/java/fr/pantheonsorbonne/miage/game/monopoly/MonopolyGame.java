package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;
import fr.pantheonsorbonne.miage.game.monopoly.cell.FreeParking;
import fr.pantheonsorbonne.miage.game.monopoly.cell.GoToJail;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Jail;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Opportunity;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.PublicService;
import fr.pantheonsorbonne.miage.game.monopoly.cell.StartingPoint;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Station;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Terrain;
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
                int cellId = player.play(player.getProperties().getCellId(), GameAction.BUY_HOUSE);
                Property chosenCell = (Property) Board.getCellWithId(cellId);
                if (chosenCell.isBuildable()) {
                        ((Terrain) chosenCell).buildNewHouse();
                }

                // Player Turn Phase
                player.movePawnOf(player.rollDoubleDice());
                Cell playerCurrentCell = Board.getCellWithId(player.getPawnPosition());
                playerCurrentCell.trigger(player);
        }

        private void sendMoneyToAllPlayers(int moneyAmount) {
                this.players.forEach(player -> player.addMoney(moneyAmount));
        }
}
