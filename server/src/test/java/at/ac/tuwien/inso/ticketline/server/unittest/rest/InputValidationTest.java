package at.ac.tuwien.inso.ticketline.server.unittest.rest;

import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.rest.InputValidation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class InputValidationTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private InputValidation inputValidation;

    @Before
    public void setUp() throws Exception {
        inputValidation = new InputValidation();
    }

    @Test
    public void checkLettersNoSpecial() {
        try {
            inputValidation.checkLettersNoSpecial("abcdefghijklmnopqrstuvw");
            inputValidation.checkLettersNoSpecial("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            inputValidation.checkLettersNoSpecial("0123456789");
            inputValidation.checkLettersNoSpecial("A1b2C3");
            inputValidation.checkLettersNoSpecial("input String");
        } catch (ValidationException e) {
            fail("Validation exception was thrown for regular characters: " + e.getMessage());
        }
    }

    @Test
    public void checkLettersNoSpecial_Fail_EmptyString() throws Exception {
        exception.expect(ValidationException.class);
        inputValidation.checkLettersNoSpecial("");
    }

    @Test
    public void checkLettersNoSpecial_Fail_SpecialChars() {
        try {
            inputValidation.checkLettersNoSpecial(" ");
            fail("Blank  passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkLettersNoSpecial("ä");
            fail("Umlauts passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkLettersNoSpecial("$");
            fail("Special character passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkLettersNoSpecial(null);
            fail("Null passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkLettersNoSpecial("");
            fail("Empty String passed validation");
        } catch (ValidationException e) {
        }
    }

    @Test
    public void checkUsername() {
        try {
            inputValidation.checkUsername("username");
            inputValidation.checkUsername("userName");
            inputValidation.checkUsername("user123");
            inputValidation.checkUsername("9999");
        } catch (ValidationException e) {
            fail("Validation exception was thrown for valid username: " + e.getMessage());
        }
    }

    @Test
    public void checkUsername_Fail_SpecialChars() throws Exception {
        try {
            inputValidation.checkUsername(" ");
            fail("Blank symbol passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkUsername("user name");
            fail("Username with blank passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkUsername("user_name");
            fail("Username with underscore passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkUsername("ä");
            fail("Username with special character passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkUsername(null);
            fail("Null passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkUsername("");
            fail("Empty String passed validation");
        } catch (ValidationException e) {
        }
    }

    @Test
    public void checkEMail() {
        try {
            inputValidation.checkEMail("a@b.com");
            inputValidation.checkEMail("abcdefg_123@t99av.de");
        } catch (ValidationException e) {
            fail("Validation exception was thrown for regular email address: " + e.getMessage());
        }
    }

    @Test
    public void checkEMail_Fail_Invalid() {
        try {
            inputValidation.checkEMail("a@b.x");
            fail("Too short TLD passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkEMail("abc");
            fail("Invalid email address passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkEMail("abc@abc");
            fail("Invalid email address passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkEMail("@abc.com");
            fail("Invalid email address passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkEMail(null);
            fail("Null passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkEMail("");
            fail("Empty String passed validation");
        } catch (ValidationException e) {
        }
    }

    @Test
    public void checkLettersWithSpecials() {
        try {
            inputValidation.checkLettersWithSpecials(" ");
            inputValidation.checkLettersWithSpecials(".,-*#'!^?/");
        } catch (ValidationException e) {
            fail("Validation exception was thrown for allowed characters: " + e.getMessage());
        }
    }

    @Test
    public void checkLettersWithSpecials_Fail() {
        try {
            inputValidation.checkLettersWithSpecials("äöü");
        } catch (ValidationException e) {
            fail("Umlauts passed validation");
        }

        try {
            inputValidation.checkLettersWithSpecials(null);
            fail("Null passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkLettersWithSpecials("");
            fail("Empty String passed validation");
        } catch (ValidationException e) {
        }
    }

    @Test
    public void checkId() throws ValidationException {
        inputValidation.checkId(1);
        inputValidation.checkId(99);

        exception.expect(ValidationException.class);
        inputValidation.checkId(0);
    }

    @Test
    public void checkPageNumber() throws ValidationException {
        inputValidation.checkPageNumber(0);
        inputValidation.checkPageNumber(1);
        inputValidation.checkPageNumber(99);

        exception.expect(ValidationException.class);
        inputValidation.checkPageNumber(-1);
    }

    @Test
    public void checkDuration() throws ValidationException {
        inputValidation.checkDuration(1);
        inputValidation.checkDuration(99);

        exception.expect(ValidationException.class);
        inputValidation.checkDuration(0);
    }

    @Test
    public void checkPrice() throws ValidationException {
        inputValidation.checkPrice(0.0);
        inputValidation.checkPrice(99.99);

        exception.expect(ValidationException.class);
        inputValidation.checkPrice(-1.0);
    }

    @Test
    public void checkDate() throws ValidationException {
        inputValidation.checkDate("1985-01-01");
        inputValidation.checkDate("9999-99-99");


    }

    @Test
    public void checkDate_Fail() {
        try {
            inputValidation.checkDate("9999-99-990");
            fail("Invalid date passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkDate("9999-99-");
            fail("Invalid date passed validation");
        } catch (ValidationException e) {
        }
    }


    @Test
    public void checkTime() throws ValidationException {
        inputValidation.checkTime("12:34:56");
        inputValidation.checkTime("99:99:99");
        inputValidation.checkTime("00:00:00");

        exception.expect(ValidationException.class);
        inputValidation.checkTime("1:2:3");
    }


    @Test
    public void checkDateTime() throws ValidationException {
        inputValidation.checkDateTime("1985-01-01 12:34:56");
        inputValidation.checkDateTime("9999-99-99 99:99:99");
        inputValidation.checkDateTime("0000-00-00 00:00:00");
    }

    @Test
    public void checkDateTime_Fail() {
        try {
            inputValidation.checkDateTime("1985-01-01");
            fail("Missing time passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkDateTime("12:34:56");
            fail("Missing date passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkDateTime("1985-01-0112:34:56");
            fail("Missing blank passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkDateTime(null);
            fail("Null passed validation");
        } catch (ValidationException e) {
        }

        try {
            inputValidation.checkDateTime("");
            fail("Empty String passed validation");
        } catch (ValidationException e) {
        }
    }


}