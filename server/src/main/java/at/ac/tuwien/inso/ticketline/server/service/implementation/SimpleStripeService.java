package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.StripePaymentDao;
import at.ac.tuwien.inso.ticketline.model.StripePayment;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@Component
public class SimpleStripeService implements StripeService {

    @Autowired
    private StripePaymentDao stripePaymentDao;


    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStripeService.class);

    /**
     * STRIPE Secret & Public Key From Christoph And Patrick
     */
    @Value("${stripe.secretApiKey}")
    private String secretApiKey;

    @Value("${stripe.publishableApiKey}")
    private String publishableApiKey;

    /**
     * Currency to pay with
     */
    @Value("${stripe.currency}")
    private String chargeCurrency;

    public SimpleStripeService() {
        LOGGER.debug("Stripe API-Key: " + secretApiKey);
        LOGGER.debug("Stripe PK API-Key: " + publishableApiKey);
        LOGGER.debug("Stripe Currency: " + chargeCurrency);

        Stripe.apiKey = secretApiKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Charge charge(int amount, String description, StripePayment stripe) throws ServiceException {
        Charge charge = new Charge();

        try {
            Stripe.apiKey = secretApiKey;
            Map<String, Object> chargeWithTokenParams = new HashMap<>();
            chargeWithTokenParams.put("amount", amount); //in cent --> 1GE = 100
            chargeWithTokenParams.put("currency", chargeCurrency);
            chargeWithTokenParams.put("card", stripe.getToken());
            charge = Charge.create(chargeWithTokenParams);
            LOGGER.debug("Charge for token {} :" + charge, stripe.getToken());
        } catch (StripeException e) {
            LOGGER.error("Error for Token {} :", stripe.getToken());
            throw new ServiceException(e);
        }
        return charge;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public StripePayment save(StripePayment stripePayment) throws ServiceException {
        try {
            return stripePaymentDao.save(stripePayment);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Refund refund(int amount, String cancellationReason, StripePayment stripePayment) throws ServiceException {
        Refund refund = new Refund();

        try {
            Stripe.apiKey = secretApiKey;
            Map<String, Object> refundParams = new HashMap<String, Object>();
            refundParams.put("charge", stripePayment.getCharge());
            refundParams.put("amount", amount);
            refund = Refund.create(refundParams);

        } catch (StripeException e) {
            LOGGER.error("Error for refund charge {} :", stripePayment.getCharge());
            throw new ServiceException(e);
        }
        return refund;
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the stripe keys and currency unit
     *
     * @param secretApiKey the stripe secret key
     * @param publishableApiKey the stripe public key
     * @param chargeCurrency the stripe currency
     */
    public void setStripeKeys(String secretApiKey, String publishableApiKey, String chargeCurrency) {
        this.secretApiKey = secretApiKey;
        this.publishableApiKey = publishableApiKey;
        this.chargeCurrency = chargeCurrency;
    }

    // -------------------- For Testing purposes --------------------
}
