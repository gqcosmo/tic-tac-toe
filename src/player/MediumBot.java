package player;
import game.Game;
import board.Board;

public class MediumBot extends Bot {
    public MediumBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
    }

    @Override
    public int[] makeMove() {
        /*
        Winning move: If the AI has two in a row and can win with one more move, it takes that move.
        Blocking move: If the opponent has two in a row and can win with one more move, the AI blocks it.
        Fallback move: If neither of the above applies, the AI makes a random move.
         */
        for (int i = 0; i < 3; i++) {
            // need to check if 2 out of any of the 3 spots are taken by same symbol
        }
    }
}
