package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 16/05/2016.
 */
public interface SeatService {

    /**
     * gets seats of a row
     * @param row the row you want the seats for
     * @return a list of all seats in that row
     * @throws ServiceException on error
     */
    List<Seat> getSeatsByRow(Row row) throws ServiceException;

    /**
     * gets seats of a room
     * @param room the room you want the seats for
     * @return a list of all seats in that room
     * @throws ServiceException on error
     */
    List<Seat> getSeatsByRoom(Room room) throws ServiceException;

}
