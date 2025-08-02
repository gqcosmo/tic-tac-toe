package player;
import java.util.ArrayList;
import game.Game;
import board.Board;

public abstract class Bot extends Player {
    ArrayList<int[]> avail;

    public Bot(Game game, Board board, char symbol) {
        super(game, symbol);
        avail = board.availableCoords();
    }

    /* remove users move from the list */
    public void userMove(int[] coords) {
        int x = coords[0];
        int y = coords[1];

        for (int i = 0; i < avail.size(); ++i) {
            if (avail.get(i)[0] == x && avail.get(i)[1] == y) {
                avail.remove(i);
                return;
            }
        }
    }
}