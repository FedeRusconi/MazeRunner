import mazerunner.engine.Cell;
import mazerunner.engine.Maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Maze.
 *
 * @author Federico Rusconi
 *
 */
public class MazeTest {

    Maze maze;

    @BeforeEach
    public void init(){
        maze = new Maze(10);
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testGetSize(){
        assertEquals(10, maze.getSize());
    }

    @Test
    void testGetMaxIndex(){
        assertEquals(9, maze.getMaxIndex());
    }

    @Test
    void testGenerateMazeGrid(){
        assertEquals(10, maze.getSize());
        //Test entrance
        Cell cell = maze.getCell(maze.getMaxIndex(), 0);
        assertTrue(cell.isEntrance());
    }

    @Test
    void testPlaceExitCell(){
        //Only 1 exit can exist
        int counter = 0;
        maze.placeExitCell();
        for(int x = 0; x < maze.getSize(); x ++){
            for(int y = 0; y < maze.getSize(); y ++){
                Cell cell = maze.getCell(x, y);
                if(cell.isExit()){
                    counter++;
                }
            }
        }
        assertEquals(1, counter);
    }

}
