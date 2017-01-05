package at.ac.tuwien.inso.ticketline.server;

import at.ac.tuwien.inso.ticketline.server.integrationtest.rest.AdminControllerTest;
import at.ac.tuwien.inso.ticketline.server.integrationtest.rest.AreaControllerTest;
import at.ac.tuwien.inso.ticketline.server.integrationtest.rest.ArtistControllerTest;
import at.ac.tuwien.inso.ticketline.server.integrationtest.rest.SeatControllerTest;
import at.ac.tuwien.inso.ticketline.server.integrationtest.service.*;
import at.ac.tuwien.inso.ticketline.server.unittest.rest.InputValidationTest;
import at.ac.tuwien.inso.ticketline.server.unittest.service.*;
import at.ac.tuwien.inso.ticketline.server.unittest.util.ReservationNumberGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
        //Format:
        //UnitTest.class, IntegrationTest.class
        AdminServiceTest.class, AdminServiceIntegrationTest.class,
        AreaServiceTest.class, AreaServiceIntegrationTest.class,
        ArtistServiceTest.class, ArtistServiceIntegrationTest.class,
        CustomerServiceTest.class, CustomerServiceIntegrationTest.class,
        InputValidationTest.class,
        LocationServiceTest.class, LocationServiceIntegrationTest.class,
        NewsServiceTest.class, NewsServiceIntegrationTest.class,
        PerformanceServiceTest.class,
        ReceiptServiceTest.class,
        ReceiptEntryServiceTest.class, ReceiptServiceIntegrationTest.class,
        ReservationServiceTest.class, ReservationServiceIntegrationTest.class,
        RoomServiceTest.class, RoomServiceIntegrationTest.class,
        RowServiceTest.class, RowServiceIntegrationTest.class,
        ShowServiceTest.class, ShowServiceIntegrationTest.class,
        SeatProcessingServiceTest.class, SeatProcessingServiceIntegrationTest.class,
        SeatServiceIntegrationTest.class,
        StripeServiceIntegrationTest.class,
        TicketServiceTest.class, TicketServiceIntegrationTest.class,

        //Util
        ReservationNumberGeneratorTest.class,

        //REST
        AdminControllerTest.class,
        AreaControllerTest.class,
        ArtistControllerTest.class,
        SeatControllerTest.class,

})
public class AppTestSuite {

}
