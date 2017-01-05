package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.StripePayment;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;

/**
 * Service for Stripe Payment
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface StripeService {

    /**
     * Charges a CC by the client side created Token
     *
     * @param amount      to charge
     * @param description for payment
     * @param stripe       card-token
     * @return created charge
     * @throws ServiceException on error, containing Stripe error message
     */
    Charge charge(int amount, String description, StripePayment stripe) throws ServiceException;

    /**
     * Save stripePayment object in database
     *
     * @param stripePayment object to be saved
     * @return saved object
     * @throws ServiceException on error
     */
    StripePayment save(StripePayment stripePayment) throws ServiceException;


    /**
     * Refund the whole payment or a certain amount
     *
     * @param amount which will be refunded
     * @param cancellationReason reason for cancellation
     * @param stripePayment object to be refunded
     * @return refund object if it was successful
     * @throws ServiceException on error
     */
    Refund refund(int amount, String cancellationReason, StripePayment stripePayment) throws ServiceException;

}
