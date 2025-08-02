package player;
import java.util.Collections;
import game.Game;
import board.Board;

public class EasyBot extends Bot {
    public EasyBot(Game game, Board board, char symbol) {
        super(game, board, symbol);
        Collections.shuffle(avail);
    }

    @Override
    public int[] makeMove() {
        int[] coords = avail.removeLast();
        game.populate(coords[0], coords[1], symbol);
        return coords;
    }
}
