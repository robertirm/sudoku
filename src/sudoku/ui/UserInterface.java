package sudoku.ui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sudoku.domain.Coordinates;
import sudoku.domain.SudokuGame;

import java.util.HashMap;

public class UserInterface implements IUserInterface.View, EventHandler<KeyEvent> {
    private final Stage stage;
    private final Group root;

    private final HashMap<Coordinates, SudokuTile> tilesCoordinates;
    private IUserInterface.EventListener listener;

    private static final double WINDOW_X = 688;
    private static final double WINDOW_Y = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 585;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,75,140);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(240, 240, 240);

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.tilesCoordinates = new HashMap<>();
        initUserInterface();
    }

    @Override
    public void setListener(IUserInterface.EventListener listener) {
        this.listener = listener;
    }

    private void initUserInterface() {
        stage.setTitle("Sudoku");
        drawBackground(root);
        drawBoard(root);
        drawTiles(root);
        drawGrid(root);
        drawButtons(root);
        stage.show();
    }

    private void drawButtons(Group root){
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e->{
            listener.onNewGameButtonClick();
        });

        root.getChildren().add(newGameButton);
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root,WINDOW_X,WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

    private void drawTiles(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        //how much to move the x or y value after each loop
        final int spacing = 64;

        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * spacing;
                int y = yOrigin + yIndex * spacing;
                SudokuTile tile = new SudokuTile(xIndex, yIndex);

                styleSudokuTile(tile, x, y);
                tile.setOnKeyPressed(this);

                tilesCoordinates.put(new Coordinates(xIndex, yIndex), tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudokuTile tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    private void drawGrid(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(xAndY + 64 * index, BOARD_PADDING, BOARD_X_AND_Y, thickness);

            Rectangle horizontalLine = getLine(BOARD_PADDING, xAndY + 64 * index, thickness, BOARD_X_AND_Y);

            root.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }
    }

    public Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);

        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;

    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTile tile = tilesCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(input);

        if (value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = tilesCoordinates.get(new Coordinates(xIndex, yIndex));

                int tileValue = game.getBoard()[xIndex][yIndex];

                String value = Integer.toString(tileValue);
                if(tileValue == game.getStartingBoard()[xIndex][yIndex] && tileValue!=0){
                    tile.setStyle("-fx-opacity: 0.6;");
                    tile.setDisable(true);
                }
                else{
                    tile.setStyle("-fx-opacity: 1;");
                    tile.setDisable(false);
                }
                if (value.equals("0")) value = "";
                tile.setText(value);
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }


    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().equals("0")
                    || event.getText().equals("1")
                    || event.getText().equals("2")
                    || event.getText().equals("3")
                    || event.getText().equals("4")
                    || event.getText().equals("5")
                    || event.getText().equals("6")
                    || event.getText().equals("7")
                    || event.getText().equals("8")
                    || event.getText().equals("9")
            ) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField)event.getSource()).setText("");
            }
        }

        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTile) source).getX(),
                ((SudokuTile) source).getY(),
                value
        );
    }

}
