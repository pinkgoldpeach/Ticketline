package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.Gender;
import at.ac.tuwien.inso.ticketline.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class generates data for employees
 * 
 * @see at.ac.tuwien.inso.ticketline.model.Employee
 */
@Component
public class EmployeeGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeGenerator.class);

    @Autowired
    private EmployeeDao dao;

    @Autowired
    private PasswordEncoder encoder;

	/**
	 * {@inheritDoc}
	 */
    public void generate() {
        LOGGER.info("+++++ Generate Employee Data +++++");

        for(int i = 0; i<10; i++) {
            String firstname = "Marvin";
            String lastname = "Jones";
            String username = "marvin";
            String password = "42";
            String pwHash = this.encoder.encode(password);
            Employee e1;
            Date dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000+i)).getTime();
            Date since = GregorianCalendar.from(ZonedDateTime.now().minusDays(50)).getTime();


            String city = "EmployeeCity";
            String country = "Schlumpfhausen";
            String postalcode = "10";
            String street = "21st Jump Street";

            Address address1 = new Address();
            address1.setCity(city + i);
            address1.setStreet(street);
            address1.setCountry(country);
            address1.setPostalCode(postalcode + i);

            if(i>0)
                e1 = new Employee(firstname+i, lastname+i, username+i, pwHash);
            else
                e1 = new Employee(firstname, lastname, username, pwHash);

            e1.setDateOfBirth(dateofbirth);
            e1.setEmployedSince(since);
            e1.setAddress(address1);
            e1.setPermission(Permission.ROLE_USER);
            e1.setEmail("marvin" + i + "@hallo.to");
            if((i%2)==0)
                e1.setGender(Gender.MALE);
            else
                e1.setGender(Gender.FEMALE);


            e1.setInsuranceNumber("" + 349830 * i);

            e1.setLastLogin(since);
            dao.save(e1);

        }

        Date dateofbirth = GregorianCalendar.from(ZonedDateTime.now().minusDays(6000)).getTime();
        Date since = GregorianCalendar.from(ZonedDateTime.now().minusDays(50)).getTime();


        String city = "EmployeeCity";
        String country = "Schlumpfhausen";
        String postalcode = "10";
        String street = "21st Jump Street";

        Address address1 = new Address();
        address1.setCity(city);
        address1.setStreet(street);
        address1.setCountry(country);
        address1.setPostalCode(postalcode);

        Employee admin = new Employee("admin", "admin", "admin", this.encoder.encode("admin"));
        admin.setPermission(Permission.ROLE_ADMINISTRATOR);
        admin.setEmployedSince(since);
        admin.setDateOfBirth(dateofbirth);
        admin.setAddress(address1);
        admin.setEmail("admin@admin.at");
        admin.setGender(Gender.MALE);
        admin.setInsuranceNumber("" + 349830);
        admin.setLastLogin(since);
        dao.save(admin);
    }

}
