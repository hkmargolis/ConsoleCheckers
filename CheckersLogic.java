package core;

import java.util.HashMap;
import ui.CheckersTextConsole;

/**
 * This class handles all the logic required to implement the game of checkers.
 *
 * @author Hannah
 */
public class CheckersLogic {

    /**
     * Variables
     */
    private String[][] board;
    private String[] positions; //entered by user, require validation
    private int xNumPieces;
    private int oNumPieces;
    private int xMovesAvailable;
    private int oMovesAvailable;
    private HashMap<String, Integer> rowMap;
    private HashMap<String, Integer> colMap;
    private String turnStatus;
    private int rowI;
    private int colI;
    private int rowF;
    private int colF;

    /**
     * Constructor
     */
    public CheckersLogic() {
        //new board
        this.board = new String[][]{{"8", "_", "o", "_", "o", "_", "o", "_", "o"},
        {"7", "o", "_", "o", "_", "o", "_", "o", "_"},
        {"6", "_", "o", "_", "o", "_", "o", "_", "o"},
        {"5", "_", "_", "_", "_", "_", "_", "_", "_"},
        {"4", "_", "_", "_", "_", "_", "_", "_", "_"},
        {"3", "x", "_", "x", "_", "x", "_", "x", "_"},
        {"2", "_", "x", "_", "x", "_", "x", "_", "x"},
        {"1", "x", "_", "x", "_", "x", "_", "x", "_"},
        {"*", "a", "b", "c", "d", "e", "f", "g", "h"},};

//testBoard
//        this.board = new String[][]{{"8", "_", "_", "_", "_", "_", "_", "_", "_"},
//        {"7", "_", "_", "o", "_", "_", "_", "_", "_"},
//        {"6", "_", "_", "_", "x", "_", "_", "_", "_"},
//        {"5", "_", "_", "_", "_", "o", "_", "_", "_"},
//        {"4", "_", "_", "_", "_", "_", "_", "_", "_"},
//        {"3", "_", "_", "o", "_", "_", "_", "_", "_"},
//        {"2", "_", "x", "_", "_", "_", "_", "_", "_"},
//        {"1", "_", "_", "o", "_", "_", "_", "_", "_"},
//        {"*", "a", "b", "c", "d", "e", "f", "g", "h"},};
        //set positions to null, player has not entered anything yet into console
        this.positions = null;

        //set players' initial pieces and available number of moves
        xNumPieces = 12;
        oNumPieces = 12;

        //set players initial number of moves available to 4
        xMovesAvailable = 4;
        oMovesAvailable = 4;

        //set checkerboard cell maps
        rowMap = new HashMap<>();
        rowMap.put("8", 0);
        rowMap.put("7", 1);
        rowMap.put("6", 2);
        rowMap.put("5", 3);
        rowMap.put("4", 4);
        rowMap.put("3", 5);
        rowMap.put("2", 6);
        rowMap.put("1", 7);

        colMap = new HashMap<>();
        colMap.put("a", 1);
        colMap.put("b", 2);
        colMap.put("c", 3);
        colMap.put("d", 4);
        colMap.put("e", 5);
        colMap.put("f", 6);
        colMap.put("g", 7);
        colMap.put("h", 8);

        //set player x as first
        turnStatus = "x";

        //set rows and columns to 0 b/c positions is null
        rowI = 0;
        colI = 0;
        rowF = 0;
        colF = 0;

    }

    /**
     * Getters & Setters
     */
    /**
     * This method returns the current board
     *
     * @return board;
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * This method sets positions. It is used by the CheckersTextConsole to save
     * the player input/move entered in the console.
     *
     * @param input user input from CheckersTextConsole
     */
    public void setPositions(String[] input) {
        this.positions = input;
    }

    /**
     * This method returns turnStatus, and assists in determining which player's
     * turn it currently is.
     *
     * @return turnStatus
     */
    public String getTurnStatus() {
        return turnStatus;
    }

    /**
     * Methods
     */
    /**
     * This method checks to see if the positions entered by the player are
     * valid.
     *
     * @return valid
     */
    public boolean validMoveEntry() {
        //checks if positions are valid cells on the checkerboard.
        if (validPositions()) {
            System.out.println("valid positions...");
            //case 1: piece to move must belong to the player (i.e. playerX can only move an x piece)
            if (validPiece()) {
                System.out.println("valid piece...");
                //case 2: piece must move forward & diagonally one space
                if (validMove(rowI, colI, rowF, colF)) {
                    System.out.println("valid move...");
                    //case 3: piece moves into an empty space
                    if (clearToMove(rowF, colF)) {
                        System.out.println("clear to move...moving piece...");
                        return true;
                    } //case 3: piece will capture opponents piece
                    else if (validCapture(rowI, colI, rowF, colF)) {
                        System.out.println("valid capture...moving piece...");
                        return true;
                    }
                }
            }
        }//else move is invalid
        return false;
    }

    /**
     * This is a helper method for validMoveEntry() that checks if the positions
     * are valid cells
     *
     * @return valid
     */
    public boolean validPositions() {

        String rowInitial = Character.toString(positions[0].charAt(0));
        String colInitial = Character.toString(positions[0].charAt(1));
        String rowFinal = Character.toString(positions[1].charAt(0));
        String colFinal = Character.toString(positions[1].charAt(1));

        //check if positions are valid cells, if they are set these are our intial rows and cols
        if (rowMap.containsKey(rowInitial) && rowMap.containsKey(rowFinal) && colMap.containsKey(colInitial) && colMap.containsKey(colFinal)) {
            rowI = rowMap.get(rowInitial);
            colI = colMap.get(colInitial);
            rowF = rowMap.get(rowFinal);
            colF = colMap.get(colFinal);
            return true;
        }
        return false;
    }

//  test method
//    public int[] getCellVals() {
//        return new int[]{rowI, colI, rowF, colF};
//    }
    /**
     * This is a helper method for validMoveEntry() that checks if the piece to
     * move belongs to player whose turn it is
     *
     * @return valid
     */
    public boolean validPiece() {
        if (turnStatus.equals("x") && board[rowI][colI].equals("x")) {
            return true;
        }
        if (turnStatus.equals("o") && board[rowI][colI].equals("o")) {
            return true;
        }
        return false;

    }

    /**
     * This is a helper method for validMoveEntry() that checks if the final
     * position is empty
     *
     * @param rowF (final row position)
     * @param colF (final column position)
     * @return valid
     */
    public boolean clearToMove(int rowF, int colF) {
        if (board[rowF][colF].equals("_")) {
            return true;
        }
        return false;
    }

    /**
     * This is a helper method for validMoveEntry() that checks if the piece
     * moves forward and diagonal by one
     *
     * @param rowI(initial row position)
     * @param colI(initial column position)
     * @param rowF(final row position)
     * @param colF(final column position)
     * @return valid
     */
    public boolean validMove(int rowI, int colI, int rowF, int colF) {
        //if it is playerX's turn and final cell is one row forward and diagonal to the right or left by one
        if (turnStatus.equals("x") && (rowF == rowI - 1) && (colF == colI + 1 || colF == colI - 1)) {
            return true;
        } //if it is playerO's turn and final cell is one row forward and diagonal to the right or left by one

        if (turnStatus.equals("o") && (rowF == rowI + 1) && (colF == colI + 1 || colF == colI - 1)) {
            return true;
        }
        //else this is not a valid move
        return false;
    }

    /**
     * This is a helper method for validMoveEntry() that checks if the piece can
     * capture an opponents piece
     *
     * @param rowI(initial row position)
     * @param colI(initial column position)
     * @param rowF(final row position)
     * @param colF(final column position)
     * @return valid
     */
    public boolean validCapture(int rowI, int colI, int rowF, int colF) {
        //if it is playerX's turn and the final cell contains an o
        if (turnStatus.equals("x") && board[rowF][colF].equals("o")) {
            //confirm right capture
            if (colI < colF) {
                return clearToMove(rowF - 1, colF + 1);
            } //confirm left capture
            else {
                return clearToMove(rowF - 1, colF - 1);
            }
        }
        //if it is playerO's turn and the final cell contains an x
        if (turnStatus.equals("o") && board[rowF][colF].equals("x")) {
            //confirm right capture is legal
            if (colI < colF) {
                return clearToMove(rowF + 1, colF + 1);
            } //confirm left capture is legal
            else {
                return clearToMove(rowF + 1, colF - 1);
            }
        }
        //else this is not a valid move
        return false;
    }

    /**
     * This method moves the piece if validMoveEntry() returns true.
     *
     * @return String flagging if move or capture
     */
    public String move() {
        String flag = "c";
        //playerX's turn
        if (turnStatus.equals("x")) {
            //case 1: move
            if (clearToMove(rowF, colF)) {
                board[rowI][colI] = "_";
                board[rowF][colF] = "x";
                return "";
            } //case 2: capture
            else if (validCapture(rowI, colI, rowF, colF)) {
                if (colI < colF) {
                    //right capture & jump
                    board[rowI][colI] = "_";
                    board[rowF][colF] = "_";
                    board[rowF - 1][colF + 1] = "x";
                    System.out.println("<<Piece captured!>>");
                    --oNumPieces;

                    return flag;

                } else {
                    //left capture & jump
                    board[rowI][colI] = "_";
                    board[rowF][colF] = "_";
                    board[rowF - 1][colF - 1] = "x";
                    System.out.println("<<Piece captured!>>");
                    --oNumPieces;
                    return flag;
                }
            }

        }
        //playerO's turn
        if (turnStatus.equals("o")) {
            //case 1: move
            if (clearToMove(rowF, colF)) {
                board[rowI][colI] = "_";
                board[rowF][colF] = "o";
                return "";
            }
            //case 2: capture
            if (validCapture(rowI, colI, rowF, colF)) {
                if (colI < colF) {
                    //right capture & jump
                    board[rowI][colI] = "_";
                    board[rowF][colF] = "_";
                    board[rowF + 1][colF + 1] = "o";
                    System.out.println("<<Piece captured!>>");
                    --xNumPieces;
                    return "c";
                } else {
                    //left capture & jump
                    board[rowI][colI] = "_";
                    board[rowF + 1][colF - 1] = "o";
                    board[rowF][colF] = "_";
                    System.out.println("<<Piece captured!>>");
                    --xNumPieces;
                    return "c";
                }
            }
        }
        return "";
    }

