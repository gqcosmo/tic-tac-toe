package game;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import board.Board;
import player.*;

public class Game {
    private final Board board;
    private Player player1;
    private Player player2;

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

        board = (input.isEmpty()) ? new Board() : new Board(input.replace('_', ' '));
        board.display();

        if (isWin()) {
            return;
        }

        int countX = 0;
        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);
            if (ch == 'X') {
                ++countX;
            } else if (ch == 'O') {
                --countX;
            }
        }

        playerSetup(countX);
        play();
    }

    public void populate(int x, int y, char symbol) {
        if (board.at(x, y) != ' ') {
            throw new IllegalStateException("This cell is occupied! Choose another one!");
        }

        board.populate(x, y, symbol);
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            handleTurn(player1, player2, sc);
            if (isWin() || isDraw()) {
                break;
            }

            handleTurn(player2, player1, sc);
            if (isWin() || isDraw()) {
                break;
            }
        }

        sc.close();
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

    private void clearScanner(Scanner sc) {
        sc.nextLine();
    }

    private void playerSetup(int countX) {
        char p1Symbol = countX > 0 ? 'O' : 'X';
        char p2Symbol = countX > 0 ? 'X' : 'O';

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Input command: ");
                String start = sc.next().toUpperCase();
                String p1 = sc.next().toUpperCase();
                String p2 = sc.next().toUpperCase();

                if (!start.equals("START")) {
                    System.out.println("Invalid argument: First argument must be START");
                    clearScanner(sc);
                    continue;
                }

                switch (p1) {
                    case "USER":
                        player1 = new Human(this, p1Symbol);
                        break;
                    case "EASY":
                        player1 = new EasyBot(this, board, p1Symbol);
                        break;
                    default:
                        System.out.println("Invalid argument: first argument be be USER | BOT DIFF");
                        clearScanner(sc);
                        continue;
                }

                switch (p2) {
                    case "USER":
                        player2 = new Human(this, p2Symbol);
                        return;
                    case "EASY":
                        player2 = new EasyBot(this, board, p2Symbol);
                        return;
                    default:
                        System.out.println("Invalid argument: Second argument be be USER | BOT DIFF");
                        clearScanner(sc);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Please enter: START (USER | BOT DIFF) (USER | BOT DIFF)");
            }
        }
    }

    private boolean isWin() {
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

        // diagonal check top-left -> bottom-right
        if ((board.at(0, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(2, 2))) {
            if (board.at(0, 0) != ' ') {
                System.out.println(board.at(0, 0) + " wins");
                return true;
            }
        }

        // diagonal check bottom-left -> top-right
        if ((board.at(2, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(0, 2))) {
            if (board.at(2, 0) != ' ') {
                System.out.println(board.at(2, 0) + " wins");
                return true;
            }
        }

        return false;
    }

    private boolean isDraw() {
        if (board.isFull()) {
            System.out.println("Draw");
            return true;
    }

    return false;
    }

    private void handleTurn(Player turn, Player waiting, Scanner sc) {
        while (true) {
            try {
                int[] coords = turn.makeMove();
                waiting.userMove(coords);
                board.display();
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}