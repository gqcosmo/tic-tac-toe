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
                    return new int[]{x, y};
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
                    return new int[]{x, y};
                }

                game.populate(x, y, ' ');
            }
        }

        int[] coords = avail.removeLast();
        game.populate(coords[0], coords[1], symbol);
        return coords;
    }
}
