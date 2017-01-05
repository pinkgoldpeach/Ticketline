package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.*;

import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public interface TicketService {

    /**
     * returns a list of all Tickets
     *
     * @return list of all Tickets
     * @throws ServiceException if connection to server went wrong
     */
    List<TicketDto> getAllTickets() throws ServiceException;

    /**
     * saves a ticket on the server and returns the id of the ticket
     *
     * @param ticket the ticket to save
     * @return id of the ticket encapsulated in a MessageDto
     * @throws ServiceException if connection to server went wrong
     */
    MessageDto saveTicket(TicketPublishDto ticket) throws ServiceException;

    /**
     * cancels ticket on the server
     *
     * @param dto the ticket to save
     * @return id of the ticket encapsulated in a MessageDto
     * @throws ServiceException if connection to server went wrong
     */
    MessageDto cancelSoldTicket(CancelTicketDto dto) throws ServiceException;

    BookedAreaTicketsDto bookTicketsForArea(UsedAreasDto usedAreasDto) throws ServiceException;


    MessageDto deleteTickets(CancelTicketDto cancelTicketDto) throws ServiceException;

}
