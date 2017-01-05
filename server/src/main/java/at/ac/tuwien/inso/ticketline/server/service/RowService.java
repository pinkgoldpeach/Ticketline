package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface RowService {


    /**
     * Returns a collection of all rows by page
     *
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all rows.
     * @throws ServiceException the service exception
     */
    List<Row> getPageOfRows(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * gets all rows of a room with seatingchoice
     * @param room the room you want the rows for
     * @return a list of all rows in that room
     * @throws ServiceException the service exception
     */
    List<Row> getRowsByRoom(Room room) throws ServiceException;

    /**
     * gets all rows for a show - the show takes place in a room with seatingchoice
     * @param show the show you want the rows for
     * @return list of rows for a specific show
     * @throws ServiceException the service exception
     */
    List<Row> getRowsByShow(Show show) throws ServiceException;

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
