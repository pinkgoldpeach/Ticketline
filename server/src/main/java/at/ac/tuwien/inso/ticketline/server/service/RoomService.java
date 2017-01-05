package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface RoomService {

    /**
     * Returns a collection of all rooms
     *
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all rooms.
     * @throws ServiceException the service exception
     */
    List<Room> getPageOfRooms(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * retrieves the price range of a specific room
     * @param room the room
     * @return a string with price range - first min then max
     * @throws ServiceException the service exception
     */
    String getPriceRangeOfRoom(Room room) throws ServiceException;

    /**
     * Returns the number of records
     *
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     *
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;
}
