package player;

import board.Board;
import game.Game;

public class HardBot extends Bot {
    public HardBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
    }

    @Override
    public int[] makeMove() {
        int bestVal = (symbol == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestCoords = avail.getLast();

        for (int[] coords : board.availableCoords()) {
            Game state = new Game(board, this);
            state.populate(coords[0], coords[1], symbol);
            int currVal = minimax(state, coords, false);

            // add if conditions for symbols
        }

        opponentMove(bestCoords);
        return bestCoords;
    }

    private int minimax(Game state, int[] coords, boolean playerTurn) {
        if (state.isDraw()) {
            return 0;
        } else if (state.isWin()) {
            return (symbol == 'X') ? 1 : -1;
        }

        // iterate through all available coords, skipping argument coords and place next move

    }
}
