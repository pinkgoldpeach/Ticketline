package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 18/05/2016.
 */
public class ShowDaoTest extends AbstractDaoTest {

    @Autowired
    private ShowDao showDao;

    @Autowired
    private RowDao rowDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private LocationDao locationDao;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findAll() {
        List<Show> shows = showDao.findAll();
        assertEquals("Check findAll - is 4 first", 4, shows.size());
    }

    @Test
    public void findOne() {
        Show show = showDao.findOne(1);
        assertNotNull(show);
        assertEquals("Check findOne on first element", 1, (int) show.getId());
    }

    @Test
    public void findAllPageable() {
        List<Show> shows = showDao.findAllNotCanceledPageable(new PageRequest(0, 4)).getContent();
        assertEquals("Check findAll - is 3 first", 3, shows.size());
    }

    @Test
    public void findByDateAndTime() {
        List<Show> shows = showDao.findByDateAndTime(getUtilDate("2016-07-12 00:00:00"));
        assertEquals("Check DB findByDateAndTime - is 1 first", 1, shows.size());
        shows = showDao.findByDateAndTime(getUtilDate("2016-07-13 00:00:00"));
        assertEquals("Check DB findByDateAndTime - is 0 first", 0, shows.size());
    }

    @Test
    public void findByDateFrom() {
        List<Show> shows = showDao.findByDateFrom(getUtilDate("2016-07-09 00:00:00"));
        assertEquals("Check DB findByDateAndTime - is 3 first", 3, shows.size());
    }

    @Test
    public void findByRowPrice() {
        Row row = rowDao.findOne(1);
        List<Show> shows = showDao.findByRowPrice(row);
        assertEquals("Check DB findByRowPrice - is 4 first", 2, shows.size());

        row = rowDao.findOne(4);
        shows = showDao.findByRowPrice(row);
        assertEquals("Check DB findByRowPrice - is 2 first", 2, shows.size());
    }

    @Test
    public void findByAreaPrice() {
        Area area = areaDao.findOne(1);
        List<Show> shows = showDao.findByAreaPrice(area);
        assertEquals("Check DB findByAreaPrice - is 1 first", 1, shows.size());

        area = areaDao.findOne(3);
        shows = showDao.findByAreaPrice(area);
        assertEquals("Check DB findByAreaPrice - is 1 first", 1, shows.size());
    }

    @Test
    public void findByRoom() {
        Room room = roomDao.findOne(1);
        List<Show> shows = showDao.findByRoom(room);
        assertEquals("Check DB findByRoom - is 1 first", 1, shows.size());
        room = roomDao.findOne(4);
        shows = showDao.findByRoom(room);
        assertEquals("Check DB findByRoom - is 1 first", 1, shows.size());
    }

    @Test
    public void findByLocation() {
        Location location = locationDao.findOne(1);
        List<Show> shows = showDao.findByLocation(location);
        assertEquals("Check DB findByLocation - is 2 first", 2, shows.size());
        location = locationDao.findOne(2);
        shows = showDao.findByLocation(location);
        assertEquals("Check DB findByLocation - is 1 first", 1, shows.size());
    }

    @Test
    public void findByDateRange() {
        List<Show> shows = showDao.findByDateRange(getUtilDate("2016-07-09 00:00:00"), getUtilDate("2016-07-14 00:00:00"));
        assertEquals("Check DB findByDateRange - is 3 first", 3, shows.size());
        shows = showDao.findByDateRange(getUtilDate("2016-07-10 00:00:00"), getUtilDate("2016-07-14 00:00:00"));
        assertEquals("Check DB findByDateRange - is 2 first", 2, shows.size());
    }


    public Date getUtilDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateUtil;
    }

}
