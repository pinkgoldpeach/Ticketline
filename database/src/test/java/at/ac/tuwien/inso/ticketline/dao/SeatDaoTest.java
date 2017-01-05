package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Carla on 18/05/2016.
 */
public class SeatDaoTest extends AbstractDaoTest {

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private RowDao rowDao;

    @Autowired
    private RoomDao roomDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findOne() {
        Seat seat = seatDao.findOne(1);
        assertNotNull(seat);
        assertEquals("Check findOne on first element", 1, (int) seat.getId());
    }

    @Test
    public void findAll() {
        List<Seat> seats = seatDao.findAll();
        assertEquals("Check findAll - is 15 first", 15, seats.size());
    }

    @Test
    public void findByRow() {
        Row row = rowDao.findOne(1);
        List<Seat> seats = seatDao.findByRow(row);
        assertEquals("Check findAll - is 5 first", 5, seats.size());

        row = rowDao.findOne(3);
        seats = seatDao.findByRow(row);
        assertEquals("Check findAll - is 0 first", 0, seats.size());
    }

    @Test
    public void findByRoom() {
        Room room = roomDao.findOne(1);
        List<Seat> seats = seatDao.findByRoom(room);
        assertEquals("Check findAll - is 5 first", 10, seats.size());

        room = roomDao.findOne(2);
        seats = seatDao.findByRoom(room);
        assertEquals("Check findAll - is 5 first", 5, seats.size());

        room = roomDao.findOne(3);
        seats = seatDao.findByRoom(room);
        assertEquals("Check findAll - is 0 first", 0, seats.size());
    }
}
