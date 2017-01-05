package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public interface LocationDaoCustom {


    /**
     * find all locations a show takes place in
     * @param show you want the location(s) for
     * @return a list of locations found for a specific show
     */
    List<Location> findByShow(Show show);

    /**
     * find all locations on a specific street
     * @param address the street must be set - the street the location is on
     * @return a list of all locations found on that street.
     */
    List<Location> findByStreet(Address address);

    /**
     * find all locations in a specific city
     * @param address the city must be set - the city the location is in
     * @return a list of all locations found in that city
     */
    List<Location> findByCity(Address address);

    /**
     * find all locations in a specific country
     * @param address the country must be set - the country the location is in
     * @return a list of all locations found in that country
     */
    List<Location> findByCountry(Address address);

    /**
     * find locations by postal code
     * @param address the postal code must be set - the postal code of the location
     * @return a list of all locations found for that postal code
     */
    List<Location> findByPostalCode(Address address);
}
