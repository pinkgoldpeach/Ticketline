package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.model.CustomerStatus;
import at.ac.tuwien.inso.ticketline.model.Gender;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleCustomerService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class CustomerServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleCustomerService service = null;
    private List<Customer> customers = null;

    @Before
    public void setUp() throws Exception {
        service = new SimpleCustomerService();
        customers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Customer c = new Customer(i, Gender.MALE, "Firstname" + i, "Lastname" + i, "email" + i + "@abc.com",
                    new Date(), CustomerStatus.VALID);
            customers.add(c);
        }
    }

    @Test
    public void getAllCustomers() {
        CustomerDao dao = Mockito.mock(CustomerDao.class);
        Mockito.when(dao.findAll()).thenReturn(customers);
        service.setCustomerDao(dao);

        try {
            assertEquals(5, service.getAllCustomers().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getAllCustomers_NoDb() throws Exception {
        CustomerDao dao = Mockito.mock(CustomerDao.class);
        Mockito.when(dao.findAll()).thenThrow(new RuntimeException("No DB"));
        service.setCustomerDao(dao);

        exception.expect(ServiceException.class);
        service.getAllCustomers();
    }

    @Test
    public void getPageCount() throws Exception {
        CustomerDao dao = Mockito.mock(CustomerDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setCustomerDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        CustomerDao dao = Mockito.mock(CustomerDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setCustomerDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        CustomerDao dao = Mockito.mock(CustomerDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setCustomerDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}