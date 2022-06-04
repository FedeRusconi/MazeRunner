package mazerunner.engine;

/**
 *  Class that represents a Trap
 *
 * @author Federico Rusconi
 *
 */
public class Trap extends Item {

    /**
     * Instantiate Trap class
     * Set image name
     */
    public Trap(){
        imgName = "trap_icon.png";
    }

    /**
     * Player is trapped and must pay one coin.
     * If player has no coins, an ActionResponse is returned with a trigger to end the game.
     * Status is always 1 even if game ends because the move itself was successful
     *
     * @param player Current player
     *
     * @return The response to the action of type ActionResponse.
     */
    @Override
    public ActionResponse doAction(Player player) {
        if(player.getCoins() > 0){
            player.coinsDown(1);
            return new ActionResponse(1, "Trap encountered - Pay 1 coin");
        } else {
            return new ActionResponse(1, "Not enough coins to escape trap", true);
        }
    }

}
