package player;
import game.Game;

public abstract class Player {
    Game game;
    char symbol;

    public Player(Game game, char symbol) {
        this.game = game;
        this.symbol = symbol;
    }

    public char getSymbol() { return symbol; }
    public int[] makeMove() { return new int[]{}; }

    public void userMove(int[] coords) {}
}
