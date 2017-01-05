package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Hannes on 06.05.2016.
 *
 *
 * This class generates data for customers
 *
 * @see at.ac.tuwien.inso.ticketline.model.Customer
 */
@Component
public class CustomerGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGenerator.class);

    @Autowired
    private CustomerDao dao;

    /**
     * {@inheritDoc}
     */
    public void generate() {
        LOGGER.info("+++++ Generate Customer Data +++++");

        //Anonymous customer
        String city = "-";
        String country = "-";
        String postalcode = "-";
        String street = "-";
        Date dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000)).getTime();
        String email = "anonym@anonym.an";
        String firstname = "Anonymous";
        String lastname = "Customer";
        String phonenumber ="-";
        CustomerStatus status = CustomerStatus.VALID;

        Address address1 = new Address();
        address1.setCity("-");
        address1.setStreet("-");
        address1.setCountry("-");
        address1.setPostalCode("-");

        Customer c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Lanzenkirchen";
        country = "Austria";
        postalcode = "10343";
        street = "Mannheimgasse";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+234)).getTime();
        email = "@yello.at";
        firstname = "Sarah";
        lastname = "Lang";
        phonenumber ="0660 39234234";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Flussland";
        country = "Austria";
        postalcode = "756756";
        street = "Schubertusstraße 23";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+4544)).getTime();
        email = "@gmx.at";
        firstname = "Waltrude";
        lastname = "Minkl";
        phonenumber ="0660 23424574";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Waldheim";
        country = "Austria";
        postalcode = "2345";
        street = "Schubertstraße 23";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+454)).getTime();
        email = "@gmx.at";
        firstname = "Waltrude";
        lastname = "Glatz";
        phonenumber ="0660 23424574";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Siebenboten";
        country = "Austria";
        postalcode = "3456";
        street = "Ganz Lange Straße 234";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+4454)).getTime();
        email = "@yahoo.at";
        firstname = "Simone";
        lastname = "Landl";
        phonenumber ="0664 345634";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Tullenheim";
        country = "Deutschland";
        postalcode = "45657";
        street = "Samosalinde 8";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+6787)).getTime();
        email = "@gmx.de";
        firstname = "Mona";
        lastname = "Schandl-Sied";
        phonenumber ="066343423";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Manchester";
        country = "USA";
        postalcode = "NY 65455";
        street = "Fifth Avenue 34";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+878)).getTime();
        email = "@gmail.com";
        firstname = "John";
        lastname = "Smith";
        phonenumber ="01 234 345633";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Berlin";
        country = "Deutschland";
        postalcode = "345455";
        street = "Simmeringweg 3/23";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+566)).getTime();
        email = "@gmail.de";
        firstname = "Birgit";
        lastname = "Hindl";
        phonenumber ="0660 345 34534";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "München";
        country = "Deutschland";
        postalcode = "78700";
        street = "Hirterweg 4";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+5676)).getTime();
        email = "@gmail.com";
        firstname = "Hannes";
        lastname = "Pfeiffer";
        phonenumber ="0664 234 34134";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Linz";
        country = "Austria";
        postalcode = "34234";
        street = "Schindlerstraße 2";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+76)).getTime();
        email = "@yahoo.com";
        firstname = "Patrick";
        lastname = "Weißkirchner";
        phonenumber ="0699 345633";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Klagenfurt";
        country = "Austria";
        postalcode = "20088";
        street = "Sandgasse 45";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+23)).getTime();
        email = "@aon.at";
        firstname = "Christoph";
        lastname = "Latsch";
        phonenumber ="0664 345633";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Graz";
        country = "Austria";
        postalcode = "20088";
        street = "Sandgasse 45";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+233)).getTime();
        email = "@aon.at";
        firstname = "Christoph";
        lastname = "Hafner";
        phonenumber ="0664 345633";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Winzersdorf";
        country = "Austria";
        postalcode = "234088";
        street = "Welttesterweg 42";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+2342)).getTime();
        email = "@aon.at";
        firstname = "Alexander";
        lastname = "Poschenreithner";
        phonenumber ="0699 4545555";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.MALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Bali";
        country = "Indonesia";
        postalcode = "3444 23";
        street = "Schi hewe weg 3";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+4234)).getTime();
        email = "@binmalweg.at";
        firstname = "Carla";
        lastname = "Jancik";
        phonenumber ="063232323";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        city = "Maplewood";
        country = "USA";
        postalcode = "10334";
        street = "Applestreet 23";
        dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+3423)).getTime();
        email = "@aon.at";
        firstname = "Bettina";
        lastname = "Schlager";
        phonenumber ="01 52 234352";
        status = CustomerStatus.VALID;

        address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        c1 = new Customer();
        c1.setGender(Gender.FEMALE);
        c1.setFirstname(firstname);
        c1.setLastname(lastname);
        c1.setEmail(firstname+"."+lastname+""+email);
        c1.setDateOfBirth(dateofbirth);
        c1.setAddress(address1);
        c1.setCustomerStatus(status);
        dao.save(c1);

        // ---------------------------

        for(int i = 1; i < 20; i++){
            city = "Vienna";
            country = "Austria";
            postalcode = "343";
            street = "JO Street ";
            dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+i)).getTime();
            email = "@sample.sa";
            firstname = "Max";
            lastname = "Mustermann";
            phonenumber ="0660 34323";
            status = CustomerStatus.VALID;

            address1 = new Address();
            address1.setCity(city);
            address1.setStreet(street + i);
            address1.setCountry(country);
            address1.setPostalCode(postalcode + i);

            c1 = new Customer();
            c1.setGender(Gender.MALE);
            c1.setFirstname(firstname+i);
            c1.setLastname(lastname+i);
            c1.setEmail(i+email);
            c1.setDateOfBirth(dateofbirth);
            c1.setAddress(address1);
            c1.setCustomerStatus(status);
            dao.save(c1);

        }

        for(int i = 1; i < 20; i++){
            city = "Berlin";
            country = "Germany";
            postalcode = "345435";
            street = "Witzgasse ";
            dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+i)).getTime();
            email = "@sample.de";
            firstname = "Lisa";
            lastname = "Smith";
            phonenumber ="0660 4554345345";
            status = CustomerStatus.VALID;

            address1 = new Address();
            address1.setCity(city);
            address1.setStreet(street + i);
            address1.setCountry(country);
            address1.setPostalCode(postalcode + i);

            c1 = new Customer();
            c1.setGender(Gender.FEMALE);
            c1.setFirstname(firstname+i);
            c1.setLastname(lastname+i);
            c1.setEmail(i+email);
            c1.setDateOfBirth(dateofbirth);
            c1.setAddress(address1);
            c1.setCustomerStatus(status);
            dao.save(c1);

        }

        for(int i = 1; i < 20; i++){
            city = "London";
            country = "UK";
            postalcode = "4345";
            street = "Bakerstreet ";
            dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+i)).getTime();
            email = "@yahoo.com";
            firstname = "Lucas";
            lastname = "Skyrunner";
            phonenumber ="03434 2343";
            status = CustomerStatus.VALID;

            address1 = new Address();
            address1.setCity(city);
            address1.setStreet(street + i);
            address1.setCountry(country);
            address1.setPostalCode(postalcode + i);

            c1 = new Customer();
            c1.setGender(Gender.MALE);
            c1.setFirstname(firstname+i);
            c1.setLastname(lastname+i);
            c1.setEmail(i+email);
            c1.setDateOfBirth(dateofbirth);
            c1.setAddress(address1);
            c1.setCustomerStatus(status);
            dao.save(c1);

        }
    }
}
