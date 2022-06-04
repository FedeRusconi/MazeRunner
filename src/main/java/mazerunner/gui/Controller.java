package mazerunner.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mazerunner.engine.*;
import java.util.ArrayList;

/**
 * The Controller connects the GUI with the GameEngine.
 *
 * The controller is responsible for dynamically change the GUI in response to a user's action.
 *
 * @author Federico Rusconi
 *
 */
public class Controller {

    private GameEngine engine;

    private boolean gameEnded = false;

    private final String helpMsg =  "Welcome to MazeRunner! Move your player within the maze and get to the exit " +
    "with as many coins as you can.\n\n Be careful, evey move you make will reduce your stamina by 1. " +
    "If your stamina drops to 0, you lose the game. Collect apples to raise your stamina level.\n\n" +
    "Traps will require 1 coin to escape. If you have no coins, you lose the game.";

    //Left top
    @FXML
    private TextField difficultyField;
    @FXML
    private Button startBtn;
    @FXML
    private Button helpBtn;
    //Left bottom
    @FXML
    private Button saveBtn;
    @FXML
    private Button loadBtn;

    //Right top
    @FXML
    private Label staminaValue;
    @FXML
    private Label coinsValue;
    @FXML
    private Label movesValue;
    //Right bottom
    @FXML
    private VBox log;

    //Center
    @FXML
    private AnchorPane mazeContainer;

    //Bottom
    @FXML
    private Button upBtn;
    @FXML
    private Button rightBtn;
    @FXML
    private Button downBtn;
    @FXML
    private Button leftBtn;

    /**
     * Initialize game and
     * define Event handlers
     */
    @FXML
    public void initialize(){
        //Start button
        startBtn.setOnAction(e -> {
            try {
                int difficulty = Integer.parseInt(difficultyField.getText());
                //Start new game only if difficulty is between 0 and 10
                if(difficulty >= 0 && difficulty <= 10){
                    engine = new GameEngine(10, difficulty);
                    startGame();
                } else {
                    showAlert("Invalid Difficulty", "Please enter a number between 0 and 10.");
                }
            } catch (NumberFormatException numEx) {
                showAlert("Invalid Difficulty", "Please enter a number between 0 and 10.");
            } catch (Exception ex){
                showAlert("Error", "Something went wrong, please try again.");
            }
        });
        //Help Button
        helpBtn.setOnAction(e -> {
            //Display alert with help
            showAlert("Help", helpMsg);
        });
        //Save button
        saveBtn.setOnAction(e -> {
            try {
                ActionResponse actionResponse = engine.saveGame();
                addToLog(actionResponse.getMessages());
                showAlert("Game Saved", actionResponse.getMessages().get(0));
            } catch (Exception ex){
                if(ex.getMessage() != null){
                    showAlert("Error", ex.getMessage());
                } else {
                    showAlert("Error", "Something went wrong. Please try again.");
                }
            }
        });
        //Load button
        loadBtn.setOnAction(e -> {
            try {
                engine = GameEngine.loadGame();
                startGame();
                ActionResponse actionResponse = new ActionResponse(1, "Game Loaded Successfully");
                addToLog(actionResponse.getMessages());
            } catch (Exception ex) {
                if(ex.getMessage() != null){
                    showAlert("Error", ex.getMessage());
                } else {
                    showAlert("Error", "Something went wrong. Please try again.");
                }
            }
        });
        //Directional buttons
        upBtn.setOnAction(e -> makeMove(GameEngine.DIRECTION_UP));
        rightBtn.setOnAction(e -> makeMove(GameEngine.DIRECTION_RIGHT));
        downBtn.setOnAction(e -> makeMove(GameEngine.DIRECTION_DOWN));
        leftBtn.setOnAction(e -> makeMove(GameEngine.DIRECTION_LEFT));
    }

    /**
     * Start game -
     * Generate the maze,
     * Enable save button,
     * Enable control buttons
     */
    private void startGame(){
        generateMaze();
        resetLog();
        //Enable save button
        saveBtn.setDisable(false);
        setControlsDisabled(false);
        gameEnded = false;
    }

    /**
     * Generate GUI Maze -
     * Create new MazePane and add it to container,
     * Update player's attributes
     *
     */
    private void generateMaze(){
        //Clear from previous runs
        mazeContainer.getChildren().clear();
        //New MazePane and set size
        MazePane maze = new MazePane(engine);
        maze.prefWidthProperty().bind(mazeContainer.widthProperty());
        maze.prefHeightProperty().bind(mazeContainer.widthProperty());
        //Add to container
        mazeContainer.getChildren().add(maze);
        //Update player's attributes
        updateAttributes(engine.getPlayer());
    }

    /**
     * Update text of attributes displayed in top-right corner
     *
     * @param player The current player instance.
     */
    private void updateAttributes(Player player){
        staminaValue.setText(toString(player.getStamina()));
        coinsValue.setText(toString(player.getCoins()));
        movesValue.setText(toString(player.getMoves()));
    }

    /**
     * Clear log from older messages
     */
    private void resetLog(){
        log.getChildren().clear();
    }

    /**
     * Add messages to log
     *
     * @param messages A list of messages.
     */
    private void addToLog(ArrayList<String> messages){
        Text responseMsg;
        for(String msg : messages){
            responseMsg = new Text(msg + '\n');
            responseMsg.wrappingWidthProperty().bind(log.widthProperty().subtract(10));
            responseMsg.setFill(Color.WHITE);
            log.getChildren().add(0, responseMsg);
        }
    }

    /**
     * Move the player,
     * Re-generate the maze,
     * Add result messages to log,
     * Disable control buttons if game is over
     *
     * @param direction 8 Up - 6 Right - 2 Down - 4 Left
     */
    private void makeMove(int direction){
        ActionResponse actionResponse;
        //Make the move
        switch (direction){
            case GameEngine.DIRECTION_UP:
                actionResponse = engine.moveUp();
                break;
            case GameEngine.DIRECTION_RIGHT:
                actionResponse = engine.moveRight();
                break;
            case GameEngine.DIRECTION_DOWN:
                actionResponse = engine.moveDown();
                break;
            case GameEngine.DIRECTION_LEFT:
                actionResponse = engine.moveLeft();
                break;
            default:
                actionResponse = new ActionResponse();
                break;
        }
        //Re-generate maze
        generateMaze();
        //Add messages to log
        addToLog(actionResponse.getMessages());
        //If game over, disable control buttons
        if (actionResponse.gameHasEnded()) {
            gameEnded = true;
            setControlsDisabled(true);
        }
    }

    /**
     * Open a new simple alert
     *
     * @param title Alert's title
     * @param msg The message to display in Alert
     */
    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        //Remove header to make it a simple alert
        alert.setHeaderText(null);
        alert.setGraphic(null);
        //Display message
        alert.setContentText(msg);
        //Show
        alert.showAndWait();
    }

    /**
     * Disable/Enable control buttons
     *
     * @param disabled True for disable - False for enable
     */
    private void setControlsDisabled(boolean disabled){
        upBtn.setDisable(disabled);
        rightBtn.setDisable(disabled);
        downBtn.setDisable(disabled);
        leftBtn.setDisable(disabled);
    }

    /**
     * Convert an integer to a string.
     *
     * @param value The integer value to be converted
     *
     * @return The converted String
     *
     */
    private String toString(int value){
        return Integer.toString(value);
    }


}
