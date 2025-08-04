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
    public abstract int[] makeMove();

    /*
    @param coords: {x, y} of the move opponent made
     */
    public abstract void opponentMove(int[] coords);
}
