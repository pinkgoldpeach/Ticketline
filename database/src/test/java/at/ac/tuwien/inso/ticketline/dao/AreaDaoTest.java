package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 18/05/2016.
 */
public class AreaDaoTest extends AbstractDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ShowDao showDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findOne() {
        Area area = areaDao.findOne(1);
        assertNotNull(area);
        assertEquals("Check findOne on first element", 1, (int) area.getId());
    }

    @Test
    public void findAll() {
        List<Area> areas = areaDao.findAll();
        assertEquals("Check findAll for 3 Elements", 3, areas.size());
    }

    @Test
    public void findAllPageable() {
        List<Area> areas = areaDao.findAllPageable(new PageRequest(0, 2)).getContent();
        assertEquals("Check findAll - is 2 first", 2, areas.size());
    }

    @Test
    public void findByRoom() {
        Room room = roomDao.findOne(4);
        List<Area> areas = areaDao.findByRoom(room);
        assertEquals("Check findByRoom for 3 Elements", 3, areas.size());

        room = roomDao.findOne(1);
        areas = areaDao.findByRoom(room);
        assertEquals("Check findByRoom for 0 Elements", 0, areas.size());
    }

    @Test
    public void findByShow() {
        Show show = showDao.findOne(3);
        List<Area> areas = areaDao.findByShow(show);
        assertEquals("Check findByRoom for 3 Elements", 3, areas.size());
        show = showDao.findOne(1);
        areas = areaDao.findByShow(show);
        assertEquals("Check findByRoom for 0 Elements", 0, areas.size());
    }

}
