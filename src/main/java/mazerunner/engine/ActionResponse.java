package mazerunner.engine;

import java.util.ArrayList;

/**
 * This class is used to send responses to any action within the game.
 *
 * @author Federico Rusconi - 1131516
 */
public class ActionResponse {

    private ArrayList<String> messages;
    private int status;
    private boolean gameEnd;

    /**
     * Create new Action Response with default status of 0
     */
    public ActionResponse(){
        this.status = 0;
    }

    /**
     * Create new Action Response
     *
     * @param status Status of operation. 1 Successful - 0 Failed
     */
    public ActionResponse(int status){
        this.status = status;
        messages = new ArrayList<>();
    }

    /**
     * Create new Action Response with message
     *
     * @param status Status of operation. 1 Successful - 0 Failed
     * @param msg Text message to include within response.
     */
    public ActionResponse(int status, String msg){
        this.status = status;
        messages = new ArrayList<>();
        messages.add(msg);
    }

    /**
     * Create new Action Response with message and cause game to end
     *
     * @param status Status of operation. 1 Successful - 0 Failed
     * @param msg Text message to include within response.
     * @param gameEnd This is used to cause the game to end
     */
    public ActionResponse(int status, String msg, boolean gameEnd){
        this.status = status;
        messages = new ArrayList<>();
        messages.add(msg);
        this.gameEnd = gameEnd;
    }

    /**
     * Add message at the end of list of messages
     *
     * @param msg The text message to add.
     */
    public void addMsg(String msg){
        messages.add(msg);
    }

    /**
     * Add message at specified index of list of messages
     *
     * @param index The location where message will be added
     * @param msg The text message to add.
     */
    public void addMsg(int index, String msg){
        messages.add(index, msg);
    }

    /**
     * Set gameEnd attribute to cause the game to end.
     *
     * @param gameEnd set True if game must end
     */
    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    /** Get action status
     *
     * @return status
     */
    public int getStatus(){
        return status;
    }

    /** Get the list of messages
     *
     * @return the list of messages
     */
    public ArrayList<String> getMessages() {
        return messages;
    }

    /**
     * Print the list of messages.
     * One message per row.
     */
    public void printMessages(){
        for (String msg : messages){
            System.out.println(msg);
        }
    }

    /**
     * Check if game should end
     *
     * @return True if game should end
     */
    public boolean gameHasEnded() {
        return gameEnd;
    }

}
