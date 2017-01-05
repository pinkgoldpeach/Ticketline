package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.model.CustomerStatus;
import at.ac.tuwien.inso.ticketline.model.Gender;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class CustomerServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private CustomerService service;

    @Test
    public void getAllCustomers() {
        //test if deleted customers are found by getAllCustomers()
        try {
            List<Customer> customers = service.getAllCustomers();
            assertEquals(10, customers.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void save() throws Exception {
        Customer customer = new Customer(
                null, Gender.MALE, "Firstname", "Lastname", "f.l@sample.com", new Date(),
                CustomerStatus.VALID
        );

        Address address = new Address();
        address.setPostalCode("XY 1234");
        address.setCountry("AT");
        address.setCity("Vienna");
        address.setStreet("Fakestreet 123");
        customer.setAddress(address);

        try {
            //Save new Customer
            customer = service.save(customer);
            assertTrue(0 < customer.getId());

            Integer storedCustomerId = customer.getId();

            //Change existing Customer and check if saved as same record
            customer.setFirstname("NewFirstname");
            customer.setFirstname("NewLastname");

            customer = service.save(customer);
            assertEquals(customer.getId(), storedCustomerId);

            //Reset ID - should be saved as new record
            customer.setId(0);
            customer = service.save(customer);
            assertNotEquals(customer.getId(), storedCustomerId);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }

        exception.expect(ServiceException.class);
        service.save(null);

    }

}
