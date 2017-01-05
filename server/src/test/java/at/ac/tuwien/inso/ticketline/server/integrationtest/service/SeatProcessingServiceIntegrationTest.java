package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatProcessingService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class SeatProcessingServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private SeatProcessingService service;


    @Test
    public void getProcessingSeats() {
        try {
            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals(0, usedSeatsDto.getIds().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void getProcessingSeats_multiple_shows() {
        try {
            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals(0, usedSeatsDto.getIds().size());

            //username
            String username = "employee1";

            //Locking for 2 seats
            ArrayList<Integer> seatIds = new ArrayList<>();
            seatIds.add(10);
            seatIds.add(11);

            //For show 1
            service.addToProcessingSeats(1, seatIds, username);
            //For show 2
            service.addToProcessingSeats(2, seatIds, username);

            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 2 for show 1", 2, usedSeatsDto.getIds().size());

            usedSeatsDto = service.getProcessingSeats(2);
            assertEquals("Must find 2 for show 2", 2, usedSeatsDto.getIds().size());


        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }


    @Test
    public void addToProcessingSeats() {
        try {

            //username
            String username = "employee1";

            //Locking for 2 seats
            ArrayList<Integer> seatIds = new ArrayList<>();
            seatIds.add(10);
            seatIds.add(11);

            service.addToProcessingSeats(1, seatIds, username);

            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals(2, usedSeatsDto.getIds().size());

            //Adding for another user
            //username
            String username2 = "employee2";

            //Locking for 2 seats
            ArrayList<Integer> seatIds2 = new ArrayList<>();
            seatIds2.add(15);
            seatIds2.add(16);

            service.addToProcessingSeats(1, seatIds2, username);

            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals(4, usedSeatsDto.getIds().size());


        } catch (Exception e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void checkProcessingSeats() throws Exception {
        try {

            //username
            String username = "employee1";

            //Locking for 2 seats
            ArrayList<Integer> seatIdsLocked = new ArrayList<>();
            seatIdsLocked.add(10);
            seatIdsLocked.add(11);

            service.addToProcessingSeats(1, seatIdsLocked, username);

            ArrayList<Integer> seatIdsFree = new ArrayList<>();
            seatIdsFree.add(15);
            seatIdsFree.add(16);

            assertTrue("All checked seats are free, so it must cause a TRUE", service.checkProcessingSeats(1, seatIdsFree));
            assertFalse("Already locked seats should cause a FALSE", service.checkProcessingSeats(1, seatIdsLocked));


        } catch (Exception e) {
            fail("ServiceException thrown: " + e);
        }
    }


    @Test
    public void freeProcessingSeat() throws Exception {
        try {

            //username
            String username = "employee1";

            //Locking for 2 seats
            ArrayList<Integer> seatIdsLocked = new ArrayList<>();
            seatIdsLocked.add(10);
            seatIdsLocked.add(11);

            service.addToProcessingSeats(1, seatIdsLocked, username);
            assertEquals("Two locked seats must be found (10, 11)", 2, service.getProcessingSeats(1).getIds().size());

            //Freeing 10
            service.freeProcessingSeat(new ProcessingSeat(1, 10), username);

            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("One locked seat must be found (11)", 1, usedSeatsDto.getIds().size());

            int id = usedSeatsDto.getIds().get(0);
            assertEquals("Must be Seat ID 11", 11, id);

            //Freeing 11
            service.freeProcessingSeat(new ProcessingSeat(1, 11), username);
            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must be empty not for show 1", 0, usedSeatsDto.getIds().size());

        } catch (Exception e) {
            fail("ServiceException thrown: " + e);
        }
    }

    @Test
    public void removeProcessingSeatsforUser() {

        try {
            String username = "employee1";
            ArrayList<Integer> seatIds = new ArrayList<>();
            seatIds.add(10);
            seatIds.add(11);
            service.addToProcessingSeats(1, seatIds, username);

            String username2 = "employee2";
            ArrayList<Integer> seatIds2 = new ArrayList<>();
            seatIds2.add(15);
            seatIds2.add(16);
            service.addToProcessingSeats(1, seatIds2, username2);


            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 4 in sum", 4, usedSeatsDto.getIds().size());

            service.removeProcessingSeatsForUser(username);

            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 2 in sum", 2, usedSeatsDto.getIds().size());

            service.removeProcessingSeatsForUser(username2);

            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 0 in sum", 0, usedSeatsDto.getIds().size());


        } catch (Exception e) {
            fail("ServiceException thrown: " + e);
        }
    }


    @Test
    public void removeProcessingSeatsforUser_no_entries() {
        try {
            String username = "employee1";
            service.removeProcessingSeatsForUser(username);
        } catch (Exception e) {
            fail("ServiceException thrown: " + e);
        }
    }


    @Test
    public void removeDeprecatedProcessingSeat() {

        try {
            String username = "employee1";
            ArrayList<Integer> seatIds = new ArrayList<>();
            seatIds.add(10);
            seatIds.add(11);
            service.addToProcessingSeats(1, seatIds, username);

            //Let 2 seconds pass by
            Thread.sleep(3000);

            String username2 = "employee2";
            ArrayList<Integer> seatIds2 = new ArrayList<>();
            seatIds2.add(15);
            seatIds2.add(16);
            service.addToProcessingSeats(1, seatIds2, username2);


            UsedSeatsDto usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 4 in sum", 4, usedSeatsDto.getIds().size());

            //Delete all records older than 3 seconds
            service.removeDeprecatedProcessingSeat(3);

            usedSeatsDto = service.getProcessingSeats(1);
            assertEquals("Must find 2 in sum", 2, usedSeatsDto.getIds().size());
            assertEquals(15, (int) usedSeatsDto.getIds().get(0));
            assertEquals(16, (int) usedSeatsDto.getIds().get(1));

        } catch (Exception e) {
            fail("ServiceException thrown: " + e);
        }
    }


}
