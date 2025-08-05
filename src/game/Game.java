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

        if (getWinner() != ' ') {
            System.out.println(getWinner() + " wins");
            return;
        }

        // determine which symbol to give to p1 and p2
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

    public Game(Board board) {
        this.board = new Board(board);
        player1 = null;
        player2 = null;
    }

    public void populate(int x, int y, char symbol) {
        if (board.at(x, y) != ' ' && symbol != ' ') {
            throw new IllegalStateException("This cell is occupied! Choose another one!");
        }

        board.populate(x, y, symbol);
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            if (!handleTurn(player1, player2, sc)) {
                break;
            }
            if (!handleTurn(player2, player1, sc)) {
                break;
            }
        }

        sc.close();
    }

    public Board getBoard() {
        return board;
    }

    /*
    validate that the given input contains only valid symbols for the start state
     */
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
                String p1 = sc.next().toUpperCase();
                String p2 = sc.next().toUpperCase();

                switch (p1) {
                    case "USER":
                        player1 = new Human(this, p1Symbol);
                        break;
                    case "EASY":
                        player1 = new EasyBot(this, board, p1Symbol);
                        break;
                    case "MEDIUM":
                        player1 = new MediumBot(this, board, p1Symbol);
                        break;
                    case "HARD":
                        player1 = new HardBot(this, board, p1Symbol);
                        break;
                    default:
                        System.out.println("Invalid argument: first argument must be USER | BOT DIFF");
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
                    case "MEDIUM":
                        player2 = new MediumBot(this, board, p2Symbol);
                        return;
                    case "HARD":
                        player2 = new HardBot(this, board, p2Symbol);
                        return;
                    default:
                        System.out.println("Invalid argument: second argument must be USER | BOT DIFF");
                        clearScanner(sc);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Please enter: (USER | BOT DIFF) (USER | BOT DIFF)");
            }
        }
    }

    public char getWinner() {
        for (int i = 0; i < 3; ++i) {
            // row check
            if ((board.at(i, 0) == board.at(i, 1)) && (board.at(i, 1) == board.at(i, 2))) {
                if (board.at(i, 0) != ' ') {
                    return board.at(i, 0);
                }
            }
            // col check
            if ((board.at(0, i) == board.at(1, i)) && (board.at(1, i) == board.at(2, i))) {
                if (board.at(0, i) != ' ') {
                    return board.at(0, i);
                }
            }
        }

        // diagonal check top-left -> bottom-right
        if ((board.at(0, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(2, 2))) {
            if (board.at(0, 0) != ' ') {
                return board.at(0, 0);
            }
        }

        // diagonal check bottom-left -> top-right
        if ((board.at(2, 0) == board.at(1, 1)) && (board.at(1, 1) == board.at(0, 2))) {
            if (board.at(2, 0) != ' ') {
                return board.at(2, 0);
            }
        }

        return ' ';
    }

    public boolean isDraw() {
        return board.isFull() && getWinner() == ' ';
    }

    /*
    returns true if game has ended, otherwise false
     */
    private boolean handleTurn(Player playing, Player waiting, Scanner sc) {
        while (true) {
            try {
                int[] coords = playing.makeMove();
                waiting.opponentMove(coords);
                board.display();

                char winner = getWinner();
                if (winner != ' ') {
                    System.out.println(winner + " wins");
                    return false;
                }
                if (isDraw()) {
                    System.out.println("Draw");
                    return false;
                }

                return true;

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