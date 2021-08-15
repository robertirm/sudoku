package sudoku.logic;

import sudoku.constants.GameState;
import sudoku.domain.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class that implements the logic of the game
 * It is used to generate a new game and validate it
 */
public class GameLogic {
    GameGenerator gameGenerator;

    public GameLogic() {
        this.gameGenerator = new GameGenerator();
    }

    /**
     * Generates a new solvable sudoku board
     * @return a 9x9 matrix representing the board
     */
    public SudokuGame getNewGame() {
        gameGenerator = new GameGenerator();
        return new SudokuGame(GameState.NEW,gameGenerator.getBoard());
    }

    /**
     *  Checks if the board is full and the game is completed
     * @param board: the game grid
     * @return the gamestate that the game is in
     */
    public GameState checkForCompletion(int[][] board) {
        if(invalidSudoku(board)) return GameState.ACTIVE;
        if(emptyTiles(board)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    /**
     * Checks if there are empty tiles in the board
     * @param board: the game grid
     * @return true, if there are empty tiles
     *         false, otherwise
     */
    private boolean emptyTiles(int[][] board) {
        for (int xCoord = 0; xCoord < SudokuGame.BOARD_SIZE; xCoord++) {
            for (int yCoord = 0; yCoord < SudokuGame.BOARD_SIZE; yCoord++) {
                if (board[xCoord][yCoord] == 0) return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current state of the game is solvable
     * @param board: the game grid
     * @return true, if the game is still solvable
     *         false, otherwise
     */
    private boolean invalidSudoku(int[][] board) {
        if(invalidRows(board)) return true;
        if(invalidColumns(board)) return true;
        if(invalidRegions(board)) return true;
        return false;
    }

    /**
     * Checks every 3x3 region of the game
     * @param grid: the game grid
     * @return true, if each region has tiles with different values
     *         false, otherwise
     */
    private boolean invalidRegions(int[][] grid) {
        if (invalidBox(0, 0, grid)) return true;
        if (invalidBox(0, 3, grid)) return true;
        if (invalidBox(0, 6, grid)) return true;
        if (invalidBox(3, 0, grid)) return true;
        if (invalidBox(3, 3, grid)) return true;
        if (invalidBox(3, 6, grid)) return true;
        if (invalidBox(6, 0, grid)) return true;
        if (invalidBox(6, 3, grid)) return true;
        if (invalidBox(6, 6, grid)) return true;
        return false;
    }

    /**
     * Validates a single region
     * @param xValue: the x coordinate of the top left tile of the current box
     * @param yValue: the y coordinate
     * @param board: the game grid
     * @return true, if the region is valid
     *          false, otherwise
     */
    private boolean invalidBox(int xValue, int yValue, int[][] board) {
        int[] xAxisMovement = {0,0,0,1,1,1,2,2,2};
        int[] yAxisMovement = {0,1,2,0,1,2,0,1,2};
        ArrayList<Integer> values = new ArrayList<>();
        for (int move = 0; move < 9; move++) {
                values.add(board[xValue+xAxisMovement[move]][yValue+yAxisMovement[move]]);
        }
        for (int index = 1; index <= 9; index++) {
            if (Collections.frequency(values, index) > 1) return true;
        }
        return false;
    }

    /**
     * Validates each column of the grid
     * @param board: the game grid
     * @return true, if each column is valid
     *         false, otherwise
     */
    private boolean invalidColumns(int[][] board) {
        for (int col = 0; col < SudokuGame.BOARD_SIZE; col++) {
            for (int row1 = 0; row1 < SudokuGame.BOARD_SIZE - 1; row1++) {
                for (int row2 = row1 + 1; row2 < SudokuGame.BOARD_SIZE; row2++) {
                     if (board[row1][col] == board[row2][col]) return true;
                }
            }
        }
        return false;
    }

    /**
     * Validates each row of the grid
     * @param board: the game grid
     * @return true, if each row is valid
     *         false, otherwise
     */
    private boolean invalidRows(int[][] board) {
        for(int row = 0; row < SudokuGame.BOARD_SIZE; row++){
            for (int col1 = 0; col1 < SudokuGame.BOARD_SIZE - 1; col1++) {
                for (int col2 = col1 + 1; col2 < SudokuGame.BOARD_SIZE; col2++) {
                    if(board[row][col1] == board[row][col2]) return true;
                }
            }
        }
        return false;
    }


}
