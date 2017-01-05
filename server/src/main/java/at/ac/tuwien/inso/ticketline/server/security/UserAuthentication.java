package at.ac.tuwien.inso.ticketline.server.security;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to load a user for Spring Security
 */
public class UserAuthentication implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthentication.class);

    @Autowired
    private EmployeeDao userDao;

    /**
     * Instantiates a new user authentication.
     */
    public UserAuthentication() {
    }

    /**
     * Instantiates a new user authentication.
     *
     * @param userDao the user dao
     */
    public UserAuthentication(EmployeeDao userDao) {
        this.userDao = userDao;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        LOGGER.info("UserAuthentication#loadUserByUsername");
        LOGGER.info("Username: {}", username);
        List<Employee> users = userDao.findByUsername(username);
        Employee loginData;
        if (users.isEmpty()) {
            throw new UsernameNotFoundException(String.format("user %s does not exists", username));
        }
        if (1 == users.size()) {
            loginData = users.get(0);
        } else {
            throw new UsernameNotFoundException(String.format("Username %s is not unique", username));
        }
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(loginData.getPermission().name()));
        return new TicketlineUser(loginData.getUsername(), loginData.getPasswordHash(), auths, loginData.getFirstname(), loginData.getLastname());
    }

    /**
     * Sets the user dao.
     *
     * @param userDao the new user dao
     */
    public void setUserDao(EmployeeDao userDao) {
        this.userDao = userDao;
    }

}
