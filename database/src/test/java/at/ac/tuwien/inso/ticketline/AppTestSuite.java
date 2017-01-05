package at.ac.tuwien.inso.ticketline;

import at.ac.tuwien.inso.ticketline.dao.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
        AreaDaoTest.class,
        ArtistDaoTest.class,
        EmployeeDaoTest.class,
        LocationDaoTest.class,
        NewsDaoTest.class,
        ParticipationDaoTest.class,
        PerformanceDaoTest.class,
        RoomDaoTest.class,
        RowDaoTest.class,
        SeatDaoTest.class,
        ShowDaoTest.class,
        TicketDaoTest.class,
        ReceiptDaoTest.class
})
public class AppTestSuite {

}
