package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.Entity;

/**
 * The cash entity
 */
@Entity
public class Cash extends MethodOfPayment {

    private static final long serialVersionUID = 6325243180024990430L;

    public Cash() {
        setMethodOfPaymentType(MethodOfPaymentType.CASH);
    }
}
