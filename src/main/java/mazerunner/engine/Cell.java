package mazerunner.engine;

import java.io.Serializable;

/**
 *  Class that represents a Cell of the maze.
 *
 * @author Federico Rusconi
 *
 */
public class Cell implements Serializable {

    private CellType type;
    private Item item;
    private boolean player;

    /**
     * Generate new cell of DEFAULT type (empty cell)
     */
    public Cell(){
        setType(CellType.DEFAULT);
    }

    /** Set type of cell
     *
     * @param type The type of cell - Options: DEFAULT, ENTRANCE, EXIT
     */
    public void setType(CellType type){
        this.type = type;
    }

    /** Set Item within cell
     *
     * @param item Item to assign to cell, can be Coin, Trap or Apple
     */
    public void setItem(Item item){
        this.item = item;
    }

    /**
     * Set if player is on current cell
     *
     * @param value True if player is on current cell - False if not
     */
    public void setPlayer(boolean value){
        player = value;
    }

    /**
     * Get type of cell
     *
     * @return DEFAULT, ENTRANCE or EXIT
     */
    public CellType getType(){
        return type;
    }

    /**
     * Check if cell is entrance
     *
     * @return True or False
     */
    public boolean isEntrance() {
        return getType() == CellType.ENTRANCE;
    }

    /**
     * Check if cell is exit
     *
     * @return True or False
     */
    public boolean isExit() {
        return getType() == CellType.EXIT;
    }

    /**
     * Check if cell is default
     *
     * @return True or False
     */
    public boolean isDefault() {
        return getType() == CellType.DEFAULT;
    }

    /**
     * Get item within cell
     *
     * @return Item
     */
    public Item getItem(){
        return item;
    }

    /**
     * Check if cell has item within
     *
     * @return True or False
     */
    public boolean hasItem(){
        return item != null;
    }

    /**
     * Check if player is on cell
     *
     * @return True or False
     */
    public boolean hasPlayer(){
        return player;
    }

}
