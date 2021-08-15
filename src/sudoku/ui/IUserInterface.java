package sudoku.ui;

import sudoku.domain.SudokuGame;

public interface IUserInterface {
    /**
     * Check for signals from the user
     */
    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
        void onNewGameButtonClick();
    }

    /**
     * Updates the view based on the listener
     */
    interface View {
        void setListener(IUserInterface.EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String message);
        void showError(String message);
    }

}
