import mazerunner.engine.ActionResponse;
import mazerunner.engine.Apple;
import mazerunner.engine.Player;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Apple.
 *
 * @author Federico Rusconi
 *
 */
public class AppleTest {

    private Player player;
    private Apple apple;

    @BeforeEach
    public void init(){
        apple = new Apple();
        player = new Player(0, 10, 10, 10);
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testDoAction(){
        ActionResponse actionResponse = apple.doAction(player);
        assertEquals(1, actionResponse.getStatus());
        assertEquals(13, player.getStamina());
    }

}
