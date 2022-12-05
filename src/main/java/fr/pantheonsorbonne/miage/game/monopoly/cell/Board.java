package fr.pantheonsorbonne.miage.game.monopoly.cell;

import java.util.Arrays;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

public final class Board {
	private static final String OPPORTUNITY_CELL_NAME = "Case Chance";
	private static final AbstractCell[] gameBoard = initGameBoard();
	public static final int BOARD_LENGTH = gameBoard.length;

	private Board() {
	}

	public static AbstractCell getCellWithId(int id) {
		return gameBoard[id];
	}

	public static AbstractCell[] getGameBoard() {
		return gameBoard;
	}

	private static AbstractCell[] initGameBoard() {
		return new AbstractCell[] {
				new StartingPoint("Case Départ"),
				new Terrain("Boulevard de Belleville", 60, Color.BROWN,
						new int[] { 2, 4, 10, 30, 90, 160 }),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Rue Lecourbe", 60, Color.BROWN, new int[] { 4, 8, 20, 60, 180, 320 }),
				new Tax("Impôts sur le Revenu", 200),
				new Station("Gare Montparnasse", 200),
				new Terrain("Rue de Vaugirard", 100, Color.LIGHT_BLUE,
						new int[] { 6, 12, 30, 90, 270, 400 }),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Rue de Courcelles", 100, Color.LIGHT_BLUE, new int[] { 6, 12, 30, 90, 270,
						400 }),
				new Terrain("Avenue de la République", 100, Color.LIGHT_BLUE,
						new int[] { 8, 16, 40, 100, 300,
								450 }),
				new Jail("Prison"),
				new Terrain("Boulevard de la Vilette", 140, Color.PINK,
						new int[] { 10, 20, 50, 150, 450,
								625 }),
				new PublicService("Compagnie de Distribution de l'Electricité", 150),
				new Terrain("Avenue de Neuilly", 150, Color.PINK, new int[] { 10, 20, 50, 150, 450,
						625 }),
				new Terrain("Rue de Paradis", 160, Color.PINK, new int[] { 12, 24, 60, 180, 500,
						700 }),
				new Station("Gare de Lyon", 200),
				new Terrain("Avenue Mozart", 180, Color.ORANGE, new int[] { 14, 28, 70, 200, 550,
						750 }),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Boulevard Saint-Michel", 180, Color.ORANGE,
						new int[] { 14, 28, 70, 200, 550,
								750 }),
				new Terrain("Place Pigalle", 200, Color.ORANGE, new int[] { 16, 32, 80, 220, 600,
						800 }),
				new FreeParking("Parc Gratuit"),
				new Terrain("Avenue Matignon", 220, Color.RED, new int[] { 18, 36, 90, 250, 700,
						875 }),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Boulevard Malesherbes", 220, Color.RED, new int[] { 18, 36, 90, 250, 700,
						875 }),
				new Terrain("Avenue Henri-Martin", 220, Color.RED, new int[] { 20, 40, 100, 300, 750,
						925 }),
				new Station("Gare du Nord", 200),
				new Terrain("Faubourg Saint-Honoré", 260, Color.YELLOW,
						new int[] { 22, 44, 110, 330, 800,
								975 }),
				new Terrain("Place de la Bourse", 260, Color.YELLOW, new int[] { 22, 44, 110, 330, 800,
						975 }),
				new PublicService("Compagnie de Distribution des Eaux", 150),
				new Terrain("Rue La Fayette", 280, Color.YELLOW, new int[] { 24, 48, 120, 360, 850,
						1025 }),
				new GoToJail("Case Allez en Prison"),
				new Terrain("Avenue de Breteuil", 300, Color.GREEN, new int[] { 26, 52, 130, 390, 900,
						1100 }),
				new Terrain("Avenue Foch", 300, Color.GREEN, new int[] { 26, 52, 130, 390, 900,
						1100 }),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Boulevard des Capucines", 320, Color.GREEN,
						new int[] { 28, 56, 150, 450, 1000,
								1200 }),
				new Station("Gare Saint-Lazare", 200),
				new Opportunity(OPPORTUNITY_CELL_NAME),
				new Terrain("Avenue des Champs-Elysées", 250, Color.DEEP_BLUE,
						new int[] { 35, 70, 175, 500, 1100,
								1300 }),
				new Tax("Taxe de Luxe", 100),
				new Terrain("Rue de La Paix", 400, Color.DEEP_BLUE, new int[] { 50, 100, 200, 600, 1400,
						1700 })
		};
	}

	/**
	 * This returns the number of existing cell for each color. For instance, giving
	 * it BROWN will returns 2.
	 * 
	 * @param color
	 * @return the number of cells with the same color
	 */
	public static int getExistingCellNumberWithColor(Color color) {
		switch (color) {
			case BROWN:
			case DEEP_BLUE:
				return 2;
			case COLORLESS:
				return 18;
			default:
				return 3;
		}
	}

	/**
	 * This returns the number of adversaries who already own a cell of the given
	 * color. It is useful when considering a terrain purchase.
	 * 
	 * @param player
	 * @param color
	 * @return the number of adversaries who own terrains belonging to the group of
	 *         color
	 */
	public static int getNumberOfAdversaryOwnersForColor(Player player, Color color) {
		return (int) Arrays.stream(gameBoard)
				.filter(cell -> cell.getColor() == color && cell.getOwner() != null)
				.filter(cell -> !cell.getOwner().equals(player)).count();
	}

	/**
	 * This restores the default values for each cell of the Monopoly board.
	 */
	public static void reset() {
		Arrays.stream(gameBoard).forEach(AbstractCell::reset);
	}
}
