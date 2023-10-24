
import ui.CheckersTextConsole;

/**
 * This is the main class for the game of checkers. This class starts and runs
 * the game until the user exits the program.
 *
 * @author Hannah
 * @version 2.0
 */
public class ConsoleCheckers {

    /**
     * Default constructor
     */
    public ConsoleCheckers() {

    }

    /**
     * Main method. Runs checker game.
     *
     * @param args part of main method
     */
    public static void main(String[] args) {
        CheckersTextConsole checkersGame = new CheckersTextConsole();
        System.out.println("Welcome to Checkers!\n");
        checkersGame.start();
    }
}
