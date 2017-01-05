package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 17/05/2016.
 */
public class RoomDaoTest extends AbstractDaoTest {

    @Autowired
    private RoomDao roomDao;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findAll() {
        List<Room> rooms = roomDao.findAll();
        assertEquals("Check findAll - is 7 first", 7, rooms.size());
    }

    @Test
    public void findAllPageable() {

        List<Room> rooms = roomDao.findAllPageable(new PageRequest(0, 20)).getContent();
        assertEquals("Check findAll - is 7 first", 7, rooms.size());
        rooms = roomDao.findAllPageable(new PageRequest(0, 6)).getContent();
        assertEquals("Check findAll - is 6 first", 6, rooms.size());
    }

    @Test
    public void findOne() {
        Room room = roomDao.findOne(1);
        assertNotNull(room);
        assertEquals("Check findOne on first element", 1, (int) room.getId());

    }
}
