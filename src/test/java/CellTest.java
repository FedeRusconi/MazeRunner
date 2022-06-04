import mazerunner.engine.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Cell.
 *
 * @author Federico Rusconi
 *
 */
public class CellTest {

    private Cell cell;

    @BeforeEach
    public void init(){
        cell = new Cell();
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testSetGetType(){
        cell.setType(CellType.ENTRANCE);
        assertEquals(CellType.ENTRANCE, cell.getType());
    }

    @Test
    void testSetGetItem(){
        cell.setItem(new Apple());
        assertTrue(cell.hasItem());
    }

    @Test
    void testSetGetPlayer(){
        cell.setPlayer(true);
        assertTrue(cell.hasPlayer());
    }

    @Test
    void testIsDefault(){
        cell.setType(CellType.DEFAULT);
        assertTrue(cell.isDefault());
    }

    @Test
    void testIsEntrance(){
        cell.setType(CellType.ENTRANCE);
        assertTrue(cell.isEntrance());
    }

    @Test
    void testIsExit(){
        cell.setType(CellType.EXIT);
        assertTrue(cell.isExit());
    }

}
