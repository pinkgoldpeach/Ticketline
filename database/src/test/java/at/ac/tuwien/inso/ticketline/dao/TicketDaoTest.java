package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
public class TicketDaoTest extends AbstractDaoTest {

    @Autowired
    private TicketDao ticketDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findAll() {
        List<Ticket> tickets = ticketDao.findAll();
        assertNotNull(tickets);
        assertEquals("Number all seats found", 2, tickets.size());
    }

    @Test
    public void getSoldSeats() {
        List<Ticket> tickets = ticketDao.findAll();
        assertNotNull(tickets);
        assertEquals("Number all seats found", 2, tickets.size());

        List<Integer> seatIds = ticketDao.getSoldSeats(1);
        assertNotNull(seatIds);
        assertEquals("Number sold seats found", 1, seatIds.size());
        assertEquals("Wrong Seat ID", 2, (int) seatIds.get(0));
    }

    @Test
    public void getReservedSeats() {
        List<Ticket> tickets = ticketDao.findAll();
        assertNotNull(tickets);
        assertEquals("Number all seats found", 2, tickets.size());

        List<Integer> seatIds = ticketDao.getReservedSeats(1);
        assertNotNull(seatIds);
        assertEquals("Number reserved seats found", 1, seatIds.size());
        assertEquals("Wrong Seat ID", 1, (int) seatIds.get(0));
    }

}
