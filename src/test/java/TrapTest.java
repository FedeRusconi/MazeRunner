import mazerunner.engine.ActionResponse;
import mazerunner.engine.Player;
import mazerunner.engine.Trap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for trap.
 *
 * @author Federico Rusconi
 *
 */
public class TrapTest {

    private Player player;
    private Trap trap;

    @BeforeEach
    public void init(){
        trap = new Trap();
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testDoActionSuccess(){
        player = new Player(0, 10, 10, 10);
        ActionResponse actionResponse = trap.doAction(player);
        assertEquals(1, actionResponse.getStatus());
        assertEquals(9, player.getCoins());
    }

    @Test
    void testDoActionFail(){
        player = new Player(0, 10, 10, 0);
        ActionResponse actionResponse = trap.doAction(player);
        assertEquals(1, actionResponse.getStatus());
        assertTrue(actionResponse.gameHasEnded());
    }

}
