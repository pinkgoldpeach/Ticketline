package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.PersonDto;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 * @author Carla Jancik
 */
public class InputValidation {

    /**
     * Allows A-Z, a-z, 0-9 and blank
     */
    public static final String RegexRegularCharacters = "^[a-zA-Z0-9][a-zA-Z0-9 ]*$";


    /**
     * Allows A-Z, a-z, ß, - and umlauts
     */
    public static final String RegexNames = "^[a-zA-Z0-9äöüÄÖÜ][a-zA-Z0-9-öäüÖÄÜß]*$";

    /**
     * Allows A-Z, a-z, ß, - blanks and umlauts
     */
    public static final String RegexNamesBlanks = "^[a-zA-Z0-9äöüÄÖÜ][a-zA-Z0-9-öäüÖÄÜß ]*$";

    /**
     * Validation of E-mail address
     */
    public static final String RegexEmailAddress = "^[a-zA-Z0-9äöüßéèêáà._%+-]+@[a-zA-Z0-9äöüßéèêáà.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Allows all characters without Umlauts
     */
    public static final String RegexCharactersWithoutUmlauts = "^[a-zA-Z0-9 .,!?+'-_:;)(/&$§\"^@€<>´`][a-zA-Z0-9 .,!?+'-_:;)(/&$§\"^@€<>´`]*$";

    /**
     * Allows all characters with Umlauts
     */
    public static final String RegexCharactersWithUmlauts = "^[a-zA-Z0-9 .,!?+'-_:;)(/&$§\"^@€<>´`][a-zA-Z0-9 .,!?+'-_:;)(/&$§\"^@€<>´`)(äöüÄÖÜßéèêÉÈÊáàÁà]*$";

    /**
     * Allows Date format of YYYY-MM-DD
     */
    public static final String RegexDate = "^(\\d{4})-(\\d{2})-(\\d{2})$";

    /**
     * Allows Time format of hh:mm:ss
     */
    public static final String RegexTime = "^(\\d{2}):(\\d{2}):(\\d{2})$";

    /**
     * Allows Date time format of YYYY-MM-DD hh:mm:ss
     */
    public static final String RegexDateTime = "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$";


    /**
     * Checks if a String is not empty (""), and just contains A-Z, a-z, 0-9 and blank
     *
     * @param inputString to be validated
     * @throws ValidationException if string doesn't match pattern
     */
    public void checkLettersNoSpecial(String inputString) throws ValidationException {
        if (inputString == null || inputString.isEmpty() || !inputString.matches(RegexRegularCharacters))
            throw new ValidationException("Special letters not allowed");
    }

    /**
     * Validates the name of a person (containing umlauts, ß, double names)
     *
     * @param name to check
     * @throws ValidationException if name contains illegal symboles
     */
    public void checkNameWithUmlauts(String name) throws ValidationException {
        if (!name.matches(RegexNames)) {
            throw new ValidationException("Name of Person contains illegal symbols");
        }
    }

    /**
     * Validates the name of a person (containing umlauts, ß, double names, blanks)
     *
     * @param name to check
     * @throws ValidationException if name contains illegal symboles
     */
    public void checkNameWithUmlautsBlanks(String name) throws ValidationException {
        if (!name.matches(RegexNamesBlanks)) {
            throw new ValidationException("Name of Person contains illegal symbols");
        }
    }

    /**
     * Checks is a username contains special characters or blank
     *
     * @param inputString username to be validated
     * @throws ValidationException if string is invalid
     */
    public void checkUsername(String inputString) throws ValidationException {
        checkLettersNoSpecial(inputString);
        if (inputString.contains(" "))
            throw new ValidationException("Username not allowed");
    }

    /**
     * Validates fields of PersonDto
     *
     * @param personDto to be validated
     * @throws ValidationException on invalidity
     */
    public void checkPerson(PersonDto personDto) throws ValidationException {
        if (personDto.getId() != null)
            checkId(personDto.getId());
        checkAddress(personDto.getAddress());
        checkNameWithUmlauts(personDto.getLastName());
        checkNameWithUmlauts(personDto.getFirstName());
        checkEMail(personDto.getEmail());
        checkBirthDate(personDto.getDateOfBirth());
    }

    /**
     * Validates fields of AddressDto
     *
     * @param addressDto to be validated
     * @throws ValidationException on invlidity
     */
    public void checkAddress(AddressDto addressDto) throws ValidationException {
        checkNameWithUmlautsBlanks(addressDto.getCity());
        checkNameWithUmlauts(addressDto.getCountry());
        checkLettersWithSpecials(addressDto.getPostalCode());
        checkLettersWithSpecials(addressDto.getStreet());
    }

