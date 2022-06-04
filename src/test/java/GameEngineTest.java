import mazerunner.engine.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Game Engine.
 *
 * @author Federico Rusconi
 *
 */
public class GameEngineTest {

    GameEngine engine;
    Player player;
    Maze maze;

    @BeforeEach
    public void init(){
        engine = new GameEngine();
        player = engine.getPlayer();
        maze = engine.getMaze();
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testGenerateItems() {
        engine = new GameEngine(10, 7);
        maze = engine.getMaze();
        Item item;
        int appleCounter = 0;
        int coinCounter = 0;
        int trapCounter = 0;
        for(int x = 0; x < maze.getSize(); x ++){
            for(int y = 0; y < maze.getSize(); y ++){
                Cell cell = maze.getCell(x, y);
                if(cell.hasItem()){
                    item = cell.getItem();
                    if(item instanceof Apple){
                        appleCounter ++;
                    } else if(item instanceof Coin){
                        coinCounter ++;
                    } else if(item instanceof Trap){
                        trapCounter ++;
                    }
                }
            }
        }
        assertEquals(3, appleCounter);
        assertEquals(5, coinCounter);
        assertEquals(7, trapCounter);
    }

    @Test
    void testMoveUpSuccess(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell above player is Apple (test for Consumable) and is not exit
        maze.getCell(maze.getMaxIndex() - 1, 0).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex() - 1, 0).setItem(new Apple());
        //Move
        ActionResponse actionResponse = engine.moveUp();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert item has been consumed
        assertFalse(maze.getCell(player.getX(), player.getY()).hasItem());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
    }

    @Test
    void testMoveUpGameWon(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell above player is exit
        maze.getCell(maze.getMaxIndex() - 1, 0).setItem(null);
        maze.getCell(maze.getMaxIndex() - 1, 0).setType(CellType.EXIT);
        //Move
        ActionResponse actionResponse = engine.moveUp();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful and game is ending
        assertEquals(1, actionResponse.getStatus());
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveUpFailBoundary(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Move
        ActionResponse actionResponse = engine.moveUp();
        //Assert player has not moved
        assertTrue(maze.getCell(0, 0).hasPlayer());
        //Assert action failed
        assertEquals(0, actionResponse.getStatus());
    }

    @Test
    void testMoveUpFailStamina(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's stamina to 1
        player.setStamina(1);
        //Make sure cell above player has no items and is not exit
        maze.getCell(maze.getMaxIndex() - 1, 0).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex() - 1, 0).setItem(null);
        //Move
        ActionResponse actionResponse = engine.moveUp();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveUpFailTrap(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's coins to 0
        player.setCoins(0);
        //Make sure cell above player has trap and is not exit
        maze.getCell(maze.getMaxIndex() - 1, 0).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex() - 1, 0).setItem(new Trap());
        //Move
        ActionResponse actionResponse = engine.moveUp();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveRightSuccess(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell right of  player is Apple (test for Consumable) and is not exit
        maze.getCell(maze.getMaxIndex(), 1).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex(), 1).setItem(new Apple());
        //Move
        ActionResponse actionResponse = engine.moveRight();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert item has been consumed
        assertFalse(maze.getCell(player.getX(), player.getY()).hasItem());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
    }

