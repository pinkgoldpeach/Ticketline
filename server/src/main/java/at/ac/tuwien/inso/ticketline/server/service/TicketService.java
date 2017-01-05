package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.dto.UsedAreasDto;
import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface TicketService {

    /**
     * Returns a collection of all tickets
     *
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all tickets.
     * @throws ServiceException the service exception
     */
    List<Ticket> getPageOfTickets(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * Returns a collection of all valid tickets
     *
     * @param pageNumber the pagenumber you want to access
     * @return a collection of all valid tickets.
     * @throws ServiceException the service exception
     */

    List<Ticket> getPageOfValidTickets(int pageNumber) throws ServiceException;

    /**
     * Saves the given ticket object and returns the saved entity.
     *
     * @param ticket object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    Ticket save(Ticket ticket) throws ServiceException;

    /**
     * Saves the given ticket object and returns the saved entity.
     * To be used for Tickets without set sub-objects like Seat, Reservation and Show, those will be loaded
     * automatically and set by their IDs
     *
     * @param ticket        object to persist
     * @param seatId        Id of Seat Object
     * @param areaID        Id of Area Object
     * @param reservationId Id of Reservation Object
     * @param showId        Id of show Object
     * @return saved entity
     * @throws ServiceException service exception on error
     */
    Ticket save(Ticket ticket, Integer seatId, Integer areaID, Integer reservationId, Integer showId) throws ServiceException;

    /**
     * Returns all tickets
     *
     * @return list of all tickets
     * @throws ServiceException on error
     */
    List<Ticket> getAllTickets() throws ServiceException;

    /**
     * Returns all valid tickets
     *
     * @return list of all valid tickets
     * @throws ServiceException on error
     */
    List<Ticket> getAllValidTickets() throws ServiceException;

    /**
     * Returns all reserved seats for a show
     *
     * @param showId show
     * @return list of reserved seats
     * @throws ServiceException on error
     */
    UsedSeatsDto getReservedSeats(int showId) throws ServiceException;

    /**
     * Returns all sold seats for a show
     *
     * @param showId show
     * @return list of sold seats
     * @throws ServiceException on error
     */
    UsedSeatsDto getSoldSeats(int showId) throws ServiceException;

    /**
     * Return true if it was successful to cancel sold Tickets
     *
     * @param cancelTicketDto cancelTicketDto
     * @throws ServiceException on error
     */
    void cancelSoldTicket(CancelTicketDto cancelTicketDto) throws ServiceException;

    /**
     * Returns a ticket by its ID
     *
     * @param ticketId ticket
     * @return a ticket
     * @throws ServiceException on error
     */
    Ticket getTicketByID(Integer ticketId) throws ServiceException;

    /**
     * generates tickets, if there are enough free tickets available and books them
     * @param usedAreasDto the areas the tickets are for
     * @return the generated tickets
     * @throws ServiceException on error
     */
    List<Ticket> bookTicketAmount(UsedAreasDto usedAreasDto) throws ServiceException;



    /**
     * Returns the number of records
     *
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     *
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;


    /**
     * delete tickets which are not already on a receipt or on a reservation
     *
     * @param cancelTicketDto contains IDs of tickets which should be deleted
     * @throws ServiceException on error
     */
    void deleteTickets(CancelTicketDto cancelTicketDto) throws ServiceException;

}
