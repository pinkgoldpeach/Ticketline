package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 25/06/2016.
 */
public class RoomServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void testGetPriceRangeOfRoom(){
        try{
            List<Room> rooms = roomService.getPageOfRooms(0,8);
            //room 1
            Room room = rooms.get(4);
            String priceRangeOfRoom = roomService.getPriceRangeOfRoom(room);
            String priceRange = "34.0€ - 50.0€";
            assertTrue(priceRange.equals(priceRangeOfRoom));

            //room 3
            room = rooms.get(6);
            priceRangeOfRoom = roomService.getPriceRangeOfRoom(room);
            priceRange = "20.0€ - 26.0€";
            assertTrue(priceRange.equals(priceRangeOfRoom));

            //room 4
            room = rooms.get(7);
            priceRangeOfRoom = roomService.getPriceRangeOfRoom(room);
            priceRange = "120.0€ - 200.0€";
            assertTrue(priceRange.equals(priceRangeOfRoom));
        }catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

}
