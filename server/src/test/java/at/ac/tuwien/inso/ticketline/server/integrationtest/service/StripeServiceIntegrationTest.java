package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.StripePayment;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleStripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Patrick Weiszkirchner
 */
public class StripeServiceIntegrationTest extends AbstractServiceIntegrationTest {

    private SimpleStripeService stripeService;
    private String stripeSecretKey = "sk_test_RmFC4C48Jx0BqgFL278x1f2j";
    private String stripePublicKey = "pk_test_ljR3sdKmeoxtYSo4BNkDdeIl";
    private String stripeCurrency = "eur";

    public Map<String, Object> getToken(Long creditCard, Integer cvc, Integer year, Integer month){
        Stripe.apiKey = "pk_test_ljR3sdKmeoxtYSo4BNkDdeIl";
        Map<String, Object> defaultCardParams = new HashMap<>();
        Map<String, Object> defaultTokenParams = new HashMap<>();
        defaultCardParams.put("number", creditCard);
        defaultCardParams.put("exp_month", month);
        defaultCardParams.put("exp_year", year);
        defaultCardParams.put("cvc", cvc);
        defaultCardParams.put("name", "Testuser");
        defaultTokenParams.put("card", defaultCardParams);

        return defaultTokenParams;
    }

    @Test
    public void makeStripePayment(){
        try {
            Token createdToken = Token.create(getToken(4242424242424242L, 123, 2020, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys(stripeSecretKey, stripePublicKey, stripeCurrency);

            assertNotNull(stripeService.charge(2000, "testStripePayment", stripePayment)); // 2000 means 20,00€

        } catch (StripeException e) {
            fail("StripeException thrown: " + e.getMessage());
        } catch (ServiceException se){
            fail("ServiceException thrown: " + se.getMessage());
        }

    }

    @Test
    public void makeStripePaymentandFullRefund(){
        try {
            Token createdToken = Token.create(getToken(4242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys(stripeSecretKey, stripePublicKey, stripeCurrency);

            stripePayment.setCharge(stripeService.charge(15000, "testStripePayment", stripePayment).getId());

            assertNotNull(stripeService.refund(15000, "TestFullRefund", stripePayment));

        } catch (StripeException e) {
            fail("StripeException thrown: " + e.getMessage());
        } catch (ServiceException se){
            fail("ServiceException thrown: " + se.getMessage());
        }

    }

    @Test
    public void makeStripePaymentandTooMuchRefund(){
        try {
            Token createdToken = Token.create(getToken(4242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys(stripeSecretKey, stripePublicKey, stripeCurrency);

            stripePayment.setCharge(stripeService.charge(15000, "testStripePayment", stripePayment).getId());

            stripeService.refund(150000, "TestFullRefund", stripePayment);

            fail("Service Exception not thrown because of too high refund amount");

        } catch (StripeException e) {
            fail("StripeException thrown: " + e.getMessage());

        } catch (ServiceException se){
        }

    }

    @Test
    public void makeStripePaymentandPartialRefunds(){
        try {
            Token createdToken = Token.create(getToken(4242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys(stripeSecretKey, stripePublicKey, stripeCurrency);

            stripePayment.setCharge(stripeService.charge(10000, "testStripePayment", stripePayment).getId());

            assertNotNull(stripeService.refund(1500, "TestFullRefund", stripePayment));
            assertNotNull(stripeService.refund(3500, "TestFullRefund", stripePayment));
            assertNotNull(stripeService.refund(2500, "TestFullRefund", stripePayment));


        } catch (StripeException e) {
            fail("StripeException thrown: " + e.getMessage());
        } catch (ServiceException se){
            fail("ServiceException thrown: " + se.getMessage());
        }

    }

    @Test
    public void makeStripePaymentWithFalseCreditCardNumber(){
        try {
            Token createdToken = Token.create(getToken(1242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());
            fail("StripeException not thrown");

        } catch (StripeException e) {
        }

    }

    @Test
    public void makeStripePaymentWithFalseExpiresDate(){
        try {
            Token createdToken = Token.create(getToken(1242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys(stripeSecretKey, stripePublicKey, stripeCurrency);

            stripePayment.setCharge(stripeService.charge(10000, "testStripePayment", stripePayment).getId());

            fail("StripeException not thrown because of false Expires Date of the credit card");

        } catch (StripeException e) {
        } catch (ServiceException se){
            fail("ServiceException thrown: " + se.getMessage());
        }

    }

    @Test
    public void makeStripePaymentWithFalseSecretKey(){
        try {
            Token createdToken = Token.create(getToken(1242424242424242L, 123, 2045, 12));

            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken(createdToken.getId());

            stripeService = new SimpleStripeService();
            stripeService.setStripeKeys("sk_test_falseKey", stripePublicKey, stripeCurrency);

            stripePayment.setCharge(stripeService.charge(10000, "testStripePayment", stripePayment).getId());

            fail("StripeException not thrown because of false secret Key");

        } catch (StripeException e) {
        } catch (ServiceException se){
            fail("ServiceException thrown: " + se.getMessage());
        }

    }

}

