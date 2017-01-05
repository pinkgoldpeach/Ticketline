package at.ac.tuwien.inso.ticketline.client.guitest;

import at.ac.tuwien.inso.ticketline.client.TicketlineClient;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxToolkit.*;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class LoginTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {

    }

    @BeforeClass
    public static void setupSpec() throws Exception {
        Stage primaryStage = registerPrimaryStage();
        setupStage(stage -> stage.show());
    }

    @Before
    public void setup() throws Exception {
        setupApplication(TicketlineClient.class);
    }

    @Test
    public void login_Fail_wrongUserPassword() {
        //Type in username and wrong password
        clickOn("#txtUsername").write("username");
        clickOn("#txtPassword").write("WRONG PW");

        //click login button
        clickOn("#btnLogin");
    }


}
