package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.buildlogic.BuildLogic;
import sudoku.ui.UserInterface;

import java.io.IOException;

public class App extends Application {
    private UserInterface ui;

    @Override
    public void start(Stage stage) throws Exception {
        ui = new UserInterface(stage);
        try{
            BuildLogic.build(ui);
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
