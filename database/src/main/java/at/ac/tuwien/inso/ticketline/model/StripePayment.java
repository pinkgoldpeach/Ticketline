package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * The stripe entity
 */
@Entity
public class StripePayment extends MethodOfPayment {

    private static final long serialVersionUID = 2145243180024990430L;

    @Transient
    private String token;

    @Column(length = 50)
    private String charge;

    public StripePayment(){
        setMethodOfPaymentType(MethodOfPaymentType.STRIPE);
    }

    public StripePayment(String token) {
        this();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }
}
