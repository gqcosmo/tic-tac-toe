package player;

import board.Board;
import game.Game;

public class HardBot extends Bot {
    public HardBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
    }

    @Override
    public int[] makeMove() {
        return new int[]{};
    }
}
