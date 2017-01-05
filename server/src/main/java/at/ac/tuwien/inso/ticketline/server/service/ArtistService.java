package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Carla
 * @author Alexander Poschenreithner (1328924)
 */
public interface ArtistService {

    /**
     * Finds all artist ordered by lastname, pagination is possible
     * @param pageNumber you want artists for
     * @param entriesPerPage max number of entries in the return list
     * @return the list of all found artists on that page
     * @throws ServiceException on error
     */

    List<Artist> getPageOfArtists(Integer pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * finds artists by full name
     * @param artist with full name you want to find
     * @return a list of artists with that name
     * @throws ServiceException on error
     */
    List<Artist> getArtistByName(Artist artist) throws ServiceException;

    /**
     * finds artists by firstname
     * @param artist with firstname
     * @return a list of artists with that firstname
     * @throws ServiceException on error
     */
    List<Artist> getArtistByFirstname(Artist artist) throws ServiceException;

    /**
     * finds artists by lastname
     * @param artist with lastname
     * @return a list of artists with that lastname
     * @throws ServiceException on error
     */
    List<Artist> getArtistByLastname(Artist artist) throws ServiceException;

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
