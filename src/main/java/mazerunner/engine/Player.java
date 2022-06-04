package mazerunner.engine;

import java.io.Serializable;

/**
 *  Class that represents the Player
 *
 * @author Federico Rusconi
 *
 */
public class Player implements Movable, Serializable {

    private int x;
    private int y;
    private int stamina;
    private int coins;
    private int moves;

    /**
     * Instantiate player object at entrance of maze (bottom-left), with 12 stamina, and 0 initial coin count.
     *
     * @param size The size of the maze (both height and width)
     */
    public Player(int size){
        this(size - 1, 0, 12, 0);
    }

    /**
     * Instantiate player object with provided x,y location, stamina, and initial coins count.
     *
     * @param x X position
     * @param y Y position
     * @param stamina Initial stamina level
     * @param coins Initial coin count
     */
    public Player(int x, int y, int stamina, int coins) {
        setLocation(x, y);
        setStamina(stamina);
        setCoins(coins);
    }

    /**
     * Move Up
     * Decrease stamina by 1
     * Add 1 to number of moves counter
     * */
    @Override
    public void moveUp(){
        setLocation(getX() - 1, getY());
        staminaDown(1);
        movesUp(1);
    }

    /**
     * Move Right
     * Decrease stamina by 1
     * Add 1 to number of moves counter
     * */
    @Override
    public void moveRight(){
        setLocation(getX(), getY() + 1);
        staminaDown(1);
        movesUp(1);
    }

    /**
     * Move Down
     * Decrease stamina by 1
     * Add 1 to number of moves counter
     * */
    @Override
    public void moveDown(){
        setLocation(getX() + 1, getY());
        staminaDown(1);
        movesUp(1);
    }

    /**
     * Move Left
     * Decrease stamina by 1
     * Add 1 to number of moves counter
     * */
    @Override
    public void moveLeft(){
        setLocation(getX(), getY() - 1);
        staminaDown(1);
        movesUp(1);
    }

    /** Set location of player within maze
     * Position of 0,0 is top-left corner.
     *
     * @param x X Position
     * @param y Y Position
     */
    @Override
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Set player's stamina
     *
     * @param stamina Player's stamina
     */
    public void setStamina(int stamina){
        this.stamina = stamina;
    }

    /**
     * Increment player's stamina
     *
     * @param increment Number to add to current player's stamina
     */
    public void staminaUp(int increment){
        stamina += increment;
    }

    /**
     * Decrement player's stamina
     *
     * @param decrement Number to subtract to current player's stamina
     */
    public void staminaDown(int decrement){
        stamina -= decrement;
    }

    /**
     * Set player's coins
     *
     * @param coins Player's coins count
     */
    public void setCoins(int coins){
        this.coins = coins;
    }

    /**
     * Increment player's coin count
     *
     * @param increment Number to add to current player's coins
     */
    public void coinsUp(int increment){
        coins += increment;
    }

    /**
     * Decrement player's coin count
     *
     * @param decrement Number to subtract to current player's coins
     */
    public void coinsDown(int decrement){
        coins -= decrement;
    }

    /**
     * Increment player's moves
     *
     * @param increment Number to add to current moves counter
     */
    private void movesUp(int increment){
        moves += increment;
    }

    /**
     * Return x attribute
     *
     * @return X coordinate
     */
    @Override
    public int getX(){
        return x;
    }

    /**
     * Return y attribute
     *
     * @return Y coordinate
     */
    @Override
    public int getY(){
        return y;
    }

    /**
     * Return player's stamina
     *
     * @return Stamina level
     */
    public int getStamina(){
        return stamina;
    }

    /**
     * Return player's coins
     *
     * @return Number of coins
     */
    public int getCoins(){
        return coins;
    }

    /**
     * Return player's moves made
     *
     * @return Number of moves
     */
    public int getMoves(){
        return moves;
    }

}
