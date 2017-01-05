package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AreaService;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 31/05/2016.
 */
public class AreaServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private AreaService service;

    @Autowired
    private RoomService roomService;

    @Test
    public void testGetPageOf() {
        try {
            List<Area> areas = service.getPageOfAreas(0, 5);
            assertEquals(5, areas.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetNumberOfAvailableTickets(){
       try {
           Show show = new Show();
           show.setId(1);
           Area area = new Area();
           area.setId(1);
           long amount = service.getNumberOfAvailableTickets(show, area);
           assertEquals(37, amount);

       }catch (ServiceException e){
            fail("ServiceException thrown");
        }


    }

    @Test
    public void testGetAreasByRoom(){
        try {
            Room room = new Room();
            room.setId(1);
            List<Area> areas = service.getAreasByRoom(room);
            assertEquals(2, areas.size());

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }

    }

    @Test
    public void testGetAreasByShow(){
        try{
            Show show = new Show();
            show.setId(1);
            List<Area> areas = service.getAreasByShow(show);
            assertEquals(2, areas.size());
            show.setId(2);
            areas = service.getAreasByShow(show);
            assertEquals(4, areas.size());

    } catch (ServiceException e) {
        fail("ServiceException thrown");
    }

    }

}
