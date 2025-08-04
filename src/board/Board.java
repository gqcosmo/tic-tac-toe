package board;
import java.util.ArrayList;

public class Board {
    private final char[][] board;
    private static final int N = 3;
    private int cellsRemaining = N*N;

    public Board() {
        board = new char[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                board[i][j] = ' ';
            }
        }
    }

    public Board(Board other) {
        board = other.board;
        cellsRemaining = other.cellsRemaining;
    }

    public Board(String data) {
        board = new char[N][N];

        if (data.length() != N*N) {
            throw new IllegalArgumentException("Invalid argument: argument must be of length 9");
        }

        for (int i = 0; i < N*N; ++i) {
            char ch = data.charAt(i);
            board[i / N][i % N] = ch;
            if (ch != ' ') {
                --cellsRemaining;
            }
        }
    }

    public void populate(int x, int y, char ch) {
        validateCoords(x, y);
        board[x][y] = ch;

        cellsRemaining = (ch == ' ') ? ++cellsRemaining : --cellsRemaining;
    }

    public char at(int x, int y) {
        validateCoords(x, y);
        return board[x][y];
    }

    public boolean isFull() {
        return cellsRemaining == 0;
    }

    public ArrayList<int[]> availableCoords() {
        ArrayList<int[]> arr = new ArrayList<>();

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (board[i][j] == ' ') {
                    arr.add(new int[] {i, j});
                }
            }
        }

        return arr;
    }

    public void display() {
        System.out.println("---------");

        for (int i = 0; i < N; ++i) {
            System.out.print("|" + " ");

            for (int j = 0; j < N; ++j) {
                System.out.print(board[i][j] + " ");
            }

            System.out.println("|");
        }

        System.out.println("---------");
    }

    private static void validateCoords(int x, int y) {
        if (x < 0 || x >= N) {
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        }
        if (y < 0 || y >= N) {
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        }
    }

    public static void main(String[] args) {
        String test = " XXOO OX ";
        Board board = new Board(test);
        board.display();
    }
}