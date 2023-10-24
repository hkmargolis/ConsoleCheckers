package ui;

import core.CheckersLogic;
import java.util.Scanner;

/**
 * This class handles console input and output in order to play the game of
 * checkers.
 *
 * @author Hannah
 */
public class CheckersTextConsole {

    /**
     * Variables
     */
    CheckersLogic game;
    boolean gameOver;
    String flag;

    /**
     * Constructor
     */
    public CheckersTextConsole() {
        this.game = new CheckersLogic();
        gameOver = false;
        flag = "";
    }

    /**
     * Methods
     */
    /**
     * This method starts and runs the game and keeps track of the gameover
     * status.
     */
    public void start() {

        printBoard(game.getBoard());
        while (gameOver == false) {
            getMove();
            if (game.validMoveEntry()) {
                flag = game.move();
                game.updateMovesAvailable();
                if (game.checkWinStatus() == true) {
                    gameOver = true;
                    System.out.println("Game Over");
                    System.exit(0);
                } else {
                    if (flag.equals("c")) { //Player that captured piece gets to go again
                        start();
                    }
                    game.switchPlayer();
                    printBoard(game.getBoard());
                }
            } else {
                System.out.println("Illegal move.");
            }
        }
    }

    /**
     * Prints checkers board
     *
     * @param board checkerboard loaded from CheckersLogic
     */
    public void printBoard(String[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|\n");
        }
    }

    /**
     * This method asks the user to enter a move and records the input. If it is
     * in the correct format it returns it, else it asks the user to enter it in
     * the correct format.
     *
     *
     */
    public void getMove() {
        Scanner in = new Scanner(System.in);
        System.out.printf("|Player %s|\n", game.getTurnStatus());
        System.out.println("Choose a piece to move.\nIndicate the cell# followed by the new position, e,g, 3a-4b.\nEnter: ");
        String input = in.next();
        //check format: is it the correct length?
        if (input.length() != 5) {
            System.out.println("Incorrect format.");
            getMove();
        }
        //check format: does it have the dash?
        if (input.charAt(2) != '-') {
            System.out.println("Incorrect format.");
            getMove();
        }
        game.setPositions(input.split("-"));

    }
}
