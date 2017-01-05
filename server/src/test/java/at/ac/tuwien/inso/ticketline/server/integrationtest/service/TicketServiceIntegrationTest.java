package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.dto.UsedAreasDto;
import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Carla on 26/06/2016.
 */
public class TicketServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    ReservationService reservationService;

    @Test
    public void testGetAllValidTickets(){
        try {
            List<Ticket> tickets = ticketService.getAllValidTickets();
            assertTrue(tickets.size() == 13);
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void getAllTickets(){
        try {
            List<Ticket> tickets = ticketService.getAllTickets();
            assertTrue(tickets.size() == 13);
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }

    }


    @Test
    public void saveTicketOfAlreadyUsedSeat(){
        try {
            Ticket ticket = new Ticket();
            ticket.setPrice(100.00);
            ticket.setId(14);

            ticketService.save(ticket, 5, null, null, 3);

            fail("ServiceException not thrown");

        } catch (ServiceException e) {
        }
    }

    @Test
    public void getReservedSeats(){
        try {
            assertEquals("Amount of reserved seats for show 1", ticketService.getReservedSeats(1).getIds().size(), 0);
            assertEquals("Amount of reserved seats for show 3", ticketService.getReservedSeats(3).getIds().size(), 1);

        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void getSoldSeats(){
        try {
            assertEquals("Amount of sold seats for show 1", ticketService.getSoldSeats(1).getIds().size(), 7);
            assertEquals("Amount of sold seats for show 3", ticketService.getSoldSeats(3).getIds().size(), 5);

        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void deleteSuccessfullyTickets(){
        try {
            List<Integer> ids = new LinkedList<>();
            ids.add(6);
            ids.add(7);
            CancelTicketDto cancelTicketDto = new CancelTicketDto();
            cancelTicketDto.setIds(ids);

            assertEquals("Amount of all Tickets", ticketService.getAllTickets().size(), 13);

            ticketService.deleteTickets(cancelTicketDto);

            assertEquals("Amount of all Tickets", ticketService.getAllTickets().size(), 11);


        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void deleteUnsuccessfullyTickets(){
        try {
            List<Ticket> tickets = ticketService.getAllTickets();
            List<Integer> ids = new LinkedList<>();
            ids.add(tickets.get(0).getId());
            ids.add(0);
            ids.add(1);
            CancelTicketDto cancelTicketDto = new CancelTicketDto();
            cancelTicketDto.setIds(ids);

            assertEquals("Amount of all Tickets", ticketService.getAllTickets().size(), 13);

            ticketService.deleteTickets(cancelTicketDto);

            fail("ServiceException not thrown");

        } catch (ServiceException e) {
        }
    }

    @Test
    public void bookTicketsForArea(){
        try {
            UsedAreasDto usedAreasDto = new UsedAreasDto();
            usedAreasDto.setShowID(1);
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            hashMap.put(1, 20);
            hashMap.put(2, 10);
            usedAreasDto.setAmountPerArea(hashMap);

            List<Ticket> tickets = ticketService.bookTicketAmount(usedAreasDto);

            assertEquals("Amount of booked Tickets", tickets.size(), 30);

        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void bookTooMuchTicketsForArea(){
        try {
            UsedAreasDto usedAreasDto = new UsedAreasDto();
            usedAreasDto.setShowID(1);
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            hashMap.put(1, 200);
            hashMap.put(2, 100);
            usedAreasDto.setAmountPerArea(hashMap);

            List<Ticket> tickets = ticketService.bookTicketAmount(usedAreasDto);

            fail("ServiceException not thrown:");

        } catch (ServiceException e) {
        }
    }

}
