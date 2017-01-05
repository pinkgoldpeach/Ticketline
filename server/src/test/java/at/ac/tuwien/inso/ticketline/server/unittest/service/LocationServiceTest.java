package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleLocationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class LocationServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleLocationService service = null;
    private List<Location> locations = null;

    @Before
    public void setUp() throws Exception {
        service = new SimpleLocationService();
        locations = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Location l = new Location(
                    "LocationName" + i,
                    "LocDescription" + i,
                    "LocOwner" + i,
                    new Address("LocStreet" + i, "LocPostCode" + i, "LocCity" + i, "LocCountry" + i)
            );
            l.setId(i);
            locations.add(l);
        }
    }

    @Test
    public void getLocationsByName() {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.findByName(any(String.class))).thenReturn(locations);
        service.setLocationDao(dao);

        Location l = new Location();
        l.setName("LocationName1");

        try {
            assertEquals(5, service.getLocationsByName(l).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void getLocationsByName_NoDb() throws Exception {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.findByName(any(String.class))).thenThrow(new RuntimeException("No DB"));
        service.setLocationDao(dao);

        Location l = new Location();
        l.setName("LocationName1");

        exception.expect(ServiceException.class);
        service.getLocationsByName(l);
    }

    @Test
    public void getLocationsByCity() {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.findByCity(any(Address.class))).thenReturn(locations);
        service.setLocationDao(dao);

        Address a = new Address();
        a.setCity("LocCity1");

        try {
            assertEquals(5, service.getLocationsByCity(a).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void getLocationsByCity_EmptyAddress() throws Exception {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.findByCity(any(Address.class))).thenThrow(new RuntimeException("Null instead of Address"));
        service.setLocationDao(dao);

        exception.expect(ServiceException.class);
        service.getLocationsByCity(null);
    }

    @Test
    public void getPageCount() throws Exception {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setLocationDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setLocationDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        LocationDao dao = Mockito.mock(LocationDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setLocationDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}