package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import at.ac.tuwien.inso.ticketline.server.service.RowService;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 26/06/2016.
 */
public class RowServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    RowService rowService;

    @Autowired
    RoomService roomService;

    @Autowired
    ShowService showService;

    @Test
    public void testGetRowsByRoom(){
        try {

            List<Room> rooms = roomService.getPageOfRooms(0,8);
            //room 3
            Room room = rooms.get(6);
            List<Row> rows = rowService.getRowsByRoom(room);
            assertTrue(rows.size() == 4);
            assertTrue(rows.get(0).getId() == 1);
            assertTrue(rows.get(1).getId() == 2);
            assertTrue(rows.get(2).getId() == 3);
            assertTrue(rows.get(3).getId() == 4);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetRowsByShow(){
        try {
            List<Show> shows = showService.getAllShows();
            Show show = shows.get(4);
            List<Row> rows = rowService.getRowsByShow(show);
            assertTrue(rows.size() == 4);
            assertTrue(rows.get(0).getId() == 5);
            assertTrue(rows.get(1).getId() == 6);
            assertTrue(rows.get(2).getId() == 7);
            assertTrue(rows.get(3).getId() == 8);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }
}
