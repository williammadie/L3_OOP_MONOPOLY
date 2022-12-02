package fr.pantheonsorbonne.miage.game.monopoly.strategy;

import fr.pantheonsorbonne.miage.game.monopoly.player.Player;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.cell.Board;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Cell;
import fr.pantheonsorbonne.miage.game.monopoly.cell.Color;

public class BuyColorOnly extends Strategy {
    private List<Color> colors;

    public BuyColorOnly(Color color) {
        this.colors = new ArrayList<>();
        this.colors.add(color);
    }

    public BuyColorOnly(Color color, Color... colors) {
        this(color);
        for (Color c : colors) {
            this.colors.add(c);
        }
    }

    @Override
    public boolean doBuyCell(Player player) {
        Cell currentCell = Board.getCellWithId(player.getPawnPosition());
        return colors.contains(currentCell.getColor());
    }

}
