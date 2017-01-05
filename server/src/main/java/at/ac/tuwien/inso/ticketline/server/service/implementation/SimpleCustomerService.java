package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.CustomerService} interface
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleCustomerService extends AbstractPaginationService implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCustomerService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer save(Customer customer) throws ServiceException {
        try {
            return customerDao.save(customer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getAllCustomers() throws ServiceException {
        try {
            return customerDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getPageOfCustomers(int pagenumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Customer> customers = customerDao.findAll(new PageRequest(pagenumber, entriesPerPage));
            return customers.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return customerDao.count();
        } catch (Exception e) {
            LOGGER.error("Requesting page count - " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getPageCount() throws ServiceException {
        LOGGER.info("getPageCount() was called");
        try {
            return calculatePages(customerDao.count());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setCustomerDao(CustomerDao dao) {
        this.customerDao = dao;
    }

    // -------------------- For Testing purposes --------------------

}
