package player;

import board.Board;
import game.Game;

public class HardBot extends Bot {
    public HardBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
    }

    @Override
    public int[] makeMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestCoords = null;

        for (int[] coords : board.availableCoords()) {
            Game state = new Game(board);
            state.populate(coords[0], coords[1], symbol);
            int currVal = minimax(state, symbol == 'X' ? 'O' : 'X');

            if (currVal > bestVal || bestCoords == null) {
                bestVal = currVal;
                bestCoords = coords;
            }
        }

        game.populate(bestCoords[0], bestCoords[1], symbol);
        opponentMove(bestCoords);
        return bestCoords;
    }

    private int minimax(Game state, char playingSymbol) {
        if (state.isDraw()) {
            return 0;
        }

        char winner = state.getWinner();
        if (winner != ' ') {
            return (winner == symbol) ?  1 : -1;
        }

        int bestScore = (playingSymbol == symbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int[] coords : state.getBoard().availableCoords()) {
            Game nextState = new Game(state.getBoard());
            nextState.populate(coords[0], coords[1], playingSymbol);
            int currScore = minimax(nextState, (playingSymbol == 'X') ? 'O' : 'X');

            // assume each player makes the most optimal move
            if (playingSymbol == symbol) {
                bestScore = Math.max(bestScore, currScore);
            } else {
                bestScore = Math.min(bestScore, currScore);
            }
        }

        return bestScore;
    }
}
