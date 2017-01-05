package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.Gender;
import at.ac.tuwien.inso.ticketline.model.Permission;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
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
public class AdminServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private AdminService service;

    /**
     * Creates a valid Employee Object
     *
     * @return valid Entity
     */
    private Employee getEmploee() {
        Employee emp = new Employee("Firstname", "Lastname", "userName", "hash12345");
        emp.setInsuranceNumber("abc123");
        emp.setEmployedSince(new Date());
        emp.setAddress(new Address("street", "postalCode", "city", "country"));
        emp.setDateOfBirth(new Date());
        emp.setPermission(Permission.ROLE_USER);
        emp.setEmail("a@b.cd");
        emp.setGender(Gender.FEMALE);
        return emp;
    }

    @Test
    public void getAllEmployees() {
        try {
            List<Employee> employees = service.getAllEmployees();
            assertEquals(10, employees.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void save() throws Exception {
        Employee emp = getEmploee();

        try {
            //Save new employee
            emp = service.save(emp);
            //System.out.println(emp);
            assertTrue(emp.getId() > 0);

            Integer storedId = emp.getId();

            //Change existing Employee and check if saved as same record
            emp.setFirstname("NewFirstname");
            emp = service.save(emp);
            assertEquals(storedId, emp.getId());

        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }

        exception.expect(ServiceException.class);
        service.save(null);
    }
}
