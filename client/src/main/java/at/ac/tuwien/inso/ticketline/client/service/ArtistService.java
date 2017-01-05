package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.dto.ArtistDto;

import java.util.List;

/**
 * Service for ArstitDTO
 */
public interface ArtistService {

    /**
     * Gets all Artists
     *
     * @return all Artists as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<ArtistDto> getArtists() throws ServiceException;

    /**
     * Gets all Artists
     *
     * @param page Page Number
     * @return all Artists as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<ArtistDto> getSpecificPage(int page) throws ServiceException;

    /**
     * Gets all Artists by given firstName
     *
     * @param firstName should be a non-null valid firstName of Artists
     * @return all artists with given firstName as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<ArtistDto> getArtistsByFirstname(String firstName) throws ServiceException;

    /**
     * Gets all Artists by given lastname
     *
     * @param lastname should be a non-null string
     * @return all Artists with given lastname as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<ArtistDto> getArtistsByLastname(String lastname) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;

}