    /**
     * This method updates the total possible number of moves for each player,
     * after each player takes a turn. It counts the total possible moves for
     * each piece. Note: this method counts duplicate spaces, since it counts
     * possible moves for EACH piece. This could be improved, but does not
     * affect the checkWinStatus method.
     */
    public void updateMovesAvailable() {
        int count = 0;
        //if it is playerX's turn, check each cell for an "x"

        for (int row = 0; row < 8; row++) {
            for (int col = 1; col < 9; col++) {
                //if the cell contains an x, check for available moves
                if (board[row][col].equals("x")) {
                    //if piece is in row 0, no available moves
                    if (row == 0) {
                        count += 0;
                    } //if piece is in row 1, check for clearToMove only b/c player can't capture from this cell without going off the board
                    else if (row == 1) {
                        //if piece is in column 1, check right side only
                        if (col == 1) {
                            if (clearToMove(row - 1, col + 1)) {
                                count++;
                            }
                        }//else check left side only
                        else if (col == 8) {
                            if (clearToMove(row - 1, col - 1)) {
                                count++;
                            }
                        }//check boths sides
                        else {
                            if (clearToMove(row - 1, col + 1)) {
                                count++;
                            }
                            if (clearToMove(row - 1, col - 1)) {
                                count++;
                            }
                        }
                    } //for rows 2-7 & col==1, check only the right side
                    else if (col == 1) {
                        //check clearToMove on right and capture on right
                        if (clearToMove(row - 1, col + 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row - 1, col + 1)) {
                            count++;
                        }
                    } //for rows 2-7 & col 2, check all but capture on left
                    else if (col == 2) {

                        if (clearToMove(row - 1, col + 1)) {
                            count++;
                        }
                        if (clearToMove(row - 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row - 1, col + 1)) {
                            count++;
                        }
                    } //for rows-2-7 & col == 7, check all but capture on right
                    else if (col == 7) {
                        //check clearToMove on right&left, capture on left
                        if (clearToMove(row - 1, col + 1)) {
                            count++;
                        }
                        if (clearToMove(row - 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row - 1, col - 1)) {
                            count++;
                        }

                    } //for rows 2-7 & col ==8, check left side only
                    else if (col == 8) {
                        //check left side only for available move
                        if (clearToMove(row - 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row - 1, col - 1)) {
                            count++;
                        }
                    } //else piece is in the middle of the board, so check both sides for move and capture
                    else {
                        if (validCapture(row, col, row - 1, col + 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row - 1, col - 1)) {
                            count++;
                        }
                        if (clearToMove(row - 1, col - 1)) {
                            count++;
                        }
                        if (clearToMove(row - 1, col + 1)) {
                            count++;
                        }
                    }
                }//else cell does not contain x
            }
        }//close for loops
        System.out.println("---Game Info--\nx moves: " + count);
        System.out.println("x pieces: " + xNumPieces);
        xMovesAvailable = count;

        //if it is playerO's turn, calc available moves
        count = 0; //reset
        for (int row = 0; row < 8; row++) {
            for (int col = 1; col < 9; col++) {
                //if the cell contains an o, check for available moves
                if (board[row][col].equals("o")) {
                    //if piece is in row 0, no available moves
                    if (row == 7) {
                        count += 0;
                    } //if piece is in row 1, check for clearToMove only b/c player can't capture from this cell without going off the board
                    else if (row == 6) {
                        //if piece is in column 1, check right side only
                        if (col == 1) {
                            if (clearToMove(row + 1, col + 1)) {
                                count++;
                            }
                        } //else check left side only
                        else if (col == 8) {
                            if (clearToMove(row + 1, col - 1)) {
                                count++;
                            }
                        }//check boths sides
                        else {
                            if (clearToMove(row + 1, col + 1)) {
                                count++;
                            }
                            if (clearToMove(row + 1, col - 1)) {
                                count++;
                            }
                        }
                    } //for rows 2-7 & col==1, check only the right side
                    else if (col == 1) {
                        //check clearToMove on right and capture on right
                        if (clearToMove(row + 1, col + 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row + 1, col + 1)) {
                            count++;
                        }
                    } //for rows 2-7 & col 2, check all but capture on left
                    else if (col == 2) {
                        //check clearToMove on right&left, capture only on right
                        if (clearToMove(row + 1, col + 1)) {
                            count++;
                        }
                        if (clearToMove(row + 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row + 1, col + 1)) {
                            count++;
                        }
                    } //for rows-2-7 & col == 7, check all but capture on right
                    else if (col == 7) {
                        //check clearToMove on right&left, capture on left
                        if (clearToMove(row + 1, col + 1)) {
                            count++;
                        }
                        if (clearToMove(row + 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row + 1, col - 1)) {
                            count++;
                        }

                    } //for rows 2-7 & col ==8, check left side only
                    else if (col == 8) {
                        //check left side only for available move
                        if (clearToMove(row + 1, col - 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row + 1, col - 1)) {
                            count++;
                        }
                    } //else piece is in the middle of the board, so check both sides for move and capture
                    else {
                        if (validCapture(row, col, row + 1, col + 1)) {
                            count++;
                        }
                        if (validCapture(row, col, row + 1, col - 1)) {
                            count++;
                        }
                        if (clearToMove(row + 1, col - 1)) {
                            count++;
                        }
                        if (clearToMove(row + 1, col + 1)) {
                            count++;
                        }
                    }
                }//else cell does not contain x
            }
        }//end of for loops
        System.out.println("o moves: " + count);
        System.out.println("o pieces: " + oNumPieces + "\n--------------");
        oMovesAvailable = count;

    }//end of method

//    test methods
//    public int getXMovesAvail() {
//        return xMovesAvailable;
//    }
//
//    public int getOMovesAvail() {
//        return oMovesAvailable;
//    }
    /**
     * This method checks to see if a player has won. A player can win if their
     * opponent has no pieces remaining or no moves remaining.
     *
     * @return valid
     */
    public boolean checkWinStatus() {
        if (xNumPieces == 0) {
            System.out.println("Player X has no more pieces--Player O wins!");
            return true;
        } else if (oNumPieces == 0) {
            System.out.println("Player O has no more pieces--Player X wins!");
            return true;
        } else if (xMovesAvailable == 0) {
            System.out.println("Player X has no more moves--Player O wins!");
            return true;
        } else if (oMovesAvailable == 0) {
            System.out.println("Player O has no more moves--Player X wins!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method marks the end of player's turn and changes turnStatus to the
     * opponent's turn.
     */
    public void switchPlayer() {
        //if it is currently playerX's turn, switch to playerO
        if (turnStatus.equals("x")) {
            turnStatus = "o";
        } //else it is currenly playerO's turn, switch to playerX
        else {
            turnStatus = "x";
        }

    }
}
