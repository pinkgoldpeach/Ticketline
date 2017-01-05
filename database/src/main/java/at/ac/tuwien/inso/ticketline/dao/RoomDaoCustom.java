package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;

/**
 * Created by Carla on 25/06/2016.
 */
public interface RoomDaoCustom {

    /**
     * retrieves the price range of a specific room
     * @param room the room
     * @return a string with price range - first min then max
     */
    String findPriceRange(Room room);
}
