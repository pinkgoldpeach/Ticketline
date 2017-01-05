package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
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

@Api(value = "room", description = "Room REST service")
@RestController
@RequestMapping(value = "/room")
public class RoomController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private RoomService roomService;

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
        LOGGER.info("Room :: getPageCount() called");
        return EntityToDto.convertPageCount(roomService.getPageCount());
    }

    @ApiOperation(value = "Gets all rooms of a page", response = RoomDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pageNumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<RoomDto> getRoomsPage(@ApiParam(name = "pageNumber", value = "Number of room page") @PathVariable("pageNumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getRoomsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertRooms(roomService.getPageOfRooms(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets price range of a room", response = MessageDto.class)
    @RequestMapping(value = "/getPriceRangeOfRoom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto getPriceRangeOfRoom(@RequestParam("roomID") Integer roomID) throws ServiceException, ValidationException {
        LOGGER.info("getPriceRangeOfRoom() called");
        inputValidation.checkId(roomID);
        Room room = new Room();
        room.setId(roomID);
        String priceRange = roomService.getPriceRangeOfRoom(room);
        MessageDto messageDto = new MessageDto();
        messageDto.setType(MessageType.SUCCESS);
        messageDto.setText(priceRange);
        return messageDto;
    }

}
