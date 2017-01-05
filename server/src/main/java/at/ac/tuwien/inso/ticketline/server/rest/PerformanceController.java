package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */

@Api(value = "performance", description = "Performance REST service")
@RestController
@RequestMapping(value = "/performance")

public class PerformanceController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private PerformanceService performanceService;

    /**
     * Gets all performance items.
     *
     * @return list of all performance items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all performances", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertPerformance(performanceService.getAllPerformances(), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all performance of a page", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getPerformancesPage(@ApiParam(name = "pagenumber", value = "Number of performance page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getPerformancesPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertPerformance(performanceService.getPageOfPerformances(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }


    /**
     * Gets the performance item by name.
     *
     * @param name the name of the performance
     * @return list of performance items by name
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown when the name isn't valid
     */
    @ApiOperation(value = "Gets performances by name", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getPerformanceByName", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByName(@RequestParam("name") String name) throws ServiceException, ValidationException {
        LOGGER.info("getPerformanceByName() called");
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name is empty");
        }
        name = name.trim();
        inputValidation.checkLettersNoSpecial(name);

        Performance performance = new Performance();
        performance.setName(name);
        return EntityToDto.convertPerformance(performanceService.getPerformanceByName(performance), new ArrayList<Class<?>>());
    }

    /**
     * Gets the performance item by duration +/- 30 min.
     *
     * @param duration the duration of the performance
     * @return list of performance items by duration
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown when the duration isn't valid
     */
    @ApiOperation(value = "Gets performances by duration", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getPerformanceByDuration", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByDuration(@RequestParam("duration") Integer duration) throws ServiceException, ValidationException {
        LOGGER.info("getPerformanceByDuration() called");
        inputValidation.checkDuration(duration);

        Performance performance = new Performance();
        performance.setDuration(duration);
        return EntityToDto.convertPerformance(performanceService.getPerformanceByDuration(performance), new ArrayList<Class<?>>());
    }

    /**
     * Gets the performance item by description.
     *
     * @param description the description of the performance
     * @return list of performance items by description
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the description isn't valid
     */
    @ApiOperation(value = "Gets performances by description", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getPerformanceByDescription", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByDescription(@RequestParam("description") String description) throws ServiceException, ValidationException {
        LOGGER.info("getPerformanceByDescription() called");
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Description is null or empty");
        }
        description = description.trim();
        inputValidation.checkLettersNoSpecial(description);

        Performance performance = new Performance();
        performance.setDescription(description);
        return EntityToDto.convertPerformance(performanceService.getPerformanceByDescription(performance), new ArrayList<Class<?>>());
    }

    /**
     * Gets the performance item by performance type.
     *
     * @param performanceType the type of performance
     * @return list of performance items by performance type
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the performance type isn't valid
     */

    @ApiOperation(value = "Gets performances by type of performance", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByPerformanceType", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByPerformanceType(@RequestParam("performanceTypeDto") @Valid PerformanceTypeDto performanceType) throws ServiceException, ValidationException {
        LOGGER.info("getByPerformanceType() called");
        if (performanceType == null) {
            throw new ValidationException("Performance Type is null");
        }
        Performance performance = new Performance();
        performance.setPerformanceType(DtoToEntity.convert(performanceType));
        return EntityToDto.convertPerformance(performanceService.getPerformanceByPerformanceType(performance), new ArrayList<Class<?>>());
    }


    /**
     * Gets the performance item by artist name.
     *
     * @param firstname the fristname of the artist
     * @param lastname  the lastname of the artist
     * @return list of performance items by artist name
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if one of the names isn't valid
     */
    @ApiOperation(value = "Gets performances by artist name", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByArtistName", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByArtistName(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws ServiceException, ValidationException {
        LOGGER.info("getByArtistName() called");


        if ((firstname == null || firstname.trim().isEmpty()) && (lastname == null || lastname.trim().isEmpty())) {
            throw new ValidationException("Firstname and Lastname invalid");
        }
        firstname = firstname.trim();
        lastname = lastname.trim();
        inputValidation.checkLettersNoSpecial(firstname);
        inputValidation.checkLettersNoSpecial(lastname);
        Artist artist = new Artist();
        artist.setFirstname(firstname);
        artist.setLastname(lastname);
        return EntityToDto.convertPerformance(performanceService.getPerformanceByArtist(artist), new ArrayList<Class<?>>());
    }

    /**
     * Gets the performance item by artist.
     *
     * @param artistID the id of the artist
     * @return list of performance items by artist
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if one of the artists id isn't valid
     */
    @ApiOperation(value = "Gets performances by artist", response = PerformanceDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByArtist", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PerformanceDto> getByArtist(@RequestParam("artistID") Integer artistID) throws ServiceException, ValidationException {
        LOGGER.info("getByArtist() called");

        inputValidation.checkId(artistID);
        Artist artist = new Artist();
        artist.setId(artistID);
        return EntityToDto.convertPerformance(performanceService.getPerformanceByArtist(artist), new ArrayList<Class<?>>());
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
        return EntityToDto.convertPageCount(performanceService.getPageCount());
    }
}
