package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.Permission;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleAdminService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class AdminServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleAdminService service = null;
    private List<Employee> employees = null;

    @Before
    public void setUp() throws Exception {
        service = new SimpleAdminService();
        employees = new ArrayList<>();

        Employee e1 = new Employee("firstname1", "lastname1", "username1", "password1");
        e1.setInsuranceNumber("1");
        e1.setPermission(Permission.ROLE_USER);
        e1.setId(1);
        employees.add(e1);

        Employee e2 = new Employee("firstname2", "lastname2", "username2", "password2");
        e2.setInsuranceNumber("2");
        e2.setPermission(Permission.ROLE_USER);
        e2.setId(2);
        employees.add(e2);

        Employee e3 = new Employee("firstname3", "lastname3", "username3", "password3");
        e3.setInsuranceNumber("3");
        e3.setPermission(Permission.ROLE_USER);
        e3.setId(3);
        employees.add(e3);

        Employee e4 = new Employee("firstname4", "lastname4", "username4", "password4");
        e4.setInsuranceNumber("4");
        e4.setPermission(Permission.ROLE_USER);
        e4.setId(4);
        employees.add(e4);

        Employee e5 = new Employee("firstname5", "lastname5", "username5", "password5");
        e5.setInsuranceNumber("5");
        e5.setPermission(Permission.ROLE_USER);
        e5.setId(5);
        employees.add(e5);


    }

    @Test
    public void getAllEmployees() {
        EmployeeDao dao = Mockito.mock(EmployeeDao.class);
        Mockito.when(dao.findAll()).thenReturn(employees);
        service.setEmployeeDao(dao);

        try {
            assertEquals(5, service.getAllEmployees().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getAllEmployees_NoDb() throws Exception {
        EmployeeDao dao = Mockito.mock(EmployeeDao.class);
        Mockito.when(dao.findAll()).thenThrow(new RuntimeException("No DB"));
        service.setEmployeeDao(dao);

        exception.expect(ServiceException.class);
        service.getAllEmployees();
    }

    @Test
    public void getPageCount() throws Exception {
        EmployeeDao dao = Mockito.mock(EmployeeDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setEmployeeDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        EmployeeDao dao = Mockito.mock(EmployeeDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setEmployeeDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        EmployeeDao dao = Mockito.mock(EmployeeDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setEmployeeDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}