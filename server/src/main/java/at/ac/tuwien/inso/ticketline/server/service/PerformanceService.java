package at.ac.tuwien.inso.ticketline.server.service;


import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 * Service for {@link at.ac.tuwien.inso.ticketline.model.Performance}
 *
 * @author Alexander Poschenreithner (1328924)
 */

public interface PerformanceService {

    /**
     * Returns a collection of all performances.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    List<Performance> getAllPerformances() throws ServiceException;

    /**
     * Returns a collection of all performances ordered by name.
     *
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all performances.
     * @throws ServiceException the service exception
     */
    List<Performance> getPageOfPerformances(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * Returns a collection of all performances found with that name.
     *
     * @param performance the performance with the name you want to search for
     * @return a collection of all performances found with that name.
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByName(Performance performance) throws ServiceException;

    /**
     * Returns a collection of all performances found with a specific duration +/- 30 minutes.
     *
     * @param performance with the duration you want to search for
     * @return a collection of all performances found with a specific duration +/- 30 minutes.
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByDuration(Performance performance) throws ServiceException;

    /**
     * Returns a collection of all performances found by description
     *
     * @param performance the performance with the description you want to search for
     * @return a collection of all performances found by description
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByDescription(Performance performance) throws ServiceException;

    /**
     * Returns a collection of all performances found by type of performance
     *
     * @param performance the performance with the parameter of the performance type you want to search for
     * @return a collection of all performances found by type of performance
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByPerformanceType(Performance performance) throws ServiceException;

    /**
     * Returns a collection of all performances found by artist name
     *
     * @param artist we are looking for with a name
     * @return a collection of all performances found by artist name
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByArtistName(Artist artist) throws ServiceException;

    /**
     * Returns a collection of all performances found by artist
     *
     * @param artist we are looking for with an id
     * @return a collection of all performances found by artist
     * @throws ServiceException the service exception
     */
    List<Performance> getPerformanceByArtist(Artist artist) throws ServiceException;

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
