package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.RowDto;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.RowService;
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
@Api(value = "row", description = "Row REST service")
@RestController
@RequestMapping(value = "/row")
public class RowController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RowController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private RowService rowService;

    @ApiOperation(value = "Gets all rows of a page", response = RowDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<RowDto> getRowsPage(@ApiParam(name = "pagenumber", value = "Number of performance page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getRowsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertRows(rowService.getPageOfRows(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Gets the row item by room.
     *
     * @param roomID the id of the room
     * @return list of row items by room id
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the id of the room is invalid
     */
    @ApiOperation(value = "Gets rows by room", response = RowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRoom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<RowDto> getByRoom(@RequestParam("roomID") Integer roomID) throws ServiceException, ValidationException {
        LOGGER.info("getByRoom() called");

        inputValidation.checkId(roomID);
        Room room = new Room();
        room.setId(roomID);

        return EntityToDto.convertRows(rowService.getRowsByRoom(room), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets rows by show", response = RowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByShow", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<RowDto> getByShow(@RequestParam("showID") Integer showID) throws ServiceException, ValidationException {
        LOGGER.info("getByShow() called");

        inputValidation.checkId(showID);
        Show show = new Show();
        show.setId(showID);

        return EntityToDto.convertRows(rowService.getRowsByShow(show), new ArrayList<Class<?>>());
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
        LOGGER.info("Row :: getPageCount() called");
        return EntityToDto.convertPageCount(rowService.getPageCount());
    }
}
