package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Carla on 17/05/2016.
 */
public class LocationDaoTest extends AbstractDaoTest {

    @Autowired
    private LocationDao locationDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findOne() {
        Location location = locationDao.findOne(1);
        assertNotNull(location);
        assertEquals("Check findOne on first element", 1, (int) location.getId());

    }

    @Test
    public void findAll() {
        List<Location> locations = locationDao.findAll();
        assertEquals("Check findAll on 2 elements", 2,  locations.size());

    }

    @Test
    public void findByName() {
        List<Location> locations = locationDao.findByName("Planet");
        assertEquals("Check findByName on 1 element", 1,  locations.size());
        List<Location> locations2 = locationDao.findByName("Banana rama");
        assertEquals("Check findByName on 0 element", 0,  locations2.size());
    }

    @Test
    public void findByStreet() {
        Address address = new Address();
        address.setStreet("1 Graces Alley");
        List<Location> locations = locationDao.findByStreet(address);
        assertEquals("Check findByStreet on 1 element", 1,  locations.size());
    }

    @Test
    public void findByPostalCode() {
        Address address = new Address();
        address.setPostalCode("1");
        List<Location> locations = locationDao.findByPostalCode(address);
        assertEquals("Check findByPostalCode on 2 element", 2,  locations.size());

        address.setPostalCode("111");
        locations = locationDao.findByPostalCode(address);
        assertEquals("Check findByPostalCode on 1 element", 1, locations.size());
    }

    @Test
    public void findByCity() {
        Address address = new Address();
        address.setCity("london");
        List<Location> locations = locationDao.findByCity(address);
        assertEquals("Check findByCity on 1 element", 1,  locations.size());

        address.setCity("new york");
        locations = locationDao.findByCity(address);
        assertEquals("Check findByCity on 0 element", 0, locations.size());
    }

    @Test
    public void findByCountry() {
        Address address = new Address();
        address.setCountry("austr");
        List<Location> locations = locationDao.findByCountry(address);
        assertEquals("Check findByCountry on 1 element", 1,  locations.size());

        address.setCountry("usa");
        locations = locationDao.findByCountry(address);
        assertEquals("Check findByCountry on 0 element", 0, locations.size());

    }

    @Test
    public void findByShow(){
        Show show = new Show();
        show.setId(1);
        List<Location> locations = locationDao.findByShow(show);
        assertEquals("Check findByShow on 1 element", 1, locations.size());
        assertTrue(locations.get(0).getId() == 1);
    }
}
