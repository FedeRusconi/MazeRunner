package mazerunner.engine;

import java.io.Serializable;
import java.util.Random;

/**
 *  Class that represents the Maze
 *
 * @author Federico Rusconi
 *
 */
public class Maze implements Serializable {

    private Random rand;

    private Cell[][] cells;

    /**
     * Generate a new Maze
     *
     * @param size Size of the maze
     */
    public Maze(int size){
        rand = new Random();
        cells = new Cell[size][size];
        generateMazeGrid(size);
    }

    /**
     * Generate the maze cells grid
     *
     * @param size The width and height.
     */
    private void generateMazeGrid(int size){
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size ; y++) {
                Cell cell = new Cell();
                //Set entrance on bottom left of grid
                if(x == getMaxIndex() && y == 0){
                    cell.setType(CellType.ENTRANCE);
                }
                addCell(x, y, cell);
            }
        }
    }

    /**
     * Place the exit of the maze randomly in maze's perimeter.
     * If cell picked is entrance, repeat process
     */
    public void placeExitCell(){
        //Repeat if random cell is already entrance
        while(true){
            // Generate random number up to 4 times the size (used to place exit in perimeter)
            int exit = rand.nextInt(4 * getSize());
            //Place exit
            if(exit < getSize()){
                //Top
                cells[0][exit].setType(CellType.EXIT);
                break;
            } else if(exit < 2 * getSize()){
                //Right
                cells[exit - getSize()][getMaxIndex()].setType(CellType.EXIT);
                break;
            } else if(exit < 3 * getSize()){
                //Bottom
                Cell exitCell = cells[getMaxIndex()][exit - (2 * getSize())];
                //Ensure cell is not entrance already
                if(exitCell.isDefault()){
                    exitCell.setType(CellType.EXIT);
                    break;
                }
            } else {
                //Left
                Cell exitCell = cells[exit - (3 * getSize())][0];
                //Ensure cell is not entrance already
                if(exitCell.isDefault()){
                    exitCell.setType(CellType.EXIT);
                    break;
                }
            }

        }
    }

    /**
     * Add new Cell to the maze
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param cell the Cell instance to add
     */
    public void addCell(int x, int y, Cell cell){
        cells[x][y] = cell;
    }

    /**
     * Get a specific Cell within the maze
     *
     * @param x X coordinate
     * @param y Y coordinate
     *
     * @return The Cell instance
     */
    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    /**
     * The size of the maze.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return cells.length;
    }

    /**
     * The maximum index within the maze.
     *
     * @return this is for both x and y.
     */
    public int getMaxIndex() {
        return cells.length - 1;
    }

}
