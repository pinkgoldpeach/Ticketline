package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The entry entity.
 */
@Entity
public class ReceiptEntry implements Serializable {

    private static final long serialVersionUID = 2151550295868898930L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = true)
    private Ticket ticket;

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
     * Gets the position.
     *
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets the unit price.
     *
     * @return the unit price
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price.
     *
     * @param unitPrice the new unit price
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the receipt.
     *
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Sets the receipt.
     *
     * @param receipt the new receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
