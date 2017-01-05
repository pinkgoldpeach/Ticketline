package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.dto.LocationDto;

import java.util.List;

/**
 * Methods for LocationDto
 */
public interface LocationService {


    /**
     * Gets all locations
     *
     * @param page Page Number
     * @return all locations as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getSpecificPage(int page) throws ServiceException;


    /**
     * Gets all locations by given name
     *
     * @param name should be a non-null valid name of locations
     * @return all locations with given name as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByName(String name) throws ServiceException;

    /**
     * Gets all locations by given duration
     *
     * @param street should be a non-null valid street of locations
     * @return all locations with given street as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByStreet(String street) throws ServiceException;

    /**
     * Gets all locations by given city
     *
     * @param city should be a non-null valid city of locations
     * @return all locations with given city as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByCity(String city) throws ServiceException;

    /**
     * Gets all locations by land
     *
     * @param land should be a non-null valid land of locations
     * @return all locations with given land as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByLand(String land) throws ServiceException;

    /**
     * Gets all locations by postal
     *
     * @param postal should be a non-null valid postal of locations
     * @return all locations with given postal as list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByPostal(String postal) throws ServiceException;

    /**
     * Gets all locations by rooms
     *
     * @param roomName should be a non-null valid name of rooms
     * @return all locations with given rooms
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<LocationDto> getLocationsByRoom(String roomName) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;
}
