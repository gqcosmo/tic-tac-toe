package player;
import game.Game;
import java.util.Scanner;

public class Human extends Player {
    public Human(Game game, char symbol) {
        super(game, symbol);
    }

    @Override
    public int[] makeMove() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");
            int x = sc.nextInt();
            int y = sc.nextInt();
            game.populate(x-1, y-1, symbol);
            return new int[]{x, y};
    }
}
