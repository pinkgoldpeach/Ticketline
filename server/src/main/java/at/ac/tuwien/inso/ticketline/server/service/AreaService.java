package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Carla
 * @author Alexander Poschenreithner (1328924)
 */
public interface AreaService {

    /**
     * Returns a collection of all areas by page
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all areas.
     * @throws ServiceException the service exception
     */
    List<Area> getPageOfAreas(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * returns a number of available tickets
     * @param show the show the tickets are for
     * @param area the area you want to know the number of available tickets of
     * @return number of available tickets
     * @throws ServiceException the service exception
     */

    Long getNumberOfAvailableTickets(Show show, Area area) throws ServiceException;

    /**
     * returns a collection of all areas by room
     * @param room the room for which you want to get the areas
     * @return a collection of all areas by room
     * @throws ServiceException the service exception
     */
    List<Area> getAreasByRoom(Room room) throws ServiceException;

    /**
     * find areas for a show
     * @param show of which you want the areas
     * @return all areas of that show
     * @throws ServiceException on error
     */
    List<Area> getAreasByShow(Show show) throws ServiceException;

    /**
     * Returns the number of records
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;
}
