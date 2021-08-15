package sudoku.logic;

import sudoku.domain.SudokuGame;
import java.util.Random;

public class Utilities {
    /**
     *  Utility function to copy a Sudoku board into a given variable
     * @param oldBoard: the board to be copied
     * @param newBoard: the new board with the same elements as the oldBoard
     */
    public static void copySudokuBoard(int[][] oldBoard, int[][] newBoard){
        for (int xValue = 0; xValue < SudokuGame.BOARD_SIZE; xValue++) {
            System.arraycopy(oldBoard[xValue], 0, newBoard[xValue], 0, SudokuGame.BOARD_SIZE);
        }
    }

    /**
     *  Utility function to copy a Sudoku board and return the copy
     * @param oldBoard: the board to be copied
     * @return a new board with the same values as the old board
     */
    public static int[][] copySudokuBoard(int[][] oldBoard){
        int[][] copiedBoard = new int[SudokuGame.BOARD_SIZE][SudokuGame.BOARD_SIZE];

        for (int xValue = 0; xValue < SudokuGame.BOARD_SIZE; xValue++) {
            System.arraycopy(oldBoard[xValue], 0, copiedBoard[xValue], 0, SudokuGame.BOARD_SIZE);
        }
        return copiedBoard;
    }

    /**
     * Prints a sudoku board in console
     * Useful for debugging
     * @param board: the board to be printed
     *             it will be shown as a 9x9 matrix
     */
    public static void printSudokuBoardInConsole(int[][] board){
        for (int xValue = 0; xValue < SudokuGame.BOARD_SIZE; xValue++) {
            for (int yValue = 0; yValue < SudokuGame.BOARD_SIZE; yValue++) {
                System.out.print(board[xValue][yValue]+" ");
            }
            System.out.println();
        }
    }

    /**
     * Empties the values in a sudoku board
     * @param board: 9x9 matrix
     */
    public static void clearArray(int[][] board) {
        for (int xIndex = 0; xIndex < SudokuGame.BOARD_SIZE; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.BOARD_SIZE; yIndex++) {
                board[xIndex][yIndex] = 0;
            }
        }
    }

    /**
     * Shuffles the elements in an int array
     * @param ar: the array to be shuffled
     */
    public static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


}
