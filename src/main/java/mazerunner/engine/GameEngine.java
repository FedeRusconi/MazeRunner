package mazerunner.engine;

import java.io.*;
import java.util.Random;

/**
 * Engine of the MazeRunner
 * The engine is responsible for instantiating a maze and a player.
 * The engine controls both the flow of the game and its outcome
 *
 * @author Federico Rusconi
 *
 */
public class GameEngine implements Serializable {

    private final Maze maze;
    private final Player player;
    private final Random rand;

    public static final int DIRECTION_UP = 8;
    public static final int DIRECTION_RIGHT = 6;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 4;

    /**
     * Creates a square game board of default size 10 and difficulty 5.
     * Create a new player at entrance of maze.
     * Place exit randomly in maze's perimeter.
     * Generate items to add to maze.
     */
    public GameEngine() {
        this(10, 5);
    }

    /**
     * Create a square game board of provided size.
     * Create a new player at entrance of maze.
     * Place exit randomly in maze's perimeter.
     * Generate items to add to maze.
     *
     * @param size The width and height.
     * @param difficulty The chose difficulty level.
     */
    public GameEngine(int size, int difficulty) {
        rand = new Random();
        //New Maze
        maze = new Maze(size);
        //Create player at entrance by default
        player = new Player(size);
        //Mark cell where player is on
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Place exit
        maze.placeExitCell();
        //Generate five gold coins
        generateItems('c', 5);
        //Generate traps - number is given by difficulty
        generateItems('t', difficulty);
        //Generate apples - number is given by difficulty
        generateItems('a', 10 - difficulty);
    }

    /** Generate a number of selected items and add them to random cells
     *
     * @param type The subclass of Type to generate
     *             'c' for Coin
     *             'a' for Apple
     *             't' for Trap.
     * @param numOfItems The number of items to generate.
     */
    public void generateItems(char type, int numOfItems){
        for (int i = 0; i < numOfItems; i++) {
            //Continue until a suitable cell is found
            while (true){
                Item item;
                //Instantiate item depending on type required
                switch (type){
                    case 'c':
                        item = new Coin();
                        break;
                    case 't':
                        item = new Trap();
                        break;
                    case 'a':
                        item = new Apple();
                        break;
                    default:
                        item = null;
                        break;
                }
                //Random numbers for location
                int x = rand.nextInt(maze.getSize());
                int y = rand.nextInt(maze.getSize());
                //Find cell
                Cell randomCell = maze.getCell(x, y);
                //If cell does not already have an item and is not entrance or exit
                if(!randomCell.hasItem() && randomCell.isDefault()){
                    randomCell.setItem(item);
                    break;
                }
            }
        }
    }

