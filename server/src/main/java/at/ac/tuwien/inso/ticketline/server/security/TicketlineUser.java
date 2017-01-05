package at.ac.tuwien.inso.ticketline.server.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Ticketline user.
 * Used by Spring Security
 * 
 */
public class TicketlineUser implements UserDetails {

    private static final long serialVersionUID = -8343481930819178599L;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private List<GrantedAuthority> roles = new ArrayList<>();

    /**
     * Instantiates a new ticketline user.
     *
     * @param username the username
     * @param password the password
     * @param roles the roles
     * @param firstName the first name
     * @param lastName the last name
     */
    public TicketlineUser(String username, String password, List<GrantedAuthority> roles, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getUsername() {
        return this.username;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getPassword() {
        return this.password;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return this.lastName;
    }

}
