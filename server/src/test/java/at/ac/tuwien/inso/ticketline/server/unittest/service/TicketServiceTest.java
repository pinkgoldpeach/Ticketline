package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleTicketService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class TicketServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleTicketService service = null;
    private List<Integer> ticketsReserved = null;
    private List<Integer> ticketsSold = null;

    @Before
    public void setUp() throws Exception {
        service = new SimpleTicketService();
        ticketsReserved = new ArrayList<>();
        ticketsSold = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ticketsReserved.add(i);
        }

        ticketsSold.add(6);
    }

    @Test
    public void getReservedSeats() {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.getReservedSeats(any(Integer.class))).thenReturn(ticketsReserved);
        service.setTicketDao(dao);

        try {
            assertEquals(5, service.getReservedSeats(1).getIds().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getReservedSeats_NoDb() throws Exception {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.getReservedSeats(any(Integer.class))).thenThrow(new RuntimeException());
        service.setTicketDao(dao);

        exception.expect(ServiceException.class);
        service.getReservedSeats(1);
    }

    @Test
    public void getSoldSeats() {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.getSoldSeats(any(Integer.class))).thenReturn(ticketsSold);
        service.setTicketDao(dao);

        try {
            assertEquals(1, service.getSoldSeats(1).getIds().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getSoldSeats_NoDb() throws Exception {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.getSoldSeats(any(Integer.class))).thenThrow(new RuntimeException());
        service.setTicketDao(dao);

        exception.expect(ServiceException.class);
        service.getSoldSeats(1);
    }

    @Test
    public void getPageCount() throws Exception {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setTicketDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setTicketDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        TicketDao dao = Mockito.mock(TicketDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setTicketDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}