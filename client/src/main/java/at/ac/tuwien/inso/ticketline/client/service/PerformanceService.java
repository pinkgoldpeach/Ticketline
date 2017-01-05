package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;

import java.util.List;


public interface PerformanceService {

    /**
     * Gets all performances
     *
     * @return all performances as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformances() throws ServiceException;

    /**
     * Gets all performances
     *
     * @param page page number
     * @return all performances as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getSpecificPage(int page) throws ServiceException;

    /**
     * Gets all performances by given name
     *
     * @param name should be a non-null valid name of performance
     * @return all performances with given name as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformancesByName(String name) throws ServiceException;

    /**
     * Gets all performances by given duration
     *
     * @param duration should be a positiv integer
     * @return all performances with given duration as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformancesByDuration(Integer duration) throws ServiceException;

    /**
     * Gets all performances by given type
     *
     * @param type should be a non-null valid type of performance
     * @return all performances with given type as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformancesByType(PerformanceTypeDto type) throws ServiceException;

    /**
     * Gets all performances by given ArtistID
     *
     * @param ArtistID should be a positive and non-null valid ID
     * @return all performances with given ArtistID as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformancesByArtistID(Integer ArtistID) throws ServiceException;

    /**
     * Gets all performances
     *
     * @param description should be a non-null valid description of performance
     * @return all performances with given description as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation on server failed
     */
    List<PerformanceDto> getPerformancesByDescription(String description) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;

}
