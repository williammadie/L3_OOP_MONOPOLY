package fr.pantheonsorbonne.miage.game.monopoly;

public enum GameAction {
    ABORT_ACTION(-1),
    BUY_HOUSE(0),
    SELL_HOUSE(1),
    BUY_CELL(2),
    SELL_CELL(3),
    MOVE_PAWN_TO(4),
    SEND_MONEY_TO(5),
    SEND_MONEY(6),
    CHECK_BALANCE(7),
    GET_STRATEGY(8),
    END_TURN(9),
    GAME_OVER(10);

    public final int value;

    private GameAction(int value) {
        this.value = value;
    }
}
