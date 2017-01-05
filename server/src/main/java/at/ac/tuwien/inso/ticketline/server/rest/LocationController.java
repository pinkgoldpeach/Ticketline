package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.LocationDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Carla on 15/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "location", description = "Location REST service")
@RestController
@RequestMapping(value = "/location")
public class LocationController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private LocationService locationService;

    @ApiOperation(value = "Gets all locations by name", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByName", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByName(@RequestParam("name") String name) throws ServiceException, ValidationException {
        LOGGER.info("getByName() called");
        inputValidation.checkLettersNoSpecial(name);
        Location location = new Location();
        location.setName(name);

        return EntityToDto.convertLocations(locationService.getLocationsByName(location), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all locations of a page", response = LocationDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getLocationsPage(@ApiParam(name = "pagenumber", value = "Number of location page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getLocationsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertLocations(locationService.getPageOfLocations(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Gets all location items by show.
     *
     * @param showID the id of the show
     * @return list of all location items
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown when the show id isn't valid
     */
    @ApiOperation(value = "Gets all locations by show", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByShow", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByShow(@RequestParam("showID") Integer showID) throws ServiceException, ValidationException {
        LOGGER.info("getByShow() called");
        inputValidation.checkId(showID);
        Show show = new Show();
        show.setId(showID);

        return EntityToDto.convertLocations(locationService.getLocationsByShow(show), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all locations by street name", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByStreet", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByStreet(@RequestParam("streetName") String streetName) throws ServiceException, ValidationException {
        LOGGER.info("getByStreet() called");
        inputValidation.checkLettersNoSpecial(streetName);
        Address address = new Address();
        address.setStreet(streetName);

        return EntityToDto.convertLocations(locationService.getLocationsByStreet(address), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all locations by city", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByCity", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByCity(@RequestParam("city") String city) throws ServiceException, ValidationException {
        LOGGER.info("getByCity() called");
        inputValidation.checkLettersNoSpecial(city);
        Address address = new Address();
        address.setCity(city);

        return EntityToDto.convertLocations(locationService.getLocationsByCity(address), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all locations by country", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByCountry", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByCountry(@RequestParam("country") String country) throws ServiceException, ValidationException {
        LOGGER.info("getByCountry() called");
        inputValidation.checkLettersNoSpecial(country);
        Address address = new Address();
        address.setCountry(country);

        return EntityToDto.convertLocations(locationService.getLocationsByCountry(address), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all locations by postalCode", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByPostalCode", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<LocationDto> getByPostalCode(@RequestParam("postalCode") String postalCode) throws ServiceException, ValidationException {
        LOGGER.info("getByPostalCode() called");
        inputValidation.checkLettersNoSpecial(postalCode);
        Address address = new Address();
        address.setPostalCode(postalCode);

        return EntityToDto.convertLocations(locationService.getLocationsByPostalCode(address), new ArrayList<Class<?>>());
    }

    /**
     * Returns the count of available pages
     *
     * @return number of pages
     * @throws ServiceException the service exception
     */
    @Override
    @ApiOperation(value = "Returns the count of available pages", response = PageCountDto.class)
    @RequestMapping(value = "/pagecount", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PageCountDto getPageCount() throws ServiceException {
        LOGGER.info("Location :: getPageCount() called");
        return EntityToDto.convertPageCount(locationService.getPageCount());
    }


}
