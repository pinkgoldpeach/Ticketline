package at.ac.tuwien.inso.ticketline.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The receipt entity.
 */
@Entity
public class Receipt implements Serializable {

    private static final long serialVersionUID = 7599696540775084248L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false, length = 50)
    private String performanceName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionState transactionState;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "methOfPay_id", nullable = false)
    private MethodOfPayment methodOfPayment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<ReceiptEntry> receiptEntries;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the transaction date.
     *
     * @return the transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     *
     * @param transactionDate the new transaction date
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the transaction state.
     *
     * @return the transaction state
     */
    public TransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the transaction state.
     *
     * @param transactionState the new transaction state
     */
    public void setTransactionState(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the employee.
     *
     * @return the employee
     */
    public Employee getEmployee() {
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
    
    /**
     * Gets the method of payment.
     *
     * @return the method of payment
     */
    public MethodOfPayment getMethodOfPayment() {
        return methodOfPayment;
    }

    /**
     * Sets the method of payment.
     *
     * @param methodOfPayment the new method of payment
     */
    public void setMethodOfPayment(MethodOfPayment methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    /**
     * Gets the receipt entry.
     *
     * @return the receipt entry
     */
    public List<ReceiptEntry> getReceiptEntries() {
        return receiptEntries;
    }

    /**
     * Sets the receipt entry.
     *
     * @param receiptEntries the new receipt entry
     */
    public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
        this.receiptEntries = receiptEntries;
    }


    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }
}
