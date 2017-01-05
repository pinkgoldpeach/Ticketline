
package at.ac.tuwien.inso.ticketline.server.unittest.util;

import at.ac.tuwien.inso.ticketline.server.integrationtest.rest.AbstractControllerTest;
import at.ac.tuwien.inso.ticketline.server.util.General;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@Transactional
@WebAppConfiguration
public class GeneralTest extends AbstractControllerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    @WithMockUser(username = "test")
    public void getUserName() {
        try {
            General.getUserName();
        } catch (Exception e) {
            fail("Couldn't load Username from Context: " + e.getMessage());
        }
    }

    @Test
    public void getUserName_fail() throws Exception {
        exception.expect(NullPointerException.class);
        General.getUserName();
    }
}