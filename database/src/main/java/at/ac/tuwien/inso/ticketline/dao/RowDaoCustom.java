package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Show;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public interface RowDaoCustom {

    /**
     * find all rows of a room with seatingchoice
     * @param room the room you want the rows for
     * @return a list of all rows in that room
     */
    List<Row> findByRoom(Room room);

    /**
     * finds all rows for a show - the show takes place in a room with seatingchoice
     * @param show the show you want the rows for
     * @return list of rows for a specific show
     */
    List<Row> findByShow(Show show);
}
