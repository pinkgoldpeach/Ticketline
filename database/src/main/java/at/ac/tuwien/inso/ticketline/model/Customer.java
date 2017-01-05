package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The customer entity.
 */
@Entity
public class Customer extends Person {

    private static final long serialVersionUID = 4192214529072911981L;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Receipt> receipts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reservation> reservations;

    /**
     * Instantiates a new customer.
     */
    public Customer() {
    }

    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param gender the gender
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param dateOfBirth the date of birth
     * @param customerStatus the customer status
     */
    public Customer(Integer id, Gender gender, String firstname, String lastname, String email, Date dateOfBirth, CustomerStatus customerStatus) {
        super(id, gender, firstname, lastname, email, dateOfBirth);
        this.customerStatus = customerStatus;
    }

    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param gender the gender
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param dateOfBirth the date of birth
     * @param address the address
     * @param customerStatus the customer status
     */
    public Customer(Integer id, Gender gender, String firstname, String lastname, String email, Date dateOfBirth, Address address, CustomerStatus customerStatus) {
        super(id, gender, firstname, lastname, email, dateOfBirth, address);
        this.customerStatus = customerStatus;
    }

    /**
     * Gets the customer status.
     *
     * @return the customer status
     */
    public CustomerStatus getCustomerStatus() {
		return customerStatus;
	}

	/**
	 * Sets the customer status.
	 *
	 * @param customerStatus the new customer status
	 */
	public void setCustomerStatus(CustomerStatus customerStatus) {
		this.customerStatus = customerStatus;
	}

    /**
     * Gets the receipts.
     *
     * @return the receipts
     */
    public List<Receipt> getReceipts() {
        return receipts;
    }

    /**
     * Sets the receipts.
     *
     * @param receipts the new receipts
     */
    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    /**
     * Gets the reservations.
     *
     * @return the reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations.
     *
     * @param reservations the new reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
