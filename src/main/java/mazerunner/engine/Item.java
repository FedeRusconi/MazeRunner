package mazerunner.engine;

import java.io.Serializable;

/**
 *  Abstract Class that represents a generic Item
 *
 * @author Federico Rusconi
 *
 */
public abstract class Item implements Serializable {

    protected String imgName;

    /**
     * Perform item's action
     *
     * @param player Current player
     *
     * @return The response to the action of type ActionResponse.
     */
    public abstract ActionResponse doAction(Player player);

    /**
     * Get Item's image name
     *
     * @return The name of the image used as an icon
     */
    public String getImgName(){
        return imgName;
    }

}
