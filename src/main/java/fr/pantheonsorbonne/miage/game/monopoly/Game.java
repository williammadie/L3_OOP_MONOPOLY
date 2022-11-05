package fr.pantheonsorbonne.miage.game.monopoly;

import java.util.ArrayList;
import java.util.List;

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
                new Cell("Case Départ", CellType.SPECIAL),
                new Cell("Boulevard de Belleville", CellType.TERRAIN, 60, Color.BROWN),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Rue Lecourbe", CellType.TERRAIN, 60, Color.BROWN),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Gare Montparnasse", CellType.STATION, 200),
                new Cell("Rue de Vaugirard", CellType.TERRAIN, 100, Color.LIGHT_BLUE),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Rue de Courcelles", CellType.TERRAIN, 100, Color.LIGHT_BLUE),
                new Cell("Avenue de la République", CellType.TERRAIN, 100, Color.LIGHT_BLUE),
                new Cell("Prison", CellType.JAIL),
                new Cell("Boulevard de la Vilette", CellType.TERRAIN, 140, Color.PINK),
                new Cell("Compagnie de Distribution de l'Electricité", CellType.PUBLIC_SERVICE, 150),
                new Cell("Avenue de Neuilly", CellType.TERRAIN, 150, Color.PINK),
                new Cell("Rue de Paradis", CellType.TERRAIN, 160, Color.PINK),
                new Cell("Gare de Lyon", CellType.STATION, 200),
                new Cell("Avenue Mozart", CellType.TERRAIN, 180, Color.ORANGE),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Boulevard Saint-Michel", CellType.TERRAIN, 180, Color.ORANGE),
                new Cell("Place Pigalle", CellType.TERRAIN, 200, Color.ORANGE),
                new Cell("Parc Gratuit", CellType.SPECIAL),
                new Cell("Avenue Matignon", CellType.TERRAIN, 220, Color.RED),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Boulevard Malesherbes", CellType.TERRAIN, 220, Color.RED),
                new Cell("Avenue Henri-Martin", CellType.TERRAIN, 220, Color.RED),
                new Cell("Gare du Nord", CellType.STATION, 200),
                new Cell("Faubourg Saint-Honoré", CellType.TERRAIN, 260, Color.RED),
                new Cell("Place de la Bourse", CellType.TERRAIN, 260, Color.RED),
                new Cell("Compagnie de Distribution des Eaux", CellType.PUBLIC_SERVICE, 150),
                new Cell("Rue La Fayette", CellType.TERRAIN, 280, Color.RED),
                new Cell("Case Allez en Prison", CellType.SPECIAL),
                new Cell("Avenue de Breteuil", CellType.TERRAIN, 300, Color.GREEN),
                new Cell("Avenue Foch", CellType.TERRAIN, 300, Color.GREEN),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Boulevard des Capucines", CellType.TERRAIN, 320, Color.GREEN),
                new Cell("Gare Saint-Lazare", CellType.STATION, 200),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Avenue des Champs-Elysées", CellType.TERRAIN, 250, Color.DEEP_BLUE),
                new Cell(OPPORTUNITY_CELL_NAME, CellType.OPPORTUNITY),
                new Cell("Rue de La Paix", CellType.TERRAIN, 400, Color.DEEP_BLUE)
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
            chosenCell.buildNewHouse();
        }

        // Player Turn Phase
        player.movePawnOf(player.rollDoubleDice());
        Cell playerCurrentCell = player.getCurrentCell();
        if (playerCurrentCell.canBeBought()) {
            if (playerCurrentCell.isVacant()) {
                player.play(playerCurrentCell.getCellId(), GameAction.BUY_CELL);
            } else if (!player.doOwn(playerCurrentCell){
                player.pay(playerCurrentCell.getRentValue(), getOwnerOfCell(playerCurrentCell));
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
