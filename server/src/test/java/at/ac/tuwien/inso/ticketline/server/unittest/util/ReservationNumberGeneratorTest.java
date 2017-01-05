
package at.ac.tuwien.inso.ticketline.server.unittest.util;

import at.ac.tuwien.inso.ticketline.server.util.ReservationNumberGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ReservationNumberGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void generateString_length_of_6() {
        String reservationNumber = ReservationNumberGenerator.generateString();
        assertEquals("Reservation number must be at length 6", 6, reservationNumber.length());
    }


    @Test
    public void generateString_check_for_forbidden_chars_regex() {

        ArrayList<String> toCheckFail = new ArrayList<>();
        toCheckFail.add("MHA01");
        toCheckFail.add("ABCDEFGHI");
        toCheckFail.add("IMUSTA");
        toCheckFail.add("29O91");
        toCheckFail.stream().filter(t -> t.matches(ReservationNumberGenerator.REGEX_READABLE_CHARS)).forEach(t -> {
            fail("Illegal character passed check: " + t);
        });

        ArrayList<String> toCheckPass = new ArrayList<>();
        toCheckPass.add("ABCDEFGH");
        toCheckPass.add("28395X");
        toCheckPass.add("WQ234");

        toCheckPass.stream().filter(t -> !t.matches(ReservationNumberGenerator.REGEX_READABLE_CHARS)).forEach(t -> {
            fail("Legal string failed check: " + t);
        });

    }

    @Test
    public void generateString_check_for_forbidden_chars() {
        for (int i = 0; i < 200; i++) {
            String reservationNumber = ReservationNumberGenerator.generateString();
            if (reservationNumber.contains("0") || reservationNumber.contains("O")
                    || reservationNumber.contains("I") || reservationNumber.contains("1")) {
                fail("Found forbidden character in Reservation Number");
            }
        }
    }


}