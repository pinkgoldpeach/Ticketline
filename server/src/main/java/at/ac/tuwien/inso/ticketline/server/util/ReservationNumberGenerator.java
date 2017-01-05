package at.ac.tuwien.inso.ticketline.server.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ReservationNumberGenerator {

    /**
     * Allows all Characters and Numbers except 0, 1, O and I
     */
    final public static String REGEX_READABLE_CHARS = "^(?!.*?(?:O|1|0|I)).*$";

    /**
     * Generates a secure random string mixture of uppercase letters and digits with the length of 6
     *
     * @return random string with length 6
     */
    public static final String generateString() {
        SecureRandom secureRandom = new SecureRandom();
        String generated;

        do {
            generated = String.valueOf(new BigInteger(130, secureRandom).toString(32).toUpperCase().substring(5, 11));
        } while (!generated.matches(REGEX_READABLE_CHARS));

        return generated;
    }

}
