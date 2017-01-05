package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.security.AuthUtil;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.AdminService} interface
 */
@Service
public class SimpleAdminService extends AbstractPaginationService implements AdminService {

    @Autowired
    private EmployeeDao employeeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCustomerService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee save(Employee employee) throws ServiceException {
        try {
            if ((employee.getPasswordHash() == null) && (employee.getId() != null)) { // set passwordHash from database when password don't get changed
                employee.setPasswordHash(employeeDao.findById(employee.getId()).getPasswordHash());
            }

            if (employee.getId() == null) {
                employee.setLastLogin(employee.getEmployedSince());
            } else {
                employee.setLastLogin(employeeDao.findById(employee.getId()).getLastLogin());
            }

            return employeeDao.save(employee);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees() throws ServiceException {

        try {
            return employeeDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getPageOfEmployees(int pageNumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Employee> employees = employeeDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return employees.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee getCurrentlyLoggedInEmployee() throws ServiceException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserStatusDto userStatusDto = AuthUtil.getUserStatusDto(authentication);
            String username = userStatusDto.getUsername();
            return employeeDao.findByUsername(username).get(0);
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
            return employeeDao.count();
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
            return calculatePages(employeeDao.count());
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
    public void setEmployeeDao(EmployeeDao dao) {
        this.employeeDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
