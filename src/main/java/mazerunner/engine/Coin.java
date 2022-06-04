package mazerunner.engine;

/**
 *  Class that represents a coin
 *
 * @author Federico Rusconi
 *
 */
public class Coin extends Item implements Consumable {

    /**
     * Instantiate Coin class
     * Set image name
     */
    public Coin(){
        imgName = "coin_icon.png";
    }

    /** Add 1 to player's coin count
     *
     * @param player Current player
     *
     * @return The response to the action of type ActionResponse.
     */
    @Override
    public ActionResponse doAction(Player player){
        player.coinsUp(1);
        return new ActionResponse(1, "Coin collected");
    }

}