    /**
     * Move player up by one.
     * Perform action of cell's item.
     *
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse moveUp(){
        ActionResponse actionResponse;
        //If player is not at top row
        if(player.getX() > 0){
            //Remove player from old cell
            maze.getCell(player.getX(), player.getY()).setPlayer(false);
            //Move player
            player.moveUp();
            //Find new cell
            Cell newLocation = maze.getCell(player.getX(), player.getY());
            //Add player to new cell
            newLocation.setPlayer(true);
            //Perform cell item action
            actionResponse = doItemAction(newLocation);
            actionResponse.addMsg(0, "Moved up successfully");
            //If game has not ended already and player is out of stamina
            if(!actionResponse.gameHasEnded() && player.getStamina() == 0){
                actionResponse.addMsg("No more stamina");
                gameOver(actionResponse);
            }
        } else {
            actionResponse = new ActionResponse(0, "Move failed - Boundary reached");
        }
        return actionResponse;
    }

    /**
     * Move player to the right by one.
     * Perform action of cell's item.
     *
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse moveRight(){
        ActionResponse actionResponse;
        //If player is not at last column
        if(player.getY() < maze.getMaxIndex()){
            //Remove player from old cell
            maze.getCell(player.getX(), player.getY()).setPlayer(false);
            //Move player
            player.moveRight();
            //Find new cell
            Cell newLocation = maze.getCell(player.getX(), player.getY());
            //Add player to new cell
            newLocation.setPlayer(true);
            //Perform cell action
            actionResponse = doItemAction(newLocation);
            actionResponse.addMsg(0, "Moved right successfully");
            //If game has not ended already and player is out of stamina
            if(!actionResponse.gameHasEnded() && player.getStamina() == 0){
                actionResponse.addMsg("No more stamina");
                gameOver(actionResponse);
            }
        } else {
            actionResponse = new ActionResponse(0, "Move failed - Boundary reached");
        }
        return actionResponse;
    }

    /**
     * Move player down by one.
     * Perform action of cell's item.
     *
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse moveDown(){
        ActionResponse actionResponse;
        //If player is not at last row
        if(player.getX() < maze.getMaxIndex()){
            //Remove player from old cell
            maze.getCell(player.getX(), player.getY()).setPlayer(false);
            //Move player and decrease stamina
            player.moveDown();
            //Find new cell
            Cell newLocation = maze.getCell(player.getX(), player.getY());
            //Add player to new cell
            newLocation.setPlayer(true);
            //Perform cell action
            actionResponse = doItemAction(newLocation);
            actionResponse.addMsg(0, "Moved down successfully");
            //If game has not ended already and player is out of stamina
            if(!actionResponse.gameHasEnded() && player.getStamina() == 0){
                actionResponse.addMsg("No more stamina");
                gameOver(actionResponse);
            }
        } else {
            actionResponse = new ActionResponse(0, "Move failed - Boundary reached");
        }
        return actionResponse;
    }

    /**
     * Move player to the left by one.
     * Perform action of cell's item.
     *
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse moveLeft(){
        ActionResponse actionResponse;
        //If player is not at first column
        if(player.getY() > 0){
            //Remove player from old cell
            maze.getCell(player.getX(), player.getY()).setPlayer(false);
            //Move player
            player.moveLeft();
            //Find new cell
            Cell newLocation = maze.getCell(player.getX(), player.getY());
            //Add player to new cell
            newLocation.setPlayer(true);
            //Perform cell action
            actionResponse = doItemAction(newLocation);
            actionResponse.addMsg(0, "Moved left successfully");
            //If game has not ended already and player is out of stamina
            if(!actionResponse.gameHasEnded() && player.getStamina() == 0){
                actionResponse.addMsg("No more stamina");
                gameOver(actionResponse);
            }
        } else {
            actionResponse = new ActionResponse(0, "Move failed - Boundary reached");
        }
        return actionResponse;
    }

    /**
     * Perform action of cell's item
     * OR
     * End game if cell is exit
     *
     * @param cell The cell that the player is moving into.
     *
     * @return The response to the action of type ActionResponse.
     */
    private ActionResponse doItemAction(Cell cell){
        ActionResponse actionResponse = new ActionResponse(1);
        if(cell.hasItem()){
            actionResponse = cell.getItem().doAction(player);
            //If item within cell is consumable - remove it
            if(cell.getItem() instanceof Consumable){
                cell.setItem(null);
            }
            //If action brings game to end
            if(actionResponse.gameHasEnded()){
                gameOver(actionResponse);
            }
        } else if(cell.isExit()){
            actionResponse = new ActionResponse(1, "Congratulations, You Won!!!", true);
            actionResponse.addMsg("Score: " + player.getCoins());
        }
        return actionResponse;
    }

    /**
     * Game is over - Lost
     *
     * @param actionResponse The response from last action
     */
    private void gameOver(ActionResponse actionResponse){
        actionResponse.addMsg("Game Over");
        actionResponse.addMsg("Thank you for playing!");
        actionResponse.addMsg("Score: -1");
        actionResponse.setGameEnd(true);
    }

    /**
     * Save game with default file name
     *
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse saveGame() throws Exception {
        BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream("maze.save"));
        ObjectOutputStream objOutput = new ObjectOutputStream(bufferOutput);
        objOutput.reset();
        objOutput.writeObject(this);
        objOutput.close();
        return new ActionResponse(1, "Game Saved Successfully");
    }

    /**
     * Save game with provided file name
     *
     * @param  fileName The name of the file
     * @return The response to the action of type ActionResponse.
     */
    public ActionResponse saveGame(String fileName) throws Exception {
        BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream(fileName));
        ObjectOutputStream objOutput = new ObjectOutputStream(bufferOutput);
        objOutput.reset();
        objOutput.writeObject(this);
        objOutput.close();
        return new ActionResponse(1, "Game Saved Successfully");
    }

    /**
     * Load saved game with default file name
     *
     * @return Saved game as GameEngine instance
     */
    public static GameEngine loadGame() throws Exception {
        BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream("maze.save"));
        ObjectInputStream objInput = new ObjectInputStream(bufferInput);
        GameEngine engine = (GameEngine) objInput.readObject();
        objInput.close();
        return engine;
    }

    /**
     * Load saved game with provided file name
     *
     * @param fileName The name of the file to load
     * @return Saved game as GameEngine instance
     */
    public static GameEngine loadGame(String fileName) throws Exception {
        BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(fileName));
        ObjectInputStream objInput = new ObjectInputStream(bufferInput);
        GameEngine engine = (GameEngine) objInput.readObject();
        objInput.close();
        return engine;
    }

    /**
     * Return the maze
     *
     * @return Maze instance
     */
    public Maze getMaze(){
        return maze;
    }

    /**
     * Get the current player
     *
     * @return the player instance
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Plays a text-based game
     */
    public static void main(String[] args) {
        TextUI.playTextGame();
    }
}
