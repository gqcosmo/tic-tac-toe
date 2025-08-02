package player;
import game.Game;

public abstract class Player {
    Game game;

    public Player(Game game) {
        this.game = game;
    }

    public int[] makeMove() { return new int[]{}; }

    public void userMove(int[] coords) {}
}
