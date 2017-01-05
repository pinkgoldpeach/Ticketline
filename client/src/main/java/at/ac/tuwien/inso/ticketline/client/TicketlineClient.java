package at.ac.tuwien.inso.ticketline.client;

import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.ac.tuwien.inso.ticketline.client.gui.JavaFXUtils;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;

/**
 * Main class to launch the Ticketline client
 */
public class TicketlineClient extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketlineClient.class);
    
    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting Ticketline Client");
        LocaleContextHolder.setLocale(Locale.getDefault());
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/spring/client-context.xml");
        classPathXmlApplicationContext.start();
        SpringFxmlLoader springFxmlLoader = (SpringFxmlLoader) classPathXmlApplicationContext.getBean("springFxmlLoader");
        try {
            primaryStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/login.fxml")));
            primaryStage.setResizable(false);
            primaryStage.setTitle(BundleManager.getBundle().getString("app.name"));
            primaryStage.getIcons().add(new Image(TicketlineClient.class.getResourceAsStream("/image/ticketlineLogo.png")));
            primaryStage.show();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Alert alert = JavaFXUtils.createAlert(e);
            alert.showAndWait();
            Platform.exit();
        }
    }
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    public void stop() throws Exception {
    	classPathXmlApplicationContext.stop();
    }

}