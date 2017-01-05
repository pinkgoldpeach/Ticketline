package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 14/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface ShowService {

    /**
     * Returns a collection of all shows.
     * @return list of all found shows
     * @throws ServiceException the service exception
     */
    List<Show> getAllShows() throws ServiceException;

    /**
     * find all shows for a specific page
     * @param pageNumber the pagenumber of the shows
     * @param entriesPerPage the max number of entries on the page and in the return list
     * @return a list with shows
     * @throws ServiceException the service exception
     */
    List<Show> getPageOfShows(Integer pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * find all shows for a specific page that are not canceled
     * @param pageNumber the pagenumber of the shows
     * @return a list with shows
     * @throws ServiceException the service exception
     */
    List<Show> getPageOfNotCanceledShows(Integer pageNumber) throws ServiceException;


    /**
     * Returns a collection of all shows found on that specific date.
     * @param show the show with the given date
     * @return list of all found shows
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByDateAndTime(Show show) throws ServiceException;

    /**
     * Returns a collection of all shows found after a given date.
     *
     * @param show the show with the given date
     * @return list of all found shows
     * @throws ServiceException the service exception
     */

    List<Show> getShowsByDateFrom(Show show) throws ServiceException;

    /**
     * gets shows that take place between two dates
     * @param min the starting date  of the range
     * @param max the ending date of the range
     * @return list with all shows between those dates
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByDateRange(Date min, Date max) throws ServiceException;

    /**
     * gets all shows which have a row with a specific price and are taking place in the future
     * @param row the row with a specific price
     * @return a list of all shows with that price
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByRowPrice(Row row) throws ServiceException;

    /**
     * gets all shows which have an area with a specific price and are taking place in the future
     * @param area an area with a specific price
     * @return a list of all shows with that price
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByAreaPrice(Area area) throws ServiceException;

    /**
     * gets shows that take place in a specific location at some time in the future
     * @param location the location the shows take place in
     * @return a list of all shows that take place in that location
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByLocation(Location location) throws ServiceException;

    /**
     *  gets shows by performance that are taking place in the future
     * @param performance the performance you want the shows for
     * @return all shows for that performance
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByPerformance(Performance performance) throws ServiceException;

    /**
     * gets shows that take place in a specific room at some time in the future
     * @param room the room the shows take place in
     * @return a list of all shows that take place in that room
     * @throws ServiceException the service exception
     */
    List<Show> getShowsByRoom(Room room) throws ServiceException;

    /**
     * the overall top ten events of the last month
     * @return a hashmap of the most sold shows
     * @throws ServiceException the service exception
     */
    HashMap<Show, Long> getTopTenShows() throws ServiceException;

    /**
     * the top ten events of a specific category of the last month from today till 30 days ago
     * @param performanceType the performancetype you want the top ten events for
     * @return a hashmap of the most sold shows that belong to that performancetype
     * @throws ServiceException the service exception
     */
    HashMap<Show, Long> getTopTenShowsByPerformanceType(PerformanceType performanceType) throws ServiceException;


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
