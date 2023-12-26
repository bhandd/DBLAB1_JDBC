package dblab1.dblab1_jdbc.view;


import dblab1.dblab1_jdbc.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    private final View view;
    public EventHandler<? super ActionEvent> hintHandler;
    public EventHandler<ActionEvent> checkHandler;
    int GRID_SIZE = 2;

    //  private Bord bord;
    private char buttonCheck = '0';

    /**
     * An event handler for mouse clicks on the Sudoku grid.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == view.getNumberByPos(row,col)) {
                        // we got the row and column - now call the appropriate controller method, e.g.
                        MouseEvent(buttonCheck, row, col, view.getNumberTiles());

                        return;
                    }
                }
            }
        }
    };

    /**
     * The event handler for button clicks.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            buttonCheck = PressedButton(actionEvent.getSource());
        }
    };

    /**
     * An event handler for exiting the application.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> eventExitHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.exit(0);
        }
    };

    /**
     * An event handler for saving the game.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> eventSaveGameHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //saveGame();
        }
    };

    /**
     * An event handler for loading the game.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> eventLoadGameHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            //setBord(loadGame());



        }
    };

    /**
     * An event handler for restarting the game.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //getNewBordWithDifficulty(getCurrentDifficulty());

        }
    };

    /**
     * An event handler for restarting the Sudoku game.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    };

    /**
     * An event handler for selecting the Sudoku difficulty on new game.
     */
    public EventHandler<ActionEvent> levelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            ButtonType easy = new ButtonType("Easy");
            ButtonType medium = new ButtonType("Medium");
            ButtonType hard = new ButtonType("Hard");

            alert.getButtonTypes().setAll(easy, medium, hard);
            alert.setTitle("Difficulty");
            alert.setHeaderText(null);
            alert.setContentText("Choose the difficulty");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == easy) {

                //   getNewBordWithDifficulty(SudokuLevel.EASY);

                //  updateBord(view.getNumberTiles());
            } else if (choice.get() == medium) {

                //      getNewBordWithDifficulty(SudokuLevel.MEDIUM);
                //   updateBord(view.getNumberTiles());
            } else if (choice.get() == hard) {

                //     getNewBordWithDifficulty(SudokuLevel.HARD);
                //   updateBord(view.getNumberTiles());
            }
        }
    };

    /**
     * An event handler for displaying the Sudoku rules.
     *
     * @param view The Sudoku game view.
     */
    public EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Rulse");
            alert.setHeaderText("Sudoku Rules");
            alert.setContentText("Every square needs contain a number\n" +
                    "The numbers 1-9 can be used\n" +
                    "In each of the 3x3 boxes the numbers 1-9 can only appare once\n" +
                    "The same numbers can only appare once in each row and column\n");
            alert.showAndWait();
        }
    };

    /**
     * An event handler for checking the validity of the Sudoku board.
     *
     * @param view The Sudoku game view.
     */




    /**
     * Constructs a new `Controller` object, initializing the provided `GridView` instance.
     *
     * @param view The `GridView` object representing the visual representation of the Sudoku board.
     */
    public Controller(View view){
        this.view = view;
        //bord = new Bord(SudokuLevel.DEFAULT);
    }

    /**
     * Constructs a new `Controller` object, initializing the `GridView` and `Bord` objects.
     */
    public Controller() {
        this.view = new View();
        //   this.bord = new Bord(SudokuLevel.DEFAULT);
    }

    /**
     * Sets the internal `Bord` object representing the Sudoku puzzle data.
     * @param bord The `Bord` object containing the Sudoku puzzle data to be set.
     */
    //  public void setBord(Bord bord){
//        this.bord = bord;
//    }


    /**
     * Retrieves the size of the Sudoku grid from SudokuUtilities
     *
     * @return An integer representing the size of the Sudoku grid.
     */
    public int getGridSize(){
        return GRID_SIZE;
    }

    /**
     * Creates a new instance of the `Bord` class representing a new Sudoku puzzle with the specified difficulty level.
     *
     * @param level The desired difficulty level for the new Sudoku puzzle.
     * @return A new `Bord` object representing a new Sudoku puzzle with the specified difficulty level.
     */
//    public Bord getNewBordWithDifficulty(SudokuLevel level){
//        bord = new Bord(level);
//        return bord;
//    }







    /**
     * Takes button of choice ("1-9" or "C") and updateTile
     *
     * @param button button of choice
     * @param x      x position on bord
     * @param y      y position on bord
     */
    public void MouseEvent(char button, int x, int y, Label[][] numberTiles) {
        if (button == 'C') {
            //  bord.removeCurrentValue(x, y);
            //updateTile(numberTiles, x, y);
        }
    }

    /**
     * Extracts the pressed button character from the given `Object` representing a button.
     *
     * @param button The `Object` representing the pressed button.
     * @return The extracted pressed button character.
     */
    public char PressedButton(Object button) {
        return button.toString().charAt(button.toString().length() - 2);
    }


    public void hintHandler(){

    }

    public void setCheckHandler(){

    }


    /**
     * Triggers the hint mechanism to provide hints for the current Sudoku puzzle.
     */
    public void eventHint() {
        //  SudokuUtilities.getHint(bord);

    }






}
