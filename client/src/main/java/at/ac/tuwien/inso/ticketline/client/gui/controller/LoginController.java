package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.service.AuthService;
import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.client.util.StripePayment;
import at.ac.tuwien.inso.ticketline.dto.UserEventDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the login window
 */
@Component
public class LoginController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @Autowired
    private AuthService authService;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin, btnExit;

    @FXML
    private ComboBox<Locale> cbLanguage;

    @FXML
    private Pane root;

    @Autowired
    private ContainerController containerController;

    private NotificationPane notificationPane;

    private final ImageView errorImage = new ImageView(new Image(LoginController.class.getResourceAsStream("/image/icon/warning.png")));

    private boolean english = false;
	/**
	 * {@inheritDoc}
	 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notificationPane = new NotificationPane(root.getChildren().get(0));
        notificationPane.setId("error_login");
        root.getChildren().clear();
        root.getChildren().add(notificationPane);
        cbLanguage.getItems().clear();
        cbLanguage.getItems().addAll(BundleManager.getSupportedLocales());
        cbLanguage.valueProperty().addListener((observable, oldValue, newValue) -> reinitLocale(newValue));
        cbLanguage.getSelectionModel().select(resourceBundle.getLocale());
        if((cbLanguage.getValue().toString()).equals("en")){
            english = true;
        }
    }

    /**
     * Reinitializes the UI with the given locale.
     *
     * @param newValue the new value
     */
    private void reinitLocale(Locale newValue) {
        LocaleContextHolder.setLocale(newValue);
        BundleManager.changeLocale(newValue);
        btnExit.setText(BundleManager.getBundle().getString("generic.exit"));
        btnLogin.setText(BundleManager.getBundle().getString("login.login"));
        txtPassword.setPromptText(BundleManager.getBundle().getString("login.password"));
        txtUsername.setPromptText(BundleManager.getBundle().getString("login.username"));
    }

    /**
     * Triggered if the user hits the login button, logs in the user,
     * approppriate error messages if connection failed or input is not valid
     * @param event the event
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        UserEventDto loginEvent;
        try {
            loginEvent = this.authService.login(txtUsername.getText(), txtPassword.getText());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.connection"), errorImage);
            return;
        }

        if (loginEvent.equals(UserEventDto.AUTH_FAILURE)) {
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.loginData"), errorImage);
            return;
        } else if(loginEvent.equals(UserEventDto.BLOCKED)){
            notificationPane.show(BundleManager.getExceptionBundle().getString("error.loginBlocked"), errorImage);
            return;
        }

        ((Node) event.getSource()).setCursor(Cursor.WAIT);
        Stage landingPageContainerStage = new Stage();
        landingPageContainerStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/container.fxml")));
        landingPageContainerStage.setResizable(false);
        landingPageContainerStage.setTitle(BundleManager.getBundle().getString("app.name"));
        landingPageContainerStage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/image/ticketlineLogo.png")));
        landingPageContainerStage.show();

        if((cbLanguage.getValue().toString()).equals("en")){
            english = true;
        }

        ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        containerController.loadCache();
    }

    /**
     * Exits the application
     *
     * @param event the event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }


    public boolean isEnglish(){
        return english;
    }
}
