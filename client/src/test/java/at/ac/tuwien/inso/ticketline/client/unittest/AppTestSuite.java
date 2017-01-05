package at.ac.tuwien.inso.ticketline.client.unittest;

import at.ac.tuwien.inso.ticketline.client.guitest.LoginTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
        //Unit Tests


        //GUI Tests
        LoginTest.class
})
public class AppTestSuite {

}