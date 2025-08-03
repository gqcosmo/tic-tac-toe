package player;
import java.util.ArrayList;
import java.util.Collections;

import game.Game;
import board.Board;

public abstract class Bot extends Player {
    ArrayList<int[]> avail;
    Board board;

    public Bot(Game game, Board board, char symbol) {
        super(game, symbol);
        this.board = board;
        avail = board.availableCoords();
        Collections.shuffle(avail);
    }

    /* remove users move from the list */
    @Override
    public void opponentMove(int[] coords) {
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