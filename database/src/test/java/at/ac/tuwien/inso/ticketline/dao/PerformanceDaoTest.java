package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 17/05/2016.
 */
public class PerformanceDaoTest extends AbstractDaoTest {

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private ArtistDao artistDao;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testFindAll() {
        List<Performance> performances = performanceDao.findAll();
        assertEquals("Check DB initial data - is 3 first", 3, performances.size());
    }

    @Test
    public void findOne() {
        Performance performance = performanceDao.findOne(1);
        assertNotNull(performance);
        assertEquals("Check findOne on first element", 1, (int) performance.getId());

    }

    @Test
    public void findByName(){
        List<Performance> performances = performanceDao.findByNameLike("hcp");
        assertEquals("Check DB initial data - is 1 first", 1, performances.size());
        List<Performance> performances2 = performanceDao.findByNameLike("o");
        assertEquals("Check DB initial data - is 2 first", 2, performances2.size());

    }

    @Test
    public void findByDuration(){
        List<Performance> performances = performanceDao.findByDurationBetween(70, 95);
        assertEquals("Check DB initial data - is 1 first", 1, performances.size());
        List<Performance> performances2 = performanceDao.findByDurationBetween(85, 125);
        assertEquals("Check DB initial data - is 2 first", 2, performances2.size());
    }

    @Test
    public void findByDescription(){
        List<Performance> performances = performanceDao.findByDescription("chili peppers");
        assertEquals("Check DB initial data - is 1 first", 1, performances.size());
        List<Performance> performances2 = performanceDao.findByDescription("lalala");
        assertEquals("Check DB initial data - is 0 first", 0, performances2.size());
    }

    @Test
    public void findByPerformanceType(){
        List<Performance> performances = performanceDao.findByPerformanceType(PerformanceType.CONCERT);
        assertEquals("Check DB initial data - is 2 first", 2, performances.size());
        List<Performance> performances2 = performanceDao.findByPerformanceType(PerformanceType.MOVIE);
        assertEquals("Check DB initial data - is 1 first", 1, performances2.size());
    }

    @Test
    public void findByArtistName(){
        Artist artist = new Artist();
        artist.setFirstname("Anthony");
        artist.setLastname("Kiedis");
        List<Performance> performances = performanceDao.findPerformanceByArtistName(artist);
        assertEquals("Check DB initial data - is 1 first", 1, performances.size());

    }

    @Test
    public void findByArtist(){
        Artist artist = artistDao.findOne(1);
        assertNotNull(artist);
        List<Performance> performances = performanceDao.findPerformanceByArtist(artist);
       assertEquals("Check DB initial data - is 1 first", 1, performances.size());

    }


}
