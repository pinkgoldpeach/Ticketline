package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import at.ac.tuwien.inso.ticketline.server.service.RowService;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 26/06/2016.
 */
public class SeatServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    SeatService seatService;

    @Autowired
    RowService rowService;

    @Autowired
    RoomService roomService;

    @Test
    public void testGetSeatsByRow(){
        try {
            List<Row> rows = rowService.getPageOfRows(0, 8);
            Row row = rows.get(4);
            List<Seat> seats = seatService.getSeatsByRow(row);
            assertTrue(seats.size() == 6);
            row = rows.get(5);
            seats = seatService.getSeatsByRow(row);
            assertTrue(seats.size() == 3);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetSeatsByRoom(){
        try {
            List<Room> rooms = roomService.getPageOfRooms(0, 8);
            Room room = rooms.get(7);
            List<Seat> seats = seatService.getSeatsByRoom(room);
            assertTrue(seats.size() == 9);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }
}
