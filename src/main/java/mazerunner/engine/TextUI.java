package mazerunner.engine;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  Class that is used to display a text ui to play the game
 *
 * @author Federico Rusconi
 *
 */
public class TextUI {

    /**
     * Play a text-based version of the game
     */
    public static void playTextGame() {
        System.out.println("Welcome to MazeRunner");
        //Create a scanner object
        Scanner scanner = new Scanner(System.in);
        //Store difficulty entered by user
        int difficulty;
        //Keep the loop active until a valid input is entered
        while (true) {
            try {
                //Ask for difficulty
                System.out.println("Please enter the difficulty (0-10) ");
                difficulty = scanner.nextInt();
                //Continue only if difficulty is between 0 and 10
                if(difficulty >= 0 && difficulty <= 10){
                    break;
                } else {
                    System.out.println("Please try again. Enter a number between 0 and 10.");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e){
                System.out.println("Please try again. An integer is required.");
                scanner.nextLine();
            }
        }
        //New game engine
        GameEngine engine = new GameEngine(10, difficulty);
        System.out.println("P = Player - C = Coin - T = Trap - A = Apple - O = Out(Exit) - I = In(Entrance)");
        //Print maze
        printMaze(engine.getMaze());
        //Store user command
        int command;
        //Store action response
        ActionResponse actionResponse;
        while (true) {
            try {
                System.out.print("Enter next move (8:Up 6:Right 2:Down 4:Left) ");
                command = scanner.nextInt();
                System.out.println(command);
                //Do action
                switch (command) {
                    case GameEngine.DIRECTION_UP:
                        actionResponse = engine.moveUp();
                        break;
                    case GameEngine.DIRECTION_RIGHT:
                        actionResponse = engine.moveRight();
                        break;
                    case GameEngine.DIRECTION_DOWN:
                        actionResponse = engine.moveDown();
                        break;
                    case GameEngine.DIRECTION_LEFT:
                        actionResponse = engine.moveLeft();
                        break;
                    default:
                        actionResponse = new ActionResponse(0, "Command does not exist, please try again.");
                        break;
                }
                //Print maze
                System.out.println("P = Player - C = Coin - T = Trap - A = Apple - O = Out(Exit) - I = In(Entrance)");
                printMaze(engine.getMaze());
                //Print results of action
                actionResponse.printMessages();
                //End game or display stats
                if (actionResponse.gameHasEnded()) {
                    System.exit(0);
                } else {
                    //Print player stamina
                    System.out.println("Stamina: " + engine.getPlayer().getStamina());
                    //Print player coins
                    System.out.println("Coins: " + engine.getPlayer().getCoins());
                    //Print player moves number
                    System.out.println("Moves: " + engine.getPlayer().getMoves());
                }
            } catch (InputMismatchException e){
                System.out.println("Please try again. An integer is required.");
                scanner.nextLine();
            }

        }
    }

    /**
     * Print the maze to system output
     *
     * @param maze The Maze instance
     */
    public static void printMaze(Maze maze){
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                Cell currentCell = maze.getCell(x, y);
                if(currentCell.hasPlayer()){
                    System.out.print(" P ");
                } else if(currentCell.isEntrance()){
                    System.out.print(" I ");
                } else if(currentCell.isExit()){
                    System.out.print(" O ");
                } else {
                    if(currentCell.hasItem()){
                        char initial = currentCell.getItem().getClass().getSimpleName().toUpperCase().charAt(0);
                        System.out.printf(" %C ", initial);
                    } else {
                        System.out.print(" _ ");
                    }
                }
            }
            System.out.print("\n");
        }
    }

}
