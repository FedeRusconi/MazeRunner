import mazerunner.engine.ActionResponse;
import mazerunner.engine.Coin;
import mazerunner.engine.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Coin.
 *
 * @author Federico Rusconi
 *
 */
public class CoinTest {

    private Player player;
    private Coin coin;

    @BeforeEach
    public void init(){
        coin = new Coin();
        player = new Player(0, 10, 10, 10);
    }

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testDoAction(){
        ActionResponse actionResponse = coin.doAction(player);
        assertEquals(1, actionResponse.getStatus());
        assertEquals(11, player.getCoins());
    }

}
