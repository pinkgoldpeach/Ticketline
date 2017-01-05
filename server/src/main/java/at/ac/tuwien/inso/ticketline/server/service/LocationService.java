package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface LocationService {

    /**
     * gets a list of locations for specific page
     * @param pageNumber the pagenumber of locations
     * @param entriesPerPage max entries of locations in the return list and on page
     * @return list of locations
     * @throws ServiceException on error
     */
    List<Location> getPageOfLocations(Integer pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * gets a list of locations by location name
     * @param location with the name of the location
     * @return a list of locations with that name
     * @throws ServiceException on error
     */
    List<Location> getLocationsByName(Location location) throws ServiceException;

    /**
     * gets a list of locations a show takes place in
     * @param show the show that takes place
     * @return a list of locations the show takes place in
     * @throws ServiceException on error
     */
    List<Location> getLocationsByShow(Show show) throws ServiceException;

    /**
     * get all locations on a specific street
     * @param address the street must be set - the street the location is on
     * @return a list of all locations found on that street.
     * @throws ServiceException on error
     */
    List<Location> getLocationsByStreet(Address address) throws ServiceException;

    /**
     * get all locations in a specific country
     * @param address the country must be set - the country the location is in
     * @return a list of all locations found in that country
     * @throws ServiceException on error
     */
    List<Location> getLocationsByCountry(Address address) throws ServiceException;


    /**
     * find locations by postal code
     * @param address the postal code must be set - the postal code of the location
     * @return a list of all locations found for that postal code
     * @throws ServiceException on error
     */
    List<Location> getLocationsByPostalCode(Address address) throws ServiceException;

    /**
     * get all locations in a specific city
     * @param address the city must be set - the city the location is in
     * @return a list of all locations found in that city
     * @throws ServiceException on error
     */
    List<Location> getLocationsByCity(Address address) throws ServiceException;


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
