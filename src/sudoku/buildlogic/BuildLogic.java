package sudoku.buildlogic;

import sudoku.domain.SudokuGame;
import sudoku.logic.GameLogic;
import sudoku.persistence.IRepository;
import sudoku.persistence.Repository;
import sudoku.ui.ControlLogic;
import sudoku.ui.IUserInterface;

import java.io.IOException;

/**
 * build logic for the app
 */
public class BuildLogic {
    public static void build(IUserInterface.View userInterface ) throws IOException{
        SudokuGame initialState;
        IRepository repo = new Repository();
        GameLogic gameLogic = new GameLogic();

        try{
            initialState = repo.getGameData();
        }catch (IOException e){
            initialState = gameLogic.getNewGame();
            repo.updateGameData(initialState);
        }

        IUserInterface.EventListener uiLogic =  new ControlLogic(gameLogic,repo,userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
