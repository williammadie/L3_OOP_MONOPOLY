package fr.pantheonsorbonne.miage.game.monopoly.strategy;

public class Hybrid extends Strategy {
<<<<<<< HEAD

    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        return player.calculateBuyingWish(player, currentCell.getColor()) > 40;
    }

=======
    public static final String IDENTIFIER = "Hybrid";
>>>>>>> doc-fix
}
