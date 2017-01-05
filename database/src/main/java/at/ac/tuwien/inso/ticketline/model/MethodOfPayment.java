package at.ac.tuwien.inso.ticketline.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The method of payment entity.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MethodOfPayment implements Serializable {

    private static final long serialVersionUID = -7609929677091471708L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MethodOfPaymentType methodOfPaymentType;


    /**
     * Instantiates a new method of payment.
     */
    public MethodOfPayment() {
    }

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
     * Gets the method of payment
     *
     * @return method of payment type
     */
    public MethodOfPaymentType getMethodOfPaymentType() {
        return methodOfPaymentType;
    }

    /**
     * Sets the method of payment type
     *
     * @param methodOfPaymentType payment method type
     */
    protected void setMethodOfPaymentType(MethodOfPaymentType methodOfPaymentType) {
        this.methodOfPaymentType = methodOfPaymentType;
    }
}
