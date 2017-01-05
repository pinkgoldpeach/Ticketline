package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link at.ac.tuwien.inso.ticketline.model.Employee}
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 */
public interface AdminService {
    /**
     * Saves the given news object and returns the saved entity.
     *
     * @param employee object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    Employee save(Employee employee) throws ServiceException;

    /**
     * Returns a collection of all news.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    List<Employee> getAllEmployees() throws ServiceException;

    List<Employee> getPageOfEmployees(int pageNumber, int entriesPerPage) throws ServiceException;

    Employee getCurrentlyLoggedInEmployee() throws ServiceException;

    /**
     * Returns the number of records
     *
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     *
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;
}
