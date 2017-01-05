package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.SeatProcessingService;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import at.ac.tuwien.inso.ticketline.server.util.General;
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
 * Created by Carla on 16/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
@Api(value = "seat", description = "Seat REST service")
@RestController
@RequestMapping(value = "/seat")
public class SeatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatProcessingService seatProcessingService;

    @Autowired
    private TicketService ticketService;


    @ApiOperation(value = "Gets seats by row", response = SeatDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRow", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<SeatDto> getByRow(@RequestParam("rowID") Integer rowID) throws ServiceException, ValidationException {
        LOGGER.info("getByRow() called");

        inputValidation.checkId(rowID);
        Row row = new Row();
        row.setId(rowID);

        return EntityToDto.convertSeats(seatService.getSeatsByRow(row), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets seats by room", response = SeatDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRoom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<SeatDto> getByRoom(@RequestParam("roomID") Integer roomID) throws ServiceException, ValidationException {
        LOGGER.info("getByRoom() called");

        inputValidation.checkId(roomID);
        Room room = new Room();
        room.setId(roomID);

        return EntityToDto.convertSeats(seatService.getSeatsByRoom(room), new ArrayList<Class<?>>());
    }

//---------------------------------------------------------


    /**
     * Returns all locked seats for a show
     *
     * @param showId show seats are requested for
     * @return list of locked seats
     * @throws ServiceException    on error
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Gets all SeatIDs in processing mode for a Show ", response = UsedSeatsDto.class)
    @RequestMapping(value = "/getLockedSeats", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public UsedSeatsDto getLockedSeats(@RequestParam(name = "showId") Integer showId) throws ServiceException, ValidationException {
        LOGGER.info("getLockedSeats() called with showId {}", showId);
        inputValidation.checkId(showId);
        return seatProcessingService.getProcessingSeats(showId);
    }

    /**
     * Locks seats for a show for the actual user
     *
     * @param usedSeatsDto with showID and seat list
     * @return true if seats could be locked
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Lock Seats and switch them into processing mode", response = Boolean.class)
    @RequestMapping(value = "/lockSeats", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto lockSeats(@ApiParam(name = "usedSeatsDto", value = "Show ID with list of sits to be locked") @Valid @RequestBody UsedSeatsDto usedSeatsDto)
            throws ServiceException, ValidationException {
        LOGGER.info("lockSeats() called show {} and seats {}", usedSeatsDto.getShowId(), usedSeatsDto.getIds());
        inputValidation.checkId(usedSeatsDto.getShowId());

        boolean success = false;
        if (seatProcessingService.checkProcessingSeats(usedSeatsDto.getShowId(), usedSeatsDto.getIds())) {
            success = seatProcessingService.addToProcessingSeats(usedSeatsDto, General.getUserName());
        }
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.INFO);
        msg.setText("" + success);
        return msg;
    }


    /**
     * Unlock seats for the actual user
     *
     * @return success message
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Unlock Seats and switch them back to normal mode", response = MessageDto.class)
    @RequestMapping(value = "/unlockSeats", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto unlockSeats() throws ServiceException {

        String username = General.getUserName();

        LOGGER.info("unlockSeats() called for user {}", username);
        seatProcessingService.removeProcessingSeatsForUser(username);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText("Successfully unlocked Seats for user " + username);
        return msg;
    }

    /**
     * this returns if a seat has successfully been unlocked
     *
     * @param processingSeatDto the dto of the seat that needs to be unlocked.
     * @return success message
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure, if the ids are not valid
     */
    @ApiOperation(value = "Unlock Seat and switch it back to normal mode", response = MessageDto.class)
    @RequestMapping(value = "/unlockSeat", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto unlockSeat(@ApiParam(name = "processingSeat", value = "Ticket to publish") @Valid @RequestBody ProcessingSeatDto processingSeatDto) throws ServiceException, ValidationException {

        String username = General.getUserName();

        LOGGER.info("unlockSeat() called for user {}", username);

        inputValidation.checkId(processingSeatDto.getSeatId());
        inputValidation.checkId(processingSeatDto.getShowId());

        seatProcessingService.freeProcessingSeat(new ProcessingSeat(processingSeatDto.getShowId(), processingSeatDto.getSeatId()), username);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText("Successfully unlocked Seat {} for Show {} " + username);
        return msg;
    }

    /**
     * Gets all sold seats for a show
     *
     * @param showId ID of show
     * @return sold seats
     * @throws ServiceException    on error
     * @throws ValidationException on show id validation failure
     */
    @ApiOperation(value = "Gets all sold seats for a show", response = UsedSeatsDto.class)
    @RequestMapping(value = "/getSoldSeats/{showId}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public UsedSeatsDto getSoldSeats(@ApiParam(name = "showId", value = "ID of Show") @PathVariable("showId") Integer showId) throws ServiceException, ValidationException {
        LOGGER.info("getSoldSeats() called for show {}", showId);
        inputValidation.checkId(showId);
        return ticketService.getSoldSeats(showId);
    }

    /**
     * Gets all reserved seats for a show
     *
     * @param showId ID of show
     * @return reserved seats
     * @throws ServiceException    on error
     * @throws ValidationException on show id validation failure
     */
    @ApiOperation(value = "Gets all reserved seats for a show", response = UsedSeatsDto.class)
    @RequestMapping(value = "/getReservedSeats/{showId}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public UsedSeatsDto getReservedSeats(@ApiParam(name = "showId", value = "ID of Show") @PathVariable("showId") Integer showId) throws ServiceException, ValidationException {
        LOGGER.info("getReservedSeats() called for show {}", showId);
        inputValidation.checkId(showId);
        return ticketService.getReservedSeats(showId);
    }


    //--------- FOR TESTING
    public SeatController() {
    }

    public SeatController(SeatService seatService, SeatProcessingService seatProcessingService) { //, General generalUtils) {
        this.seatService = seatService;
        this.seatProcessingService = seatProcessingService;
        //this.generalUtils = generalUtils;
    }
}


