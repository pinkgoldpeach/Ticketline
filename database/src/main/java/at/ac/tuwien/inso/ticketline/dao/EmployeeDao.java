package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Employee}
 */
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    /**
     * Finds employees by username.
     *
     * @param username the username
     * @return the list of employees
     */
    List<Employee> findByUsername(String username);

    /**
     * find employees by id
     * @param id of the employee
     * @return employee with that id
     */
    Employee findById(Integer id);

    /**
     * all employees by page
     * @param pageable Abstract interface for pagination information.
     * @return a page with employees
     */
    Page<Employee> findAll(Pageable pageable);

}
