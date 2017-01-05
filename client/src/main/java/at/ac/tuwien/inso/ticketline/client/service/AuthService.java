package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.UserEventDto;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;

/**
 * The Interface AuthService.
 */
public interface AuthService {

    /**
     * Gets the user status.
     *
     * @return the user status
     */
    UserStatusDto getUserStatus();

    /**
     * Authenticate the user with a given username and password.
     *
     * @param username the username for the login
     * @param password the password for the login
     * @return true if login was successful, false otherwise
     * @throws ServiceException if a problem occurs
     */
    UserEventDto login(String username, String password) throws ServiceException;

    /**
     * Logout the current authenticated user.
     *
     * @throws ServiceException if a problem occurs
     */
    void logout() throws ServiceException;
}
