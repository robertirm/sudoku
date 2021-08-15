package sudoku.domain;

import sudoku.constants.GameState;
import sudoku.logic.Utilities;

import java.io.Serializable;

/**
 *    The Sudoku game that contains the board and the state of it
 */
public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int[][] board;
    private final int[][] startingBoard;

    public static final int BOARD_SIZE = 9;
    public static final int BOX_SIZE = (int)Math.sqrt(BOARD_SIZE);

    public SudokuGame(GameState gameState, int[][] board) {
        this.gameState = gameState;
        this.board = board;
        this.startingBoard = Utilities.copySudokuBoard(board);
    }

    public int[][] getStartingBoard() {
        return startingBoard;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getBoard(){
        return Utilities.copySudokuBoard(board);
    }
}