    /**
     * Validate if a string is a valid email address
     *
     * @param inputString string to be validated as email address
     * @throws ValidationException if string does not match email pattern
     */
    public void checkEMail(String inputString) throws ValidationException {
        if (inputString == null || inputString.isEmpty() || !inputString.matches(RegexEmailAddress))
            throw new ValidationException("Email not valid");
    }

    /**
     * Check if input string contains no umlauts
     *
     * @param inputString to be validated
     * @throws ValidationException if string contains umlauts
     */
    public void checkLettersWithSpecialsNoUmlauts(String inputString) throws ValidationException {
        if (inputString == null || inputString.isEmpty() && !inputString.matches(RegexCharactersWithoutUmlauts))
            throw new ValidationException();
    }

    /**
     * string can contain every symbol
     *
     * @param inputString to be validated
     * @throws ValidationException if string is null
     */
    public void checkLettersWithSpecials(String inputString) throws ValidationException {
        if (inputString == null || inputString.isEmpty() && !inputString.matches(RegexCharactersWithUmlauts))
            throw new ValidationException();
    }

    /**
     * Checks if an ID is a positive integer and greater than 0
     *
     * @param id to check
     * @throws ValidationException if ID is negative
     */
    public void checkId(int id) throws ValidationException {
        if (id < 1)
            throw new ValidationException();
    }

    /**
     * Checks if page number is a positive integer and greater equal than 0
     *
     * @param pageNumber to be checked
     * @throws ValidationException if negative page number
     */
    public void checkPageNumber(int pageNumber) throws ValidationException {
        if (pageNumber < 0)
            throw new ValidationException();
    }

    /**
     * Checks for positive duration and on greater 0
     *
     * @param duration to be validated
     * @throws ValidationException if duration is less than 1
     */
    public void checkDuration(int duration) throws ValidationException {
        if (duration < 1)
            throw new ValidationException();
    }

    /**
     * Checks if price is positive and greater equals 0
     *
     * @param price to be validated
     * @throws ValidationException if price is less than 0
     */
    public void checkPrice(double price) throws ValidationException {
        if (price < 0)
            throw new ValidationException();
    }

    /**
     * Validate date of format  YYYY-MM-DD
     *
     * @param date to be validated
     * @throws ValidationException if date does not fit format
     */
    public void checkDate(String date) throws ValidationException {
        if (date == null || date.trim().isEmpty() || !date.matches(RegexDate)) {
            throw new ValidationException("The date format is wrong");
        }
    }

    /**
     * Checks time of format hh:mm:ss
     *
     * @param time to be validated
     * @throws ValidationException if time does not fit format
     */
    public void checkTime(String time) throws ValidationException {
        if (time == null || time.trim().isEmpty() || !time.matches(RegexTime)) {
            throw new ValidationException("The time format is wrong");
        }
    }

    /**
     * Checks date time of format YYYY-MM-DD hh:mm:ss
     *
     * @param dateTime to be validated
     * @throws ValidationException if date time does not fit format
     */
    public void checkDateTime(String dateTime) throws ValidationException {
        if (dateTime == null || dateTime.trim().isEmpty() || !dateTime.matches(RegexDateTime)) {
            throw new ValidationException("The dateTime format is wrong");
        }
    }

    /**
     * Checks if a number is greater 0
     *
     * @param amount to be checked
     * @throws ValidationException if value is less than 1
     */
    public void checkGreaterZero(Integer amount) throws ValidationException {
        if (amount < 1) {
            throw new ValidationException("The amount must be greater than zero");
        }
    }

    /**
     * Checks the birthdate of the person
     *
     * @param date to be validated
     * @throws ValidationException if birthdate is not correct
     */
    public void checkBirthDate(Date date) throws ValidationException {
        if (date.before(GregorianCalendar.from(ZonedDateTime.now().minusYears(150)).getTime()))
            throw new ValidationException("The person is too old");

        if (date.after(GregorianCalendar.from(ZonedDateTime.now().minusYears(14)).getTime()))
            throw new ValidationException("The person is too young");
    }

    /**
     * Checks date of employment
     *
     * @param date      to be checked
     * @param birthdate birthday of employee
     * @throws ValidationException if employment date before birthday
     */
    public void checkEmployeedSinceDate(Date date, Date birthdate) throws ValidationException {
        if (date.before(birthdate))
            throw new ValidationException("The employee since date is false");

    }

}
