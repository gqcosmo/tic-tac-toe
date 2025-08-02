package player;
import java.util.Collections;
import game.Game;
import board.Board;

public class EasyBot extends Bot {
    public EasyBot(Game game, Board board) {
        super(game, board);
        Collections.shuffle(avail);
    }

    @Override
    public int[] makeMove() {
        int[] coords = avail.removeLast();
        game.populate(coords[0], coords[1]);
        return coords;
    }
}
