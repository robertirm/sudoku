package sudoku.persistence;

import sudoku.domain.SudokuGame;

import java.io.IOException;

/**
 * Storage fot the game data
 */
public interface IRepository {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
