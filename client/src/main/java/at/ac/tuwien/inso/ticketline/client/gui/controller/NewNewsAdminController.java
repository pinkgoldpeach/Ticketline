package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.NewsService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for new news message
 */
@Component
public class NewNewsAdminController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewNewsAdminController.class);
    @FXML
    public AnchorPane newNewsPane;
    @FXML
    public Button btnNewNews, btnCancel;
    @FXML
    public TextField fieldNewsTitle;
    @FXML
    public TextArea areaNewsMessage;

    @Autowired
    private NewsService newsService;

    private ValidationSupport validationSupport;

    /**
     * initialize window, fill with data if in editmode
     * editmode has to be set before
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        btnCancel.setText(BundleManager.getBundle().getString("generic.cancel"));
        btnNewNews.setText(BundleManager.getBundle().getString("generic.publish"));
        fieldNewsTitle.setPromptText(BundleManager.getBundle().getString("generic.title"));
        areaNewsMessage.setPromptText(BundleManager.getBundle().getString("generic.message"));

        validationSupport = new ValidationSupport();
        validationSupport.registerValidator(fieldNewsTitle, Validator.createEmptyValidator(BundleManager.getBundle().getString("news.required.title")));
        validationSupport.registerValidator(areaNewsMessage, Validator.createEmptyValidator(BundleManager.getBundle().getString("news.required.message")));
    }

    /**
     * Handels the close of the current window
     *
     * @param actionEvent event to be handled
     */
    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) newNewsPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Triggered if User clicked on publish news. Should save news in database
     * @param actionEvent Event to be handled
     */
    public void handlePublishNewsMessage(ActionEvent actionEvent) {
        LOGGER.info("Publish News clicked");

        NewsDto news = new NewsDto();

        if(!(validationSupport.isInvalid())){
            news.setNewsText(areaNewsMessage.getText());

            news.setTitle(fieldNewsTitle.getText());

            Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("news.alert.content.save"),
                    BundleManager.getBundle().getString("news.alert.header.save"),
                    BundleManager.getBundle().getString("generic.publishNews"));
            if ((result.get() == ButtonType.OK) && news!=null) {
                try {
                    newsService.publishNews(news);
                } catch (ValidationException ve){
                    LOGGER.error("Validation Exception: " + ve.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("news.alert.content.validationException"),
                            BundleManager.getBundle().getString("news.alert.header.validationException"),
                            BundleManager.getBundle().getString("news.alert.title.validationException"));
                } catch (ServiceException e) {
                    LOGGER.error("Service Exception: " + e.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("news.alert.content.serviceException"),
                            BundleManager.getBundle().getString("news.alert.header.serviceException"),
                            BundleManager.getBundle().getString("news.alert.title.serviceException"));
                }
                Stage stage = (Stage) newNewsPane.getScene().getWindow();
                stage.close();
            }
        } else {
            LOGGER.error("There was a value null!");
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("news.alert.content.fieldNull"),
                    BundleManager.getBundle().getString("news.alert.header.fieldNull"),
                    BundleManager.getBundle().getString("news.alert.title.fieldNull"));
        }
    }
}
