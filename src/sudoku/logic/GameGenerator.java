package sudoku.logic;

import sudoku.domain.Coordinates;

import java.util.ArrayList;
import java.util.Random;

import static sudoku.domain.SudokuGame.BOARD_SIZE;
import static sudoku.domain.SudokuGame.BOX_SIZE;

/**
 * A class that generates a new solvable board using sudoku solving algorithm implemented with backtracking
 */
public class GameGenerator {
    private int[][] board;
    private ArrayList<Coordinates> emptyTiles;
    private boolean isSolvable;
    private int[] possibleValues;
    private int solutionsCounter;

    public GameGenerator() {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        constructorUtil();
    }

    /**
     * Get the current generated board
     * @return a new grid
     */
    public int[][] getBoard(){
        Utilities.clearArray(board);
        stabilize();
        fillBoard(0);
        removeTilesFromTheBoard(30);
        return Utilities.copySudokuBoard(board);
    }

    /**
     * Initialises the class fields
     */
    private void constructorUtil(){
        Random randomGenerator = new Random(System.currentTimeMillis());
        emptyTiles = new ArrayList<>();
        getEmptyLocations();
        solutionsCounter = 0;
        isSolvable = false;
        int[] ar = { 1,2,3,4,5,6,7,8,9 };
        Utilities.shuffleArray(ar);
        possibleValues = ar;
    }

    /**
     * Gets the coordinates of the tiles which are empty
     */
    private void getEmptyLocations(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(board[i][j] == 0)
                {
                    emptyTiles.add(new Coordinates(i,j));
                }
            }
        }
    }

    /**
     * Generates a new board using recursive backtracking
     * Each tile is assigned a value that will lead in the end to a solvable board
     * @param currentTileNr: the current tile
     */
    private void fillBoard(int currentTileNr){
        Utilities.shuffleArray(possibleValues);
        for (int index = 0; index < possibleValues.length && !isSolvable; index++) {
            int xIndex = emptyTiles.get(currentTileNr).getX();
            int yIndex = emptyTiles.get(currentTileNr).getY();

            board[xIndex][yIndex] = possibleValues[index];
            if (checkRow(xIndex) &&
                    checkColumn(yIndex) &&
                    checkBox((xIndex/3) * 3, (yIndex/3) * 3)) {
                if (currentTileNr + 1 == emptyTiles.size()) {
                    isSolvable = true;
                }
                else
                    fillBoard(currentTileNr + 1);
            }

            if (!isSolvable)
                board[xIndex][yIndex] = 0;

        }
    }

    /**
     * Checks if a region is valid
     * @param rowStart: x coordinate of the top left tile in the box
     * @param columnStart: y coordinate
     * @return true, if the box is valid
     *          false, otherwise
     */
    private boolean checkBox(int rowStart, int columnStart) {
        int[] values = new int[9];
        int sizeofValues = 0;
        for (int xIndex = rowStart; xIndex < rowStart + BOX_SIZE; xIndex++) {
            for (int yIndex = columnStart; yIndex < columnStart + BOX_SIZE; yIndex++) {
                if (board[xIndex][yIndex] != 0)
                    values[sizeofValues++] = board[xIndex][yIndex];
            }
        }
        for (int i = 0; i < sizeofValues-1; i++) {
            for (int j = i+1; j < sizeofValues; j++) {
                if(values[i] == values[j] && values[i] != 0 && values[j] != 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks if a column is valid
     * @param column: the column number
     * @return true, if it is valid
     *          false, otherwise
     */
    private boolean checkColumn(int column) {
        for (int row1 = 0; row1 < 8; row1++)
            for (int row2 = row1 + 1; row2 < 9; row2++)
                if (board[row1][column] == board[row2][column] && board[row1][column] != 0 && board[row2][column] != 0)
                    return false;
        return true;
    }

    /**
     * Checks if a row is valid
     * @param row: the column number
     * @return true, if it is valid
     *          false, otherwise
     */
    private boolean checkRow(int row) {
        for (int column1 = 0; column1 < 8; column1++)
            for (int column2 = column1 + 1; column2 < 9; column2++)
                if (board[row][column1] == board[row][column2] && board[row][column1] != 0 && board[row][column2] != 0)
                    return false;
        return true;
    }


    /**
     * Counts the number o solutions for a possible board
     * @param currentTileNr: the current tile
     */
    private void countSolutions(int currentTileNr){
        Utilities.shuffleArray(possibleValues);
        for (int possibleValue : possibleValues) {
            int xIndex = emptyTiles.get(currentTileNr).getX();
            int yIndex = emptyTiles.get(currentTileNr).getY();

            board[xIndex][yIndex] = possibleValue;
            if (checkRow(xIndex) &&
                    checkColumn(yIndex) &&
                    checkBox((xIndex / 3) * 3, (yIndex / 3) * 3)) {
                if (currentTileNr + 1 == emptyTiles.size()) {
                    solutionsCounter++;
                } else
                    fillBoard(currentTileNr + 1);
            }
            board[xIndex][yIndex] = 0;

        }
    }

    /**
     * Resets the generator for a new game to be generated
     */
    private void stabilize(){
        emptyTiles.clear();
        getEmptyLocations();
        solutionsCounter = 0;
    }

    /**
     * Remove tiles from a solved board to get a solvable one
     * @param nrOfRemovals: how many tile should be removed
     */
    private void removeTilesFromTheBoard(int nrOfRemovals){
        Random random = new Random(System.currentTimeMillis());
        while(nrOfRemovals > 0){
            // select a random cell that is not empty
            int xCoord = random.nextInt(9);
            int yCoord = random.nextInt(9);
            while (board[xCoord][yCoord] == 0){
                xCoord = random.nextInt(9);
                yCoord = random.nextInt(9);
            }

            // remember its value in case its removal causes an unsolvable puzzle
            int backUpValue = board[xCoord][yCoord];
            // then make it empty
            board[xCoord][yCoord] = 0;

            int[][] copyOfTheBoard = Utilities.copySudokuBoard(board);
            stabilize();
            countSolutions(0);

            if(solutionsCounter != 1){
                board[xCoord][yCoord] = backUpValue;
                board = copyOfTheBoard;
            }
            nrOfRemovals--;
        }
    }

}
