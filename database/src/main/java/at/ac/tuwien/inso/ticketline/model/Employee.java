package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The employee entity.
 */
@Entity
public class Employee extends Person {

    private static final long serialVersionUID = 1021211581003682919L;

    @Column(nullable = false, length = 50)
    private String insuranceNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date employedSince;

    private Integer loginFailCount = 0;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 1024)
    private String passwordHash;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    

    /**
     * Instantiates a new employee.
     */
    public Employee() {
    }

    /**
     * Instantiates a new employee.
     *
     * @param firstname the firstname
     * @param lastname the lastname
     * @param username the username
     * @param passwordHash the password hash
     */
    public Employee(String firstname, String lastname, String username, String passwordHash) {
        setFirstname(firstname);
        setLastname(lastname);
        setUsername(username);
        setPasswordHash(passwordHash);
    }

    /**
     * Gets the insurance number.
     *
     * @return the insurance number
     */
    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    /**
     * Sets the insurance number.
     *
     * @param insuranceNumber the new insurance number
     */
    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the permission.
     *
     * @param permission the new permission
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Gets the employed since.
     *
     * @return the employed since
     */
    public Date getEmployedSince() {
        return employedSince;
    }

    /**
     * Sets the employed since.
     *
     * @param employedSince the new employed since
     */
    public void setEmployedSince(Date employedSince) {
        this.employedSince = employedSince;
    }

    /**
     * Gets the fail counter of login
     *
     * @return number of failed login
     */
    public Integer getLoginFailcount() {
        return loginFailCount;
    }

    /**
     * Set the fail counter of login
     *
     * @param loginFailCount number of failed login
     */
    public void setLoginFailcount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }


    /**
     * Gets the password hash.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash.
     *
     * @param passwordHash the new password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
