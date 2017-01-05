package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;

import java.util.ArrayList;

/**
 * Interface for admin services
 */
public interface SearchService {

    /**
     * Requests an employee list from all in the system
     *
     * @return An array List with all employees in the database
     * @throws ServiceException on error
     */
    ArrayList<EmployeeDto> getAllEmployees() throws ServiceException;

    /**
     * Creates a new employee in the system
     *
     * @param employeeDto - The employee which should be created
     * @return true if employee was created, false otherwise
     * @throws ServiceException on error
     */
    boolean createEmployee(EmployeeDto employeeDto) throws ServiceException;

    /**
     * Updates an existing employee
     *
     * @param employeeDto The employee which should be updated including new data
     * @return true if employee was updated, false otherwise
     * @throws ServiceException on error
     */
    boolean updateEmployee(EmployeeDto employeeDto) throws ServiceException;

    /**
     * Blocks an employee for logging in to the system
     *
     * @param employeeDto - The employee which should be blocked
     * @return true if employee was blocked, false otherwise
     * @throws ServiceException on error
     */
    boolean blockEmployee(EmployeeDto employeeDto) throws ServiceException;

    /**
     * @param employeeDto - The employee from whom the password should be rest including the new one
     * @return true if password was reset, false otherwise
     * @throws ServiceException on error
     */
    boolean resetPassword(EmployeeDto employeeDto) throws ServiceException;

}
