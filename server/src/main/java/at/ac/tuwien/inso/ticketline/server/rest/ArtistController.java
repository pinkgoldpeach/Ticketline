package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.ArtistDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.ArtistService;
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

@Api(value = "artist", description = "Artist REST service")
@RestController
@RequestMapping(value = "/artist")
public class ArtistController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private ArtistService artistService;


    @ApiOperation(value = "Gets all artists of a page", response = ArtistDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ArtistDto> getArtistsPage(@ApiParam(name = "pagenumber", value = "Number of artist page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getArtistsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertArtists(artistService.getPageOfArtists(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Gets the artist item by name.
     *
     * @param firstname the firstnamme of the artist
     * @param lastname  the lastname of the artist
     * @return list of artist items by name
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown, when the inout isn't valid
     */
    @ApiOperation(value = "Gets artists by name", response = ArtistDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByName", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ArtistDto> getByName(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws ServiceException, ValidationException {
        LOGGER.info("getByName() called");
        if ((firstname == null || firstname.trim().isEmpty()) || (lastname == null || lastname.trim().isEmpty())) {
            throw new ValidationException("Firstname and Lastname invalid");
        }
        firstname = firstname.trim();
        lastname = lastname.trim();

        inputValidation.checkLettersNoSpecial(firstname);
        inputValidation.checkLettersNoSpecial(lastname);
        Artist artist = new Artist();
        artist.setFirstname(firstname);
        artist.setLastname(lastname);
        return EntityToDto.convertArtists(artistService.getArtistByName(artist), new ArrayList<Class<?>>());
    }

    /**
     * Gets the artist item by firstname.
     *
     * @param firstname the firstname of the artist
     * @return list of artist items by firstname
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown when, the firstname isn't valid
     */
    @ApiOperation(value = "Gets artists by first name", response = ArtistDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByFirstname", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ArtistDto> getByFirstname(@RequestParam("firstname") String firstname) throws ServiceException, ValidationException {
        LOGGER.info("getByFirstname() called");
        if ((firstname == null || firstname.trim().isEmpty())) {
            throw new ValidationException("Firstname invalid");
        }
        firstname = firstname.trim();

        inputValidation.checkLettersNoSpecial(firstname);
        Artist artist = new Artist();
        artist.setFirstname(firstname);
        return EntityToDto.convertArtists(artistService.getArtistByFirstname(artist), new ArrayList<Class<?>>());
    }

    /**
     * Gets the artist item by lastname.
     *
     * @param lastname the lastname of the artist
     * @return list of artist items by lastname
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown when, the lastname isn't valid
     */
    @ApiOperation(value = "Gets artists by last name", response = ArtistDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByLastname", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ArtistDto> getByLastname(@RequestParam("lastname") String lastname) throws ServiceException, ValidationException {
        LOGGER.info("getByLastname() called");
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new ValidationException("Lastname invalid");
        }
        lastname = lastname.trim();

        inputValidation.checkLettersNoSpecial(lastname);
        Artist artist = new Artist();
        artist.setLastname(lastname);
        return EntityToDto.convertArtists(artistService.getArtistByLastname(artist), new ArrayList<Class<?>>());
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
        LOGGER.info("Artist :: getPageCount() called");
        return EntityToDto.convertPageCount(artistService.getPageCount());
    }


    //--------- FOR TESTING
    public ArtistController() {
    }

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }
}
