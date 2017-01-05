package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link at.ac.tuwien.inso.ticketline.model.Customer}
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 */
public interface CustomerService {

    /**
     * Saves the given news object and returns the saved entity.
     *
     * @param customer object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    Customer save(Customer customer) throws ServiceException;

    /**
     * Returns a collection of all customers.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    List<Customer> getAllCustomers() throws ServiceException;

    /**
     * Returns customers fot given page
     *
     * @param pageNumber     page number of customers
     * @param entriesPerPage number of records to be shown per page
     * @return customers for given page number
     * @throws ServiceException on error
     */
    List<Customer> getPageOfCustomers(int pageNumber, int entriesPerPage) throws ServiceException;

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
