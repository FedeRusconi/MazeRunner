package mazerunner.engine;

/**
 *  This interface is used by classes that have a location in the grid and can be moved.
 *
 * @author Federico Rusconi
 *
 */
public interface Movable {

    /** Set location within the grid
     *
     * @param x X Position
     * @param y Y Position
     */
    void setLocation(int x, int y);

    /** Move up */
    void moveUp();

    /** Move Right */
    void moveRight();

    /** Move Down */
    void moveDown();

    /** Move Left */
    void moveLeft();

    /**
     * Get x property
     *
     * @return X coordinate
     */
    int getX();

    /**
     * Get y property
     *
     * @return Y coordinate
     */
    int getY();


}
