package Bot;
import java.util.Random;
import java.util.ArrayList;
import board.Board;

abstract class Bot {
    Random rd = new Random();
    ArrayList<int[]> avail;
    Board board;
    String name;
    char symbol;

    public Bot(Board board, String name) {
        this.board = board;
        this.name = name;
    }

    public void makeMove(Board board) {};

    /* remove users move from the list */
    public void userMove(int x, int y) {
        for (int i = 0; i < avail.size(); ++i) {
            if (avail.get(i)[0] == x && avail.get(i)[1] == y) {
                avail.remove(i);
                return;
            }
        }
    }
}