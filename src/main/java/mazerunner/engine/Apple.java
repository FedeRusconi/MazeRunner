package mazerunner.engine;


/**
 *  Class that represents an Apple
 *
 * @author Federico Rusconi
 *
 */
public class Apple extends Item implements Consumable {

    /**
     * Instantiate Apple class,
     * Set image name
     */
    public Apple(){
        imgName = "apple_icon.png";
    }

    /** Add 3 to player's stamina
     *
     * @param player Current player
     *
     * @return The response to the action of type ActionResponse.
     */
    @Override
    public ActionResponse doAction(Player player){
        player.staminaUp(3);
        return new ActionResponse(1, "Apple collected - +3 Stamina");
    }

}
