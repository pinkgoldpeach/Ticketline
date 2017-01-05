package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public interface AreaDaoCustom {


    /**
     * finds areas of a room
     * @param room, of which you want the areas
     * @return all areas of that room
     */
    List<Area> findByRoom(Room room);

    /**
     * find areas for a show
     * @param show of which you want the areas
     * @return all areas of that show
     */
     List<Area> findByShow(Show show);

    /**
     * the number of available tickets for a specific show and area
     * @param show of which you want to know how many available tickets there are
     * @param area the are in the show
     * @return available tickets for a specific area in a show
     */
    Long getNumberOfAvailableTickets(Show show, Area area);

}
