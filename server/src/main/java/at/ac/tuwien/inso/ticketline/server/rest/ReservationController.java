package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
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
import java.util.Base64;
import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "reservation", description = "Reservation REST service")
@RestController
@RequestMapping(value = "/reservation")
public class ReservationController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;


    /**
     * Gets all valid reservation items.
     *
     * @return list of all valid reservation items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all reservations", response = ReservationDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReservationDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        delete();
        return EntityToDto.convertReservations(reservationService.getAll(), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all reservations of a page", response = ReservationDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReservationDto> getReservationsPage(@ApiParam(name = "pagenumber", value = "Number of reservation page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getReservationsPage() called");
        delete();
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertReservations(reservationService.getPageOfReservations(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Publishes a new reservation.
     *
     * @param reservationDto the reservation to be saved
     * @return the message dto
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Publishes a new Reservation", response = ReservationPublishDto.class)
    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto publishReservation(@ApiParam(name = "reservation", value = "Reservation to publish") @Valid @RequestBody ReservationPublishDto reservationDto) throws ServiceException, ValidationException {
        LOGGER.info("publishReservation() called");

        Reservation toStore = DtoToEntity.convert(reservationDto);

        //Save Reservation
        Reservation reservation = this.reservationService.save(
                toStore,
                adminService.getCurrentlyLoggedInEmployee().getId(),
                reservationDto.getCustomerId()
        );

        //Process over Tickets
        // Hannes: sicherheitshalber nur reingehen, wenns auch tickets gibt (bei area reservations kommen keine tickets mehr mit)
        if (reservationDto.getTickets() != null) {
            for (TicketPublishDto tdto : reservationDto.getTickets()) {
                Ticket ticket = DtoToEntity.convert(tdto);
                ticket.setReservation(reservation);
                ticket = ticketService.save(ticket, tdto.getSeatId(), tdto.getAreaId(), reservation.getId(), tdto.getShowId());
                LOGGER.debug("Reservation {} - Ticket {} created", reservation.getId(), ticket.getId());
            }
        }

        //Set tickets back on Reservation - bidirectional
        //reservation.setTickets(tickets);
        //reservation = this.reservationService.save(reservation);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(reservation.getReservationNumber());
        return msg;
    }


    /**
     * Gets a reservation by its number
     *
     * @param reservationNumber of Reservation to look up
     * @return reservation
     * @throws ServiceException    on error
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Finds a Reseravtion by its Reservation Number", response = ReservationDto.class)
    @RequestMapping(value = "/findByReservationNumber/{reservationNumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ReservationDto findByReservationNumber(@ApiParam(name = "reservationNumber", value = "Reservation number to look up") @PathVariable("reservationNumber") String reservationNumber) throws ServiceException, ValidationException {
        LOGGER.info("findByReservationNumber() called with reservation number {}", reservationNumber);

        delete();
        Reservation reservation = reservationService.findByReservationNumber(reservationNumber);

        if (reservation == null)
            throw new ServiceException("No reservation found");

        return EntityToDto.convert(reservation, new ArrayList<Class<?>>());
    }


    /**
     * Find all reservations with a specific customer lastname
     * @param lastname of the customer
     * @return list of reservations
     * @throws ServiceException on error
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Find reservations by a certain customer lastname", response = ReservationDto.class, responseContainer = "List")
    @RequestMapping(value = "/findReservationByLastname/{lastname}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReservationDto> findReservationByLastname(@ApiParam(name = "lastname", value = "Lastname of customer to look up") @PathVariable("lastname") String lastname) throws ServiceException, ValidationException {

        String lastnameDecoded = new String(Base64.getDecoder().decode(lastname));

        LOGGER.info("findReservationByLastname() called with: '{}' and decoded name: {}", lastname, lastnameDecoded);
        delete();
        inputValidation.checkNameWithUmlauts(lastnameDecoded);
        List<Reservation> list = reservationService.findReservationByLastName(lastnameDecoded);

        return EntityToDto.convertReservations(list, new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Find reservations by a performanceID", response = ReservationDto.class, responseContainer = "List")
    @RequestMapping(value = "/findReservationByPerformanceID", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReservationDto> findReservationByPerformanceID(@RequestParam("performanceID") Integer performanceID) throws ServiceException, ValidationException {
        LOGGER.info("findReservationByPerformanceID() called");
        delete();
        inputValidation.checkId(performanceID);
        Performance performance = new Performance();
        performance.setId(performanceID);
        List<Reservation> list = reservationService.getReservationsByPerformance(performance);

        return EntityToDto.convertReservations(list, new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Find reservations by a performance name", response = ReservationDto.class, responseContainer = "List")
    @RequestMapping(value = "/findReservationByPerformanceName", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReservationDto> findReservationByPerformanceName(@RequestParam("performanceName") String name) throws ServiceException, ValidationException {
        LOGGER.info("findReservationByPerformanceName() called");
        delete();
        inputValidation.checkLettersWithSpecials(name);
        Performance performance = new Performance();
        performance.setName(name);
        List<Reservation> list = reservationService.getReservationsByPerformanceName(performance);

        return EntityToDto.convertReservations(list, new ArrayList<Class<?>>());
    }


    @ApiOperation(value = "Cancel some tickets of a Reservation", response = CancelTicketDto.class)
    @RequestMapping(value = "/cancelReservationPosition", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto cancelReservationPosition(@ApiParam(name = "cancelTickets", value = "cancelTicketDto to be cancelled") @Valid @RequestBody CancelTicketDto cancelTicketDto) throws ServiceException, ValidationException {
        LOGGER.info("cancelReservationPosition() called");
        delete();
        if (cancelTicketDto.getReservationNumber() != null)
            inputValidation.checkLettersNoSpecial(cancelTicketDto.getReservationNumber());
        else
            throw new ServiceException("Reservation Number is null");

        reservationService.cancelReservationPosition(cancelTicketDto);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        return msg;
    }

    @ApiOperation(value = "Cancel complete reservation", response = MessageDto.class)
    @RequestMapping(value = "/cancelReservationByNumber", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto cancelReservationByNumber(@ApiParam(name = "reservationNumber", value = "cancelTicketDto to be cancelled") @Valid @RequestBody MessageDto messageDto) throws ServiceException, ValidationException {
        LOGGER.info("cancelReservationByNumber() called");
        delete();
        MessageDto msg = new MessageDto();

        inputValidation.checkLettersNoSpecial(messageDto.getText());

        if (reservationService.cancelReservationByNumber(messageDto.getText())) {
            msg.setType(MessageType.SUCCESS);
        } else {
            msg.setType(MessageType.ERROR);
            msg.setText("Reservation ID not found");
        }
        return msg;
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
        LOGGER.info("Reservation :: getPageCount() called");
        delete();
        return EntityToDto.convertPageCount(reservationService.getPageCount());
    }

    private void delete() throws ServiceException {
        List<Reservation> deprecatedReservations = reservationService.findDeprecatedReservations();
        for (Reservation deprecated : deprecatedReservations) {
            reservationService.delete(deprecated);
        }
    }

}