    @Test
    void testMoveRightGameWon(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell right of player is exit
        maze.getCell(maze.getMaxIndex(), 1).setItem(null);
        maze.getCell(maze.getMaxIndex(), 1).setType(CellType.EXIT);
        //Move
        ActionResponse actionResponse = engine.moveRight();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful and game is ending
        assertEquals(1, actionResponse.getStatus());
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveRightFailBoundary(){
        //Set player location - top right
        player.setLocation(0, maze.getMaxIndex());
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Move
        ActionResponse actionResponse = engine.moveRight();
        //Assert player has not moved
        assertTrue(maze.getCell(0, maze.getMaxIndex()).hasPlayer());
        //Assert action failed
        assertEquals(0, actionResponse.getStatus());
    }

    @Test
    void testMoveRightFailStamina(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's stamina to 1
        player.setStamina(1);
        //Make sure cell right to player has no items and is not exit
        maze.getCell(maze.getMaxIndex(), 1).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex(), 1).setItem(null);
        //Move
        ActionResponse actionResponse = engine.moveRight();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveRightFailTrap(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's coins to 0
        player.setCoins(0);
        //Make sure cell right to player has trap and is not exit
        maze.getCell(maze.getMaxIndex(), 1).setType(CellType.DEFAULT);
        maze.getCell(maze.getMaxIndex(), 1).setItem(new Trap());
        //Move
        ActionResponse actionResponse = engine.moveRight();
        //Assert player has moved
        assertFalse(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveDownSuccess(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell below  player is Apple (test for Consumable) and is not exit
        maze.getCell(1, 0).setType(CellType.DEFAULT);
        maze.getCell(1, 0).setItem(new Apple());
        //Move
        ActionResponse actionResponse = engine.moveDown();
        //Assert player has moved
        assertFalse(maze.getCell(0, 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert item has been consumed
        assertFalse(maze.getCell(player.getX(), player.getY()).hasItem());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
    }

    @Test
    void testMoveDownGameWon(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell below player is exit
        maze.getCell(1, 0).setItem(null);
        maze.getCell(1, 0).setType(CellType.EXIT);
        //Move
        ActionResponse actionResponse = engine.moveDown();
        //Assert player has moved
        assertFalse(maze.getCell(0, 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful and game is ending
        assertEquals(1, actionResponse.getStatus());
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveDownFailBoundary(){
        //Set player location - bottom left
        player.setLocation(maze.getMaxIndex(), 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Move
        ActionResponse actionResponse = engine.moveDown();
        //Assert player has not moved
        assertTrue(maze.getCell(maze.getMaxIndex(), 0).hasPlayer());
        //Assert action failed
        assertEquals(0, actionResponse.getStatus());
    }

    @Test
    void testMoveDownFailStamina(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's stamina to 1
        player.setStamina(1);
        //Make sure cell below player has no items and is not exit
        maze.getCell(1, 0).setType(CellType.DEFAULT);
        maze.getCell(1, 0).setItem(null);
        //Move
        ActionResponse actionResponse = engine.moveDown();
        //Assert player has moved
        assertFalse(maze.getCell(0, 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveDownFailTrap(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's coins to 0
        player.setCoins(0);
        //Make sure cell below player has trap and is not exit
        maze.getCell(1, 0).setType(CellType.DEFAULT);
        maze.getCell(1, 0).setItem(new Trap());
        //Move
        ActionResponse actionResponse = engine.moveDown();
        //Assert player has moved
        assertFalse(maze.getCell(0, 0).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveLeftSuccess(){
        //Set player location - top right
        player.setLocation(0, maze.getMaxIndex());
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell left of player is Apple (test for Consumable) and is not exit
        maze.getCell(0, maze.getMaxIndex() - 1).setType(CellType.DEFAULT);
        maze.getCell(0, maze.getMaxIndex() - 1).setItem(new Apple());
        //Move
        ActionResponse actionResponse = engine.moveLeft();
        //Assert player has moved
        assertFalse(maze.getCell(0, maze.getMaxIndex()).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert item has been consumed
        assertFalse(maze.getCell(player.getX(), player.getY()).hasItem());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
    }

    @Test
    void testMoveLeftGameWon(){
        //Set player location - top right
        player.setLocation(0, maze.getMaxIndex());
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Make sure cell left of player is exit
        maze.getCell(0, maze.getMaxIndex() - 1).setItem(null);
        maze.getCell(0, maze.getMaxIndex() - 1).setType(CellType.EXIT);
        //Move
        ActionResponse actionResponse = engine.moveLeft();
        //Assert player has moved
        assertFalse(maze.getCell(0, maze.getMaxIndex()).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful and game is ending
        assertEquals(1, actionResponse.getStatus());
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveLeftFailBoundary(){
        //Set player location - top left
        player.setLocation(0, 0);
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Move
        ActionResponse actionResponse = engine.moveLeft();
        //Assert player has not moved
        assertTrue(maze.getCell(0, 0).hasPlayer());
        //Assert action failed
        assertEquals(0, actionResponse.getStatus());
    }

    @Test
    void testMoveLeftFailStamina(){
        //Set player location - top right
        player.setLocation(0, maze.getMaxIndex());
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's stamina to 1
        player.setStamina(1);
        //Make sure cell right of player has no items and is not exit
        maze.getCell(0, maze.getMaxIndex() - 1).setType(CellType.DEFAULT);
        maze.getCell(0, maze.getMaxIndex() - 1).setItem(null);
        //Move
        ActionResponse actionResponse = engine.moveLeft();
        //Assert player has moved
        assertFalse(maze.getCell(0, maze.getMaxIndex()).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testMoveLeftFailTrap(){
        //Set player location - top right
        player.setLocation(0, maze.getMaxIndex());
        maze.getCell(player.getX(), player.getY()).setPlayer(true);
        //Set player's coins to 0
        player.setCoins(0);
        //Make sure cell left of player has trap and is not exit
        maze.getCell(0, maze.getMaxIndex() - 1).setType(CellType.DEFAULT);
        maze.getCell(0, maze.getMaxIndex() - 1).setItem(new Trap());
        //Move
        ActionResponse actionResponse = engine.moveLeft();
        //Assert player has moved
        assertFalse(maze.getCell(0, maze.getMaxIndex()).hasPlayer());
        assertTrue(maze.getCell(player.getX(), player.getY()).hasPlayer());
        //Assert action was successful
        assertEquals(1, actionResponse.getStatus());
        //Assert game must end
        assertTrue(actionResponse.gameHasEnded());
    }

    @Test
    void testSaveGame() throws Exception {
        String fileName = "maze.save.test";
        File file = new File(fileName);
        //Make sure file does not exist before saving
        file.delete();
        ActionResponse actionResponse = engine.saveGame(fileName);
        assertEquals(1, actionResponse.getStatus());
        assertTrue(file.exists());
        //Delete test file after use
        assertTrue(file.delete());
    }

    @Test
    void testLoadGame() throws Exception {
        //Set a few attributes to check on game load
        player.setStamina(15);
        player.setLocation(5, 8);
        player.setCoins(7);
        maze.getCell(7, 4).setItem(new Apple());
        maze.getCell(9, 3).setType(CellType.EXIT);
        //Save
        String fileName = "maze.save.test";
        engine.saveGame(fileName);
        //Load
        GameEngine loadEngine = GameEngine.loadGame(fileName);
        //Check previous attributes
        Player loadPlayer = loadEngine.getPlayer();
        assertEquals(5, loadPlayer.getX());
        assertEquals(8, loadPlayer.getY());
        assertEquals(7, loadPlayer.getCoins());
        Maze loadMaze = loadEngine.getMaze();
        assertTrue(loadMaze.getCell(7, 4).hasItem());
        assertTrue(loadMaze.getCell(9, 3).isExit());
        //Delete test file after use
        File file = new File(fileName);
        assertTrue(file.delete());
    }

}
