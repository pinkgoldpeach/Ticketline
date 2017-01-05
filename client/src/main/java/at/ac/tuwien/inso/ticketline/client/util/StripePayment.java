package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripePayment {

    private String publicKeyStripe = "pk_test_ljR3sdKmeoxtYSo4BNkDdeIl";
    private static final Logger LOGGER = LoggerFactory.getLogger(StripePayment.class);

    public Token getToken(Long creditCard, Integer cvc, Integer year, Integer month, CustomerDto customerDto) throws StripeException {
        Stripe.apiKey = publicKeyStripe;
        Map<String, Object> defaultCardParams = new HashMap<>();
        Map<String, Object> defaultTokenParams = new HashMap<>();
        defaultCardParams.put("number", creditCard);
        defaultCardParams.put("exp_month", month);
        defaultCardParams.put("exp_year", year);
        defaultCardParams.put("cvc", cvc);
        defaultCardParams.put("name", customerDto.getFirstName() + " " + customerDto.getLastName());
        defaultCardParams.put("address_line1", customerDto.getAddress().getStreet());
        defaultCardParams.put("address_city", customerDto.getAddress().getCity());
        defaultCardParams.put("address_zip", customerDto.getAddress().getPostalCode());
        defaultCardParams.put("address_country", customerDto.getAddress().getCountry());
        defaultTokenParams.put("card", defaultCardParams);

        Token createdToken = Token.create(defaultTokenParams);
        LOGGER.info("Got following stripe token: " + createdToken.getId());
        return createdToken;
    }
}
