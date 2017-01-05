package at.ac.tuwien.inso.ticketline.dao;


import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;

import java.util.List;

/**
 * Created by Carla on 16/05/2016.
 */
public interface SeatDaoCustom {

    /**
     * find seats of a row
     * @param row the row you want the seats for
     * @return a list of all seats in that row
     */
    List<Seat> findByRow(Row row);

    /**
     * find seats of a room
     * @param room the room you want the seats for
     * @return a list of all seats in that room
     */
    List<Seat> findByRoom(Room room);

    /**
     * the price of a seat - rows have different prices
     * @param seat the seat you want the price for
     * @return the price of a specific seat
     */
    Double getPrice(Seat seat);
}
