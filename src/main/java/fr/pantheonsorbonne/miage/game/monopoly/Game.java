package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Property;
import fr.pantheonsorbonne.miage.game.monopoly.cell.PublicService;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Station;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Terrain;

public class Game {
    private static final String OPPORTUNITY_CELL_NAME = "Case Chance";
    private Cell[] gameBoard;
    private List<Player> players;

    public Game(List<Player> players) {
        this.gameBoard = initBoard();
        this.players = players;
        sendMoneyToAllPlayers(1500);
    }

    private Cell[] initBoard() {
        return new Cell[] {
                new Cell("Case Départ"),
                new Terrain("Boulevard de Belleville", 60, Color.BROWN, new int[] { 2, 4, 10, 30, 90, 160 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Rue Lecourbe", 60, Color.BROWN, new int[] { 4, 8, 20, 60, 180, 320 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Station("Gare Montparnasse", 200),
                new Terrain("Rue de Vaugirard", 100, Color.LIGHT_BLUE, new int[] { 6, 12, 30, 90, 270, 400 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Rue de Courcelles", 100, Color.LIGHT_BLUE, new int[] { 6, 12, 30, 90, 270,
                        400 }),
                new Terrain("Avenue de la République", 100, Color.LIGHT_BLUE, new int[] { 8, 16, 40, 100, 300,
                        450 }),
                new Cell("Prison"),
                new Terrain("Boulevard de la Vilette", 140, Color.PINK, new int[] { 10, 20, 50, 150, 450,
                        625 }),
                new PublicService("Compagnie de Distribution de l'Electricité", 150),
                new Terrain("Avenue de Neuilly", 150, Color.PINK, new int[] { 10, 20, 50, 150, 450,
                        625 }),
                new Terrain("Rue de Paradis", 160, Color.PINK, new int[] { 12, 24, 60, 180, 500,
                        700 }),
                new Station("Gare de Lyon", 200),
                new Terrain("Avenue Mozart", 180, Color.ORANGE, new int[] { 14, 28, 70, 200, 550,
                        750 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Boulevard Saint-Michel", 180, Color.ORANGE, new int[] { 14, 28, 70, 200, 550,
                        750 }),
                new Terrain("Place Pigalle", 200, Color.ORANGE, new int[] { 16, 32, 80, 220, 600,
                        800 }),
                new Cell("Parc Gratuit"),
                new Terrain("Avenue Matignon", 220, Color.RED, new int[] { 18, 36, 90, 250, 700,
                        875 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Boulevard Malesherbes", 220, Color.RED, new int[] { 18, 36, 90, 250, 700,
                        875 }),
                new Terrain("Avenue Henri-Martin", 220, Color.RED, new int[] { 20, 40, 100, 300, 750,
                        925 }),
                new Station("Gare du Nord", 200),
                new Terrain("Faubourg Saint-Honoré", 260, Color.YELLOW, new int[] { 22, 44, 110, 330, 800,
                        975 }),
                new Terrain("Place de la Bourse", 260, Color.YELLOW, new int[] { 22, 44, 110, 330, 800,
                        975 }),
                new PublicService("Compagnie de Distribution des Eaux", 150),
                new Terrain("Rue La Fayette", 280, Color.YELLOW, new int[] { 24, 48, 120, 360, 850,
                        1025 }),
                new Cell("Case Allez en Prison"),
                new Terrain("Avenue de Breteuil", 300, Color.GREEN, new int[] { 26, 52, 130, 390, 900,
                        1100 }),
                new Terrain("Avenue Foch", 300, Color.GREEN, new int[] { 26, 52, 130, 390, 900,
                        1100 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Boulevard des Capucines", 320, Color.GREEN, new int[] { 28, 56, 150, 450, 1000,
                        1200 }),
                new Station("Gare Saint-Lazare", 200),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Avenue des Champs-Elysées", 250, Color.DEEP_BLUE, new int[] { 35, 70, 175, 500, 1100,
                        1300 }),
                new Cell(OPPORTUNITY_CELL_NAME),
                new Terrain("Rue de La Paix", 400, Color.DEEP_BLUE, new int[] { 50, 100, 200, 600, 1400,
                        1700 })
        };
    }

    private Player getPlayerWithId(int playerId) {
        return players.stream().filter(x -> x.getId() == playerId).toList().get(0);
    }

    public Cell getCellWithId(int id) {
        return this.gameBoard[id];
    }

    public void nextTour(Player player) {
        // Building Phase
        int cellId = player.play(player.getProperties().getCellId(), GameAction.BUY_HOUSE);
        Cell chosenCell = getCellWithId(cellId);
        if (GameLogic.isBuildingAuthorized(chosenCell, player)) {
            ((Terrain) chosenCell).buildNewHouse();
        }

        // Player Turn Phase
        player.movePawnOf(player.rollDoubleDice());
        Cell playerCurrentCell = player.getPawnPosition();
        if (playerCurrentCell.canBeBought()) {
            
            if (playerCurrentCell.isVacant()) {
                player.play(playerCurrentCell.getCellId(), GameAction.BUY_CELL);
            } else if (!player.doOwn(playerCurrentCell){
                Property currentProperty = (Property) playerCurrentCell;
                player.pay(currentProperty.getRentValue(players), getOwnerOfCell(currentProperty));
            }
        } else {
            // Opportunity Cell and Jail
        }
    }

    private Player getOwnerOfCell(Cell cell) {
        for (Player player : players) {
            if (player.getProperties().contains(cell)) {
                return player;
            }
        }
        return null;
    }

    private void sendMoneyToAllPlayers(int moneyAmount) {
        this.players.forEach(player -> player.addMoney(moneyAmount));
    }
}
