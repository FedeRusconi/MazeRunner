package mazerunner.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import mazerunner.engine.Cell;
import mazerunner.engine.GameEngine;
import mazerunner.engine.Maze;

/**
 *  Class that represents the GUI Maze
 *  Maze Pane extends GridPane and it is made of StackPanes (the cells)
 *
 * @author Federico Rusconi
 *
 */
public class MazePane extends GridPane {

    /**
     * Create new GUI maze
     *
     * @param engine The game engine instance
     */
    public MazePane(GameEngine engine){
        StackPane cellPane;
        Maze maze = engine.getMaze();
        //Loop to create cells
        for (int x = 0; x < maze.getSize(); x++){
            for(int y = 0; y < maze.getSize(); y++){
                //New GUI cell
                cellPane = new StackPane();
                //Border
                cellPane.setStyle(
                        "-fx-border-color: white"
                );
                //Set size
                cellPane.minWidthProperty().bind(widthProperty().divide(maze.getSize()));
                cellPane.minHeightProperty().bind(heightProperty().divide(maze.getSize()));
                //Find Cell
                Cell cell = maze.getCell(x, y);
                //New image view and set size
                ImageView imgView = new ImageView();
                imgView.fitWidthProperty().bind(cellPane.minWidthProperty().subtract(10));
                imgView.fitHeightProperty().bind(cellPane.minHeightProperty().subtract(10));

                if(cell.hasPlayer()){
                    imgView.setImage(new Image("player_icon.png"));
                    cellPane.getChildren().add(imgView);
                } else if(cell.isEntrance()){
                    imgView.setImage(new Image("entrance_icon.png"));
                    cellPane.getChildren().add(imgView);
                } else if(cell.isExit()){
                    imgView.setImage(new Image("exit_icon.png"));
                    cellPane.getChildren().add(imgView);
                } else if(cell.hasItem()){
                    imgView.setImage(new Image(cell.getItem().getImgName()));
                    cellPane.getChildren().add(imgView);
                }

                add(cellPane, y, x);
            }
        }
    }

}
