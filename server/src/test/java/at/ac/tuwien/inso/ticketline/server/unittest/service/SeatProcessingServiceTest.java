package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleSeatProcessingServiceMemory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
public class SeatProcessingServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleSeatProcessingServiceMemory service = null;


    @Before
    public void setUp() throws Exception {
        service = new SimpleSeatProcessingServiceMemory();
    }

    @Test
    public void userNameTest() {
        SimpleSeatProcessingServiceMemory service = Mockito.mock(SimpleSeatProcessingServiceMemory.class);

        //Mock getUserName to return "fakeUserName" as logged in user
        Mockito.when(service.getUserName()).thenReturn("fakeUserName");

        assertTrue("fakeUserName".equals(service.getUserName()));
    }


    @Test
    public void lockSeatTest() {
        SimpleSeatProcessingServiceMemory service = Mockito.mock(SimpleSeatProcessingServiceMemory.class);
        Mockito.when(service.getUserName()).thenReturn("User1");

        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(2);
        seats.add(3);
        seats.add(4);

        Mockito.when(service.getProcessingSeats(1)).thenReturn(new UsedSeatsDto(seats));

        service.addToProcessingSeats(1, seats);
        assertEquals(seats, service.getProcessingSeats(1).getIds());
    }

}