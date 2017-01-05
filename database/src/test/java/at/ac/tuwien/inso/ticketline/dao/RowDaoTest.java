package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
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
public class RowDaoTest extends AbstractDaoTest {

    @Autowired
    private RowDao rowDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ShowDao showDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findOne() {
        Row row = rowDao.findOne(1);
        assertNotNull(row);
        assertEquals("Check findOne on first element", 1, (int) row.getId());
    }

    @Test
    public void findAll() {
        List<Row> rows = rowDao.findAll();
        assertEquals("Check findAll - is 8 first", 8, rows.size());
    }

    @Test
    public void findAllPageable() {
        List<Row> rows = rowDao.findAllPageable(new PageRequest(0, 7)).getContent();
        assertEquals("Check findAllPageable - is 7 first", 7, rows.size());
    }

    @Test
    public void findByRoom() {
        Room room = roomDao.findOne(1);
        List<Row> rows = rowDao.findByRoom(room);
        assertEquals("Check findByRoom - is 4 first", 4, rows.size());

        room = roomDao.findOne(3);
        rows = rowDao.findByRoom(room);
        assertEquals("Check findByRoom - is 0 first", 0, rows.size());
    }

    @Test
    public void findByShow() {
        Show show = showDao.findOne(1);
        List<Row> rows = rowDao.findByShow(show);
        assertEquals("Check findByShow - is 4 first", 4, rows.size());
    }

}
