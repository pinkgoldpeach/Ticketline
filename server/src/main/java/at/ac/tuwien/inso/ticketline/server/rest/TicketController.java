package at.ac.tuwien.inso.ticketline.server.rest;


import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
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
import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
@Api(value = "ticket", description = "Ticket REST service")
@RestController
@RequestMapping(value = "/ticket")
public class TicketController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private TicketService ticketService;


    @ApiOperation(value = "Gets all tickets of a page", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getTicketsPage(@ApiParam(name = "pagenumber", value = "Number of ticket page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getTicketsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertTickets(ticketService.getPageOfTickets(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all tickets of a page", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/getValidTicketsPage/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getValidTicketsPage(@ApiParam(name = "pagenumber", value = "Number of ticket page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getValidTicketsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertTickets(ticketService.getPageOfValidTickets(pageNumber), new ArrayList<Class<?>>());
    }

    /**
     * Publishes a new ticket.
     *
     * @param ticketDto the ticket to be saved
     * @return the message dto
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation faiure
     */
    @ApiOperation(value = "Publishes a new ticket", response = TicketPublishDto.class)
    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto publishTicket(@ApiParam(name = "ticket", value = "Ticket to publish") @Valid @RequestBody TicketPublishDto ticketDto) throws ServiceException, ValidationException {
        LOGGER.info("publishTicket() called");
        inputValidation.checkLettersWithSpecialsNoUmlauts(ticketDto.getDescription());

        Ticket toStore = DtoToEntity.convert(ticketDto);
        Integer id = this.ticketService.save(
                toStore,
                ticketDto.getSeatId(),
                ticketDto.getAreaId(),
                ticketDto.getReservationId(),
                ticketDto.getShowId()
        ).getId();

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(id.toString());
        return msg;
    }

    /**
     * Gets all ticket items.
     *
     * @return list of all ticket items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all tickets", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertTickets(ticketService.getAllTickets(), new ArrayList<Class<?>>());
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
        LOGGER.info("Ticket :: getPageCount() called");
        return EntityToDto.convertPageCount(ticketService.getPageCount());
    }

    /**
     * Gets all valid ticket items.
     *
     * @return list of all valid ticket items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all valid tickets", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/getAllValid", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getAllValid() throws ServiceException {
        LOGGER.info("getAllValid() called");
        return EntityToDto.convertTickets(ticketService.getAllValidTickets(), new ArrayList<Class<?>>());
    }


    @ApiOperation(value = "Cancel sold tickets", response = CancelTicketDto.class)
    @RequestMapping(value = "/cancelSoldTicket", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto cancelSoldTicket(@ApiParam(name = "tickets", value = "cancelTicketDto to be cancelled") @Valid @RequestBody CancelTicketDto tickets) throws ServiceException, ValidationException {
        LOGGER.info("cancelSoldTicket() called");

        if(tickets.getReceiptID() != null)
            inputValidation.checkId(tickets.getReceiptID());
        else
            throw new ServiceException("No Receipt ID");

        ticketService.cancelSoldTicket(tickets);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        return msg;
    }

    @ApiOperation(value = "Gets a ticket by Id", response = TicketDto.class)
    @RequestMapping(value = "/Id/{Id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public TicketDto getTicketById(@ApiParam(name = "Id", value = "Id of ticket") @PathVariable("Id") Integer id) throws ServiceException, ValidationException {
        LOGGER.info("getTicketById() called");
        inputValidation.checkId(id);
        Ticket ticket = ticketService.getTicketByID(id);
        ticket.setReservation(null);
        return EntityToDto.convert(ticket, new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Books tickets for an area", response = TicketPublishDto.class)
    @RequestMapping(value = "/bookTicketsForArea", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public BookedAreaTicketsDto bookTickets(@ApiParam(name = "tickets", value = "Tickets to book for an area") @Valid @RequestBody UsedAreasDto usedAreasDto) throws ServiceException, ValidationException {
        LOGGER.info("bookTickets() called");
        inputValidation.checkId(usedAreasDto.getShowID());

        List<Ticket> tickets = ticketService.bookTicketAmount(usedAreasDto);

        return new BookedAreaTicketsDto(EntityToDto.convertTickets(tickets, new ArrayList<>()));
    }

    @ApiOperation(value = "Delete tickets which are not used yet", response = MessageDto.class)
    @RequestMapping(value = "/deleteTickets", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto deleteTickets(@ApiParam(name = "tickets", value = "cancelTicketDto with IDs to be deleted") @Valid @RequestBody CancelTicketDto tickets) throws ServiceException, ValidationException {
        LOGGER.info("deleteTickets() called");

        ticketService.deleteTickets(tickets);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        return msg;
    }

}
