package player;
import game.Game;
import board.Board;

public class MediumBot extends Bot {
    public MediumBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
    }

    @Override
    public int[] makeMove() {
        // check if can make winning-cell
        for (int i = 0; i < 9; ++i) {
            int x = i / 3;
            int y = i % 3;

            if (board.at(x, y) == ' ') {
                game.populate(x, y, symbol);
                if (game.isWin()) {
                    int[] coords = new int[]{x, y};
                    opponentMove(coords); // remove placement from available cells
                    return coords;
                }

                game.populate(x, y, ' ');
            }
        }

        // check if can block winning-cell
        for (int i = 0; i < 9; ++i) {
            int x = i / 3;
            int y = i % 3;

            if (board.at(x, y) == ' ') {
                game.populate(x, y, symbol == 'X' ? 'O' : 'X');
                if (game.isWin()) {
                    game.populate(x, y, ' ');
                    game.populate(x, y, symbol);
                    int[] coords = new int[]{x, y};
                    opponentMove(coords);
                    return coords;
                }

                game.populate(x, y, ' ');
            }
        }

        // fallback to random placement
        int[] coords = avail.removeLast();
        game.populate(coords[0], coords[1], symbol);
        return coords;
    }
}
