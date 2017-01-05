package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleRoomService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class RoomServiceTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SimpleRoomService service = null;

    @Before
    public void setUp() throws Exception {
        service = new SimpleRoomService();
    }


    @Test
    public void getPageCount() throws Exception {
        RoomDao dao = Mockito.mock(RoomDao.class);
        Mockito.when(dao.count()).thenReturn(100L);
        service.setRoomDao(dao);
        assertEquals("According to 20 Entries per page, we expect 5", 5, service.getPageCount());
    }

    @Test
    public void getPageCount_new_page_for_101() throws Exception {
        RoomDao dao = Mockito.mock(RoomDao.class);
        Mockito.when(dao.count()).thenReturn(101L);
        service.setRoomDao(dao);
        assertEquals("According to 20 Entries per page, we expect 6", 6, service.getPageCount());
    }

    @Test
    public void getPageCount_for_0_entries() throws Exception {
        RoomDao dao = Mockito.mock(RoomDao.class);
        Mockito.when(dao.count()).thenReturn(0L);
        service.setRoomDao(dao);
        assertEquals("For 0 records we expect 0 pages", 0, service.getPageCount());
    }

}