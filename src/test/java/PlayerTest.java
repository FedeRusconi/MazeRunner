import mazerunner.engine.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *  Test Class for Player.
 *
 * @author Federico Rusconi
 *
 */
public class PlayerTest {

    private Player player;

    @AfterEach
    public void destroy(TestInfo testInfo){
        System.out.printf("%s Passed.\n", testInfo.getDisplayName());
    }

    @Test
    void testMoveUp(){
        player = new Player(10, 0, 10, 10);
        player.moveUp();
        assertEquals(9, player.getX());
        assertEquals(0, player.getY());
        assertEquals(9, player.getStamina());
        assertEquals(1, player.getMoves());
    }

    @Test
    void testMoveRight(){
        player = new Player(10, 0, 10, 10);
        player.moveRight();
        assertEquals(10, player.getX());
        assertEquals(1, player.getY());
        assertEquals(9, player.getStamina());
        assertEquals(1, player.getMoves());
    }

    @Test
    void testMoveDown(){
        player = new Player(0, 0, 10, 10);
        player.moveDown();
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
        assertEquals(9, player.getStamina());
        assertEquals(1, player.getMoves());
    }

    @Test
    void testMoveLeft(){
        player = new Player(0, 10, 10, 10);
        player.moveLeft();
        assertEquals(0, player.getX());
        assertEquals(9, player.getY());
        assertEquals(9, player.getStamina());
        assertEquals(1, player.getMoves());
    }

    @Test
    void testSetGetLocation(){
        player = new Player(10);
        player.setLocation(5, 8);
        assertEquals(5, player.getX());
        assertEquals(8, player.getY());
    }

    @Test
    void testSetGetStamina(){
        player = new Player(10);
        player.setStamina(5);
        assertEquals(5, player.getStamina());
    }

    @Test
    void testStaminaUp(){
        player = new Player(10);
        player.setStamina(0);
        player.staminaUp(3);
        assertEquals(3, player.getStamina());
    }

    @Test
    void testStaminaDown(){
        player = new Player(10);
        player.setStamina(10);
        player.staminaDown(3);
        assertEquals(7, player.getStamina());
    }

    @Test
    void testSetGetCoins(){
        player = new Player(10);
        player.setCoins(5);
        assertEquals(5, player.getCoins());
    }

    @Test
    void testCoinsUp(){
        player = new Player(10);
        player.setCoins(0);
        player.coinsUp(3);
        assertEquals(3, player.getCoins());
    }

    @Test
    void testCoinsDown(){
        player = new Player(10);
        player.setCoins(10);
        player.coinsDown(3);
        assertEquals(7, player.getCoins());
    }

}
