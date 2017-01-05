package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Employee;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by dev on 06/05/16.
 */
public class EmployeeDaoTest extends AbstractDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeDao.findAll();
        assertEquals("Check DB initial data - is one first", 5, employees.size());
    }

    @Test
    public void testDelete(){
        List<Employee> employees = employeeDao.findAll();
        assertEquals("Check DB initial data - is one first", 5, employees.size());
        employeeDao.delete(employees.get(4));
        employees = employeeDao.findAll();
        assertEquals("Delete employee from db - employeetable is zero now", 4, employees.size());
    }

    @Test
    public void testUpdate(){
        List<Employee> employees = employeeDao.findAll();
        assertEquals("Check DB initial data - is one first", 5, employees.size());
        employees.get(0).setUsername("neuer_Username");
        employeeDao.save(employees);
        employees = employeeDao.findAll();
        assertEquals("Check DB data size after update - is also one now", 5, employees.size());
        assertEquals("Check new username", "neuer_Username", employees.get(0).getUsername());
    }

}
