package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 01/06/2016.
 */
public class LocationServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private LocationService service;

    @Test
    public void testGetPageOfLocations(){
        try {
            List<Location> locations = service.getPageOfLocations(0, 3);
            assertEquals(3, locations.size());
            locations = service.getPageOfLocations(0, 2);
            assertEquals(2, locations.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetLocationsByName(){
        try {
            Location location = new Location();
            location.setName("rosses Festspielhaus");
            List<Location> locations = service.getLocationsByName(location);
            assertEquals(1, locations.size());
            location.setName("Hall");
            locations = service.getLocationsByName(location);
            assertEquals(2, locations.size());
            location.setName("Kino");
            locations = service.getLocationsByName(location);
            assertEquals(0, locations.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetLocationsByShow(){
        try {
            Show show = new Show();
            show.setId(1);
            List<Location> locations = service.getLocationsByShow(show);
            assertEquals(1, locations.size());
            show.setId(2);
            locations = service.getLocationsByShow(show);
            assertEquals(1, locations.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetLocationsByStreet(){
        try {
            Address address = new Address();
            address.setStreet("Guglgasse 8");
            List<Location> locations = service.getLocationsByStreet(address);
            assertEquals(1, locations.size());
            address.setStreet("Treitlstr 3");
            locations = service.getLocationsByStreet(address);
            assertEquals(0, locations.size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLocationsByCountry(){
        try {
            Address address = new Address();
            address.setCountry("Austria");
            List<Location> locations = service.getLocationsByCountry(address);
            assertEquals(2, locations.size());
            address.setCountry("USA");
            locations = service.getLocationsByCountry(address);
            assertEquals(0, locations.size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLocationsByPostalCode(){
        try {
            Address address = new Address();
            address.setPostalCode("1110");
            List<Location> locations = service.getLocationsByPostalCode(address);
            assertEquals(1, locations.size());
            address.setPostalCode("0");
            locations = service.getLocationsByPostalCode(address);
            assertEquals(2, locations.size());
            address.setPostalCode("XY123");
            locations = service.getLocationsByPostalCode(address);
            assertEquals(0, locations.size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLocationsByCity(){
        try {
            Address address = new Address();
            address.setCity("Salzburg");
            List<Location> locations = service.getLocationsByCity(address);
            assertEquals(1, locations.size());
            address.setCity("Tel Aviv");
            locations = service.getLocationsByPostalCode(address);
            assertEquals(0, locations.size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
