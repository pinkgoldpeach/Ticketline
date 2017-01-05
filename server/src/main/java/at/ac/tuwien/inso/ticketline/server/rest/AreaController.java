package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.AreaDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.AreaService;
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

@Api(value = "area", description = "Area REST service")
@RestController
@RequestMapping(value = "/area")
public class AreaController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private AreaService areaService;


    @ApiOperation(value = "Gets all areas of a page", response = AreaDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<AreaDto> getAreasPage(@ApiParam(name = "pagenumber", value = "Number of performance page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getAreasPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertAreas(areaService.getPageOfAreas(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
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
        LOGGER.info("Area :: getPageCount() called");
        return EntityToDto.convertPageCount(areaService.getPageCount());
    }

    /**
     * Gets the area item by room.
     *
     * @param roomID the id of the room
     * @return list of area items by room id
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown, when the input isn't valid
     */
    @ApiOperation(value = "Gets areas by room", response = AreaDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRoom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<AreaDto> getByRoom(@RequestParam("roomID") Integer roomID) throws ServiceException, ValidationException {
        LOGGER.info("getByRoom() called");

        inputValidation.checkId(roomID);
        Room room = new Room();
        room.setId(roomID);

        return EntityToDto.convertAreas(areaService.getAreasByRoom(room), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets areas by show", response = AreaDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByShow", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<AreaDto> getByShow(@RequestParam("showID") Integer showID) throws ServiceException, ValidationException {
        LOGGER.info("getByShow() called");

        inputValidation.checkId(showID);
        Show show = new Show();
        show.setId(showID);

        return EntityToDto.convertAreas(areaService.getAreasByShow(show), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets number of available tickets", response = AreaDto.class, responseContainer = "List")
    @RequestMapping(value = "/getNumberOfAvailableTickets", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto getNumberOfAvailableTickets(@RequestParam("showID") Integer showID, @RequestParam("areaID") Integer areaID) throws ServiceException, ValidationException {
        LOGGER.info("getNumberOfAvailableTickets() called");

        inputValidation.checkId(showID);
        inputValidation.checkId(areaID);

        Show show = new Show();
        show.setId(showID);
        Area area = new Area();
        area.setId(areaID);

        Long freetickets = areaService.getNumberOfAvailableTickets(show, area);
        MessageDto returnmessage = new MessageDto();
        returnmessage.setText(""+freetickets);
        return returnmessage;
    }


    //--------- FOR TESTING
    public AreaController() {
    }

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }
}
