package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimplePerformanceService;
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
public class PerformanceServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimplePerformanceService service = null;
    private List<Performance> performances = null;

    @Before
    public void setUp() throws Exception {
        service = new SimplePerformanceService();
        performances = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Performance p = new Performance(i, "Performance" + i, "Description" + i, ((i + 1) * 10), PerformanceType.CONCERT);
            performances.add(p);
        }
    }

    @Test
    public void getPerformanceByArtist() {
        Artist a = new Artist();
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findPerformanceByArtist(a)).thenReturn(performances);
        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getPerformanceByArtist(a).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByArtist_NoDb() throws Exception {
        Artist a = new Artist();
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findPerformanceByArtist(a)).thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByArtist(a);
    }

    @Test
    public void getPerformanceByArtist_EmptyArtist() throws Exception {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findPerformanceByArtist(null)).thenThrow(new RuntimeException("Null instead of Artist"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByArtist(null);
    }

    @Test
    public void getAllPerformances() {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findAllOrderByNameAsc()).thenReturn(performances);
        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getAllPerformances().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getAllPerformances_NoDb() throws Exception {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findAllOrderByNameAsc()).thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getAllPerformances();
    }


    @Test
    public void getPerformanceByDescription() {
        String descr = "test";
        Performance p = new Performance();
        p.setDescription(descr);

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByDescription(descr)).thenReturn(performances);
        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getPerformanceByDescription(p).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByDescription_NoDb() throws Exception {
        String descr = "test";
        Performance p = new Performance();
        p.setDescription(descr);

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByDescription(descr)).thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByDescription(p);
    }

    @Test
    public void getPerformanceByDuration() {
        Performance p = new Performance();
        p.setDuration(1234);

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(
                dao.findByDurationBetween(
                        any(Integer.class),
                        any(Integer.class)
                )
        ).thenReturn(performances);
        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getPerformanceByDuration(p).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByDuration_NoDb() throws Exception {
        Performance p = new Performance();
        p.setDuration(567);

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByDurationBetween(any(Integer.class), any(Integer.class)))
                .thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByDuration(p);
    }

    @Test
    public void getPerformanceByName() {
        String nameNotFound = "Nonexistent";
        String name = "Performancename";
        Performance p = new Performance();
        p.setName(name);

        List<Performance> empty = new ArrayList<>();

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByNameLike(name)).thenReturn(performances);
        Mockito.when(dao.findByNameLike(nameNotFound)).thenReturn(empty);

        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getPerformanceByName(p).size());

            p.setName(nameNotFound);
            assertEquals(0, service.getPerformanceByName(p).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByName_NoDb() throws Exception {
        Performance p = new Performance();
        p.setName("test123");

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByNameLike(any(String.class))).thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByName(p);
    }

    @Test
    public void getPerformanceByPerformanceType() {
        Performance p = new Performance();
        p.setPerformanceType(PerformanceType.CONCERT);

        List<Performance> empty = new ArrayList<>();

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByPerformanceType(PerformanceType.CONCERT)).thenReturn(performances);
        Mockito.when(dao.findByPerformanceType(PerformanceType.FESTIVAL)).thenReturn(empty);

        service.setPerformanceDao(dao);

        try {
            assertEquals(5, service.getPerformanceByPerformanceType(p).size());

            p.setPerformanceType(PerformanceType.FESTIVAL);
            assertEquals(0, service.getPerformanceByPerformanceType(p).size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByPerformanceType_NoDb() throws Exception {
        Performance p = new Performance();
        p.setPerformanceType(PerformanceType.MUSICAL);

        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.findByPerformanceType(any(PerformanceType.class))).thenThrow(new RuntimeException("No DB"));
        service.setPerformanceDao(dao);

        exception.expect(ServiceException.class);
        service.getPerformanceByPerformanceType(p);
    }

    @Test
    public void getPageCount() throws Exception {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setPerformanceDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setPerformanceDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        PerformanceDao dao = Mockito.mock(PerformanceDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setPerformanceDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}