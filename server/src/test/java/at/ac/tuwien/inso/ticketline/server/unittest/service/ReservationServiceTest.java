package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.ReservationDao;
import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleReservationService;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleTicketService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ReservationServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleReservationService service = null;
    private List<Reservation> reservations = null;


    @Before
    public void setUp() throws Exception {
        service = new SimpleReservationService();
        reservations = new ArrayList<>();


        Date dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(5000)).getTime();
        String email = "@sample.sa";
        String firstname = "Max";
        String lastname = "Mustermann";
        CustomerStatus status = CustomerStatus.VALID;

        Customer c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(email);
        c1.setDateOfBirth(dateofbirth);
        c1.setCustomerStatus(status);

        Employee e1 = new Employee(firstname, lastname, "admin", "irgendwas");

        e1.setDateOfBirth(dateofbirth);
        e1.setEmployedSince(dateofbirth);
        e1.setPermission(Permission.ROLE_USER);
        e1.setEmail("marvin"  + "@hallo.to");
        e1.setGender(Gender.MALE);
        e1.setInsuranceNumber("insurance!");
        e1.setLastLogin(dateofbirth);

        for (int i = 0; i < 5; i++) {
            Reservation reservation = new Reservation();
            reservation.setCustomer(c1);
            reservation.setEmployee(e1);
            reservation.setReservationNumber("ABC12" + i);

            reservations.add(reservation);
        }
    }


    @Test
    public void getPageCount() throws Exception {
        ReservationDao dao = Mockito.mock(ReservationDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setReservationDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        ReservationDao dao = Mockito.mock(ReservationDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setReservationDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        ReservationDao dao = Mockito.mock(ReservationDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setReservationDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }


    @Test
    public void getReservationByIdTest() {

        ReservationDao dao = Mockito.mock(ReservationDao.class);
        Mockito.when(dao.findByReservationNumber("ABC120")).thenReturn(reservations.get(0));
        service.setReservationDao(dao);

        try {
            assertEquals("ABC120", service.findByReservationNumber("ABC120").getReservationNumber());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

}
