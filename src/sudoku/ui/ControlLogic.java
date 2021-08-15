package sudoku.ui;

import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.domain.SudokuGame;
import sudoku.logic.GameLogic;
import sudoku.persistence.IRepository;

import java.io.IOException;

public class ControlLogic implements IUserInterface.EventListener {
    private final IRepository storage;
    private final IUserInterface.View view;
    private final GameLogic gameLogic;

    public ControlLogic(GameLogic gameLogic,IRepository storage, IUserInterface.View view) {
        this.gameLogic = gameLogic;
        this.storage = storage;
        this.view = view;
    }

    /**
     * Updates the value of a tile with the input from the user
     * @param x: the x coordinate of the tile
     * @param y: the y coordinate of the tile
     * @param input: the new value to be added to the tile
     */
    @Override
    public void onSudokuInput(int x, int y, int input) {
        try{
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getBoard();
            newGridState[x][y] = input;

            gameData = new SudokuGame(gameLogic.checkForCompletion(newGridState),newGridState);
            storage.updateGameData(gameData);
            view.updateSquare(x,y,input);

            if (gameData.getGameState() == GameState.COMPLETE){
                view.showDialog(Messages.GAME_COMPLETE);
            }

        }catch(IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    /**
     *   Initialises a new game after one is completed
     */
    @Override
    public void onDialogClick() {
            try{
                storage.updateGameData(gameLogic.getNewGame());
                view.updateBoard(storage.getGameData());
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    /**
     * Initialises a new game at any given moment
     */
    @Override
    public void onNewGameButtonClick() {
        try {
            SudokuGame newGame = gameLogic.getNewGame();
            storage.updateGameData(newGame);
            view.updateBoard(newGame);
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

}
