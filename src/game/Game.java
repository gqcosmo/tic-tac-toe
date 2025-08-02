package game;
import java.util.InputMismatchException;
import java.util.Scanner;
import board.Board;


public class Game {
    private final Board board;
    private char userSymbol = 'X';
    private boolean userTurn = true;

    public Game() {
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            System.out.print("Enter the cells: ");
            input = sc.nextLine().toUpperCase();
            if (input.isEmpty()) {
                break;
            }
        } while (input.length() != 9 || !validInput(input));

        // determine starting symbol
        int countX = 0;
        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);

            if (ch == 'X') {
                ++countX;
            } else if (ch == 'O') {
                --countX;
            }
        }

        if (countX > 0) {
            userSymbol = 'O';
        }

        board = (input.isEmpty()) ? new Board() : new Board(input.replace('_', ' '));
        if (winner()) {
            board.display();
            return;
        }
        play();
    }

    private static boolean validInput(String input) {
        char[] symbols = new char[]{'X', 'O', '_'};

        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);
            boolean valid = false;

            for (char symbol : symbols) {
                if (ch == symbol) {
                    valid = true;
                    break;
                }
            }

            if (!valid) {
                return false;
            }
        }

        return true;
    }

    private void populate(int x, int y) {
        if (board.at(x, y) != ' ') {
            throw new IllegalStateException("This cell is occupied! Choose another one!");
        }

        board.populate(x, y, userTurn ? userSymbol : ((userSymbol == 'X') ? 'O' : 'X'));
        userTurn = !userTurn;
    }

    private boolean winner() {
        for (int i = 0; i < 3; ++i) {
            // row check
            if ((board.at(i, 0) == board.at(i, 1)) && (board.at(i, 1) == board.at(i, 2))) {
                if (board.at(i, 0) != ' ') {
                    System.out.println(board.at(i, 0) + " wins");
                    return true;
                }
            }
            // col check
            if ((board.at(0, i) == board.at(1, i)) && (board.at(1, i) == board.at(2, i))) {
                if (board.at(0, i) != ' ') {
                    System.out.println(board.at(0, i) + " wins");
                    return true;
                }
            }
        }

        // vertical check top-left -> bottom-right
        if ((board.at(0, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(2, 2))) {
            if (board.at(0, 0) != ' ') {
                System.out.println(board.at(0, 0) + " wins");
                return true;
            }
        }

        // vertical check bottom-left -> top-right
        if ((board.at(2, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(0, 2))) {
            if (board.at(2, 0) != ' ') {
                System.out.println(board.at(2, 0) + " wins");
                return true;
            }
        }

        return false;
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                board.display();
                System.out.println("Enter the coordinates: ");
                final int x = sc.nextInt();
                final int y = sc.nextInt();
                populate(x-1, y-1);

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            board.display();

            if (winner()) {
                break;
            } else if (board.isFull()) {
                System.out.println("Draw");
                break;
            }
        }

        sc.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
