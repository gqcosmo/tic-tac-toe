package Bot;
import java.util.ArrayList;
import java.util.Collections;
import board.Board;


public class EasyBot extends Bot {
    public EasyBot(Board board, char symbol) {
        super(board,"Easy Bot");
        this.symbol = symbol;
        avail = board.availableCoords();
        Collections.shuffle(avail);
    }

    @Override
    public void makeMove(Board board) {
        int[] coords = avail.removeLast();
        board.populate(coords[0], coords[1], symbol);
    }
}
