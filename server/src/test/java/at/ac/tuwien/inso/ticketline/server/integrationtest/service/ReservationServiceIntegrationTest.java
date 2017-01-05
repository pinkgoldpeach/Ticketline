package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.dto.UsedAreasDto;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Patrick Weiszirchner
 */
public class ReservationServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Test
    public void makeReservationWithoutTickets() {
        try {
            Reservation reservation = new Reservation();

            assertNotNull(reservationService.save(reservation, adminService.getAllEmployees().get(0).getId(), customerService.getAllCustomers().get(0).getId()));

        } catch (ServiceException se) {
            fail("ServiceException thrown: " + se.getMessage());
        }
    }

    @Test
    public void makeReservationForArea(){
        try {
            Reservation reservation = new Reservation();

            reservation = reservationService.save(reservation, adminService.getAllEmployees().get(0).getId(), customerService.getAllCustomers().get(0).getId());

            UsedAreasDto usedAreasDto = new UsedAreasDto();
            usedAreasDto.setShowID(1);
            usedAreasDto.setReservationNumber(reservation.getReservationNumber());
            HashMap<Integer, Integer> amountOfTickets = new HashMap<>();
            amountOfTickets.put(1,5); // 5 Tickets für Area ID 1 buchen
            usedAreasDto.setAmountPerArea(amountOfTickets);

            assertEquals("Amount of book Tickets must equal 5", ticketService.bookTicketAmount(usedAreasDto).size(), 5);


        } catch (ServiceException se) {
            fail("ServiceException thrown: " + se.getMessage());
        }
    }

    @Test
    public void makeReservationForAreaWithTooHighAmount(){
        try {
            Reservation reservation = new Reservation();

            reservation = reservationService.save(reservation, adminService.getAllEmployees().get(0).getId(), customerService.getAllCustomers().get(0).getId());

            UsedAreasDto usedAreasDto = new UsedAreasDto();
            usedAreasDto.setShowID(1);
            usedAreasDto.setReservationNumber(reservation.getReservationNumber());
            HashMap<Integer, Integer> amountOfTickets = new HashMap<>();
            amountOfTickets.put(1,50);
            usedAreasDto.setAmountPerArea(amountOfTickets);

            ticketService.bookTicketAmount(usedAreasDto);

            fail("ServiceException not thrown because of too high amount");

        } catch (ServiceException se) {
        }
    }

    @Test
    public void makeReservationAndFindIt(){
        try {
            Reservation reservation = new Reservation();

            String resNumber = reservationService.save(reservation, adminService.getAllEmployees().get(0).getId(), customerService.getAllCustomers().get(0).getId()).getReservationNumber();

            assertNotNull(reservationService.findByReservationNumber(resNumber));
            assertNotNull(reservationService.findReservationByLastName(customerService.getAllCustomers().get(0).getLastname()));

            UsedAreasDto usedAreasDto = new UsedAreasDto();
            usedAreasDto.setShowID(1);
            usedAreasDto.setReservationNumber(reservation.getReservationNumber());
            HashMap<Integer, Integer> amountOfTickets = new HashMap<>();
            amountOfTickets.put(1,5);
            usedAreasDto.setAmountPerArea(amountOfTickets);

            ticketService.bookTicketAmount(usedAreasDto);


            Performance performance = new Performance();
            performance.setId(1);
            assertEquals(reservationService.getReservationsByPerformance(performance).size(), 1);

            performance = new Performance();
            performance.setName("RHCP");
            assertEquals(reservationService.getReservationsByPerformanceName(performance).size(), 1);

            assertEquals(reservationService.getAll().size(), 2);


        } catch (ServiceException se) {
            fail("ServiceException thrown: " + se.getMessage());
        }
    }

    @Test
    public void makeReservationAndDeleteIt(){
        try {
            Reservation reservation = new Reservation();

            reservation = reservationService.save(reservation, adminService.getAllEmployees().get(0).getId(), customerService.getAllCustomers().get(0).getId());

            reservationService.delete(reservation);

            assertEquals("Now no reservation should be available", reservationService.getAll().size(), 1);

        } catch (ServiceException se) {
            fail("ServiceException thrown: " + se.getMessage());
        }
    }

    @Test
    public void deleteUnavailableReservations(){
        try {
            Reservation reservation = new Reservation();
            reservation.setId(2);

            reservationService.delete(reservation);
            fail("ServiceException is not thrown");

        } catch (ServiceException se) {
        }
    }

    @Test
    public void createReservationWithFalseParameters(){
        try {
            Reservation reservation = new Reservation();

            reservationService.save(reservation, 500, customerService.getAllCustomers().get(0).getId());
            fail("ServiceException is not thrown");

        } catch (ServiceException se) {
        }
    }
}
