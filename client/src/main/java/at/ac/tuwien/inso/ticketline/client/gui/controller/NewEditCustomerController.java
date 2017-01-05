package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.DatePickerHelper;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.CustomerStatusDto;
import at.ac.tuwien.inso.ticketline.dto.GenderDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for new/edit customer window
 */
@Component
public class NewEditCustomerController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewEditCustomerController.class);

    @FXML
    public AnchorPane newEditCustomerWindow;
    @FXML
    private Button btnSaveCustomer, btnCancel;
    @FXML
    private TextField labelFirstname, labelLastname, labelCity, labelCountry, labelStreet, labelPostalCode, labelEMail;
    @FXML
    private DatePicker datePickerBirthday;
    @FXML
    private ChoiceBox choiceGender;
    @FXML
    public Label titleNewEditCustomer;

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private CustomerService customerService;

    private CustomerDto customerDto;
    private boolean editMode = false;
    private CustomerSettingsController customerSettingsController;
    private CustomerSettingsSalesPageController customerSettingsSalesController;
    private ContainerController containerPageController;
    private ValidationSupport validationSupport;

    private boolean saved = false;

    /**
     * initialize window, fill with data if in editmode
     * editmode has to be set before
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.info("init new/edit customer window");
        btnSaveCustomer.setText(BundleManager.getBundle().getString("generic.save"));
        btnCancel.setText(BundleManager.getBundle().getString("generic.cancel"));

        DatePickerHelper.setEnhancedDatePicker(datePickerBirthday);

        ObservableList genderList = FXCollections.observableArrayList();

        for (GenderDto gender : GenderDto.values()) {
            genderList.add(gender);
        }
        choiceGender.setItems(genderList);
        choiceGender.getSelectionModel().select(0);

    }

    /**
     * Sets the controller of the previous stage
     *
     * @param controller - the controller
     */
    public void setController(CustomerSettingsSalesPageController controller) {
        this.customerSettingsSalesController = controller;
    }

    /**
     * Sets the controller of the previous stage
     *
     * @param controller - the controller
     */
    public void setController(CustomerSettingsController controller) {
        this.customerSettingsController = controller;
    }

    /**
     * Sets the controller of the previous stage
     *
     * @param controller - the controller
     */
    public void setController(ContainerController controller) {
        this.containerPageController = controller;
    }

    /**
     * Sets a validator on each necessary field
     * Also sets the title of the page depending if the page should be an add customer or an edit customer page
     * If the page is an edit customer page, all the fields are filled with the values of the selected customer
     * Has to be called before showing the window to the user
     */
    public void setData() {
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(false);

        validationSupport.registerValidator(labelFirstname, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.firstname")));
        validationSupport.registerValidator(labelLastname, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.lastname")));
        validationSupport.registerValidator(labelCity, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.city")));
        validationSupport.registerValidator(labelCountry, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.country")));
        validationSupport.registerValidator(labelStreet, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.street")));
        validationSupport.registerValidator(labelPostalCode, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.postalCode")));
        validationSupport.registerValidator(labelEMail, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.email")));
        validationSupport.registerValidator(datePickerBirthday, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.birthday")));
        validationSupport.registerValidator(choiceGender, Validator.createEmptyValidator(BundleManager.getBundle().getString("customer.required.gender")));

        if (!editMode) {
            titleNewEditCustomer.setText(BundleManager.getBundle().getString("generic.newCustomerTitle"));
        } else {
            titleNewEditCustomer.setText(BundleManager.getBundle().getString("generic.editCustomerTitle"));

            if (customerDto != null) {
                labelFirstname.setText(customerDto.getFirstName());
                labelLastname.setText(customerDto.getLastName());
                labelCity.setText(customerDto.getAddress().getCity());
                labelCountry.setText(customerDto.getAddress().getCountry());
                labelStreet.setText(customerDto.getAddress().getStreet());
                labelPostalCode.setText(customerDto.getAddress().getPostalCode());
                labelEMail.setText(customerDto.getEmail());
                datePickerBirthday.setValue(customerDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                if (customerDto.getGender() == GenderDto.MALE) {
                    choiceGender.getSelectionModel().select(0);
                } else {
                    choiceGender.getSelectionModel().select(1);
                }

            } else {
                LOGGER.info("Customer is null");
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.customerNull"),
                        BundleManager.getBundle().getString("customer.alert.header.customerNull"),
                        BundleManager.getBundle().getString("customer.alert.title.customerNull"));
                Stage stage = (Stage) newEditCustomerWindow.getScene().getWindow();
                stage.close();
            }
        }
    }


    /**
     * Sets Mode of window. Has to be called while creating stage
     *
     * @param editMode true if editMode, else a new customer is going to be created
     */
    public void setEditMode(boolean editMode) {
        LOGGER.info("Edit Mode enabled");
        this.editMode = editMode;
    }

    /**
     * Sets the customerDto which will be used for adding or updating the customer
     *
     * @param customerDto - a customerDTP
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    /**
     * Saves customer, shows a confirmation window
     *
     * @param actionEvent Event to be handled
     */
    public void handleSaveCustomer(ActionEvent actionEvent) {

        LOGGER.info("Save Customer clicked");

        validationSupport.setErrorDecorationEnabled(true);
        if (!(validationSupport.isInvalid())) {
            customerDto.setFirstName(labelFirstname.getText());
            customerDto.setLastName(labelLastname.getText());
            customerDto.setDateOfBirth(Date.valueOf(datePickerBirthday.getValue()));
            customerDto.setEmail(labelEMail.getText());
            AddressDto addressDto = customerDto.getAddress();
            addressDto.setStreet(labelStreet.getText());
            addressDto.setPostalCode(labelPostalCode.getText());
            addressDto.setCity(labelCity.getText());
            addressDto.setCountry(labelCountry.getText());
            customerDto.setCustomerStatus(CustomerStatusDto.VALID);

            if (choiceGender.getSelectionModel().getSelectedItem().equals(GenderDto.MALE)) {
                customerDto.setGender(GenderDto.MALE);
            } else {
                customerDto.setGender(GenderDto.FEMALE);
            }

            Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("customer.alert.content.saveCustomer"),
                    BundleManager.getBundle().getString("customer.alert.header.saveCustomer"),
                    BundleManager.getBundle().getString("customer.alert.title.saveCustomer"));
            if (result.get() == ButtonType.OK) {
                try {
                    customerService.saveCustomer(customerDto);
                    if (customerSettingsController != null) {
                        if (!editMode) {
                            customerSettingsController.setPages(customerSettingsController.getTotalPages());
                            customerSettingsController.loadPage(customerSettingsController.getTotalPages());
                        } else {
                            customerSettingsController.setPages(customerSettingsController.getCurrentPage());
                            customerSettingsController.loadPage(customerSettingsController.getCurrentPage());
                        }
                    } else if (customerSettingsSalesController != null) {
                        if (!editMode) {
                            customerSettingsSalesController.setPages(customerSettingsSalesController.getTotalPages());
                            customerSettingsSalesController.loadPage(customerSettingsSalesController.getTotalPages());
                        } else {
                            customerSettingsSalesController.setPages(customerSettingsSalesController.getCurrentPage());
                            customerSettingsSalesController.loadPage(customerSettingsSalesController.getCurrentPage());
                        }
                    }
                    saved = true;
                    Stage stage = (Stage) newEditCustomerWindow.getScene().getWindow();
                    stage.close();
                } catch (ValidationException e) {
                    LOGGER.error("Got a ValidationException!");
                    String errors = "";
                    if (e.getFieldErrors() != null) {
                        for (int i = 0; i < e.getFieldErrors().size(); i++) {
                            if (!errors.equals("")) {
                                errors = errors + ". ";
                            }
                            errors = errors + e.getFieldErrors().get(i).getField();
                        }
                    }
                    String text = "";
                    if (errors.equals("")) {
                        text = BundleManager.getBundle().getString("customer.alert.content.validationExcpetion");
                    } else {
                        text = BundleManager.getBundle().getString("customer.alert.content.validationExcpetion") + " " + errors;
                    }

                    CustomAlert.throwErrorWindow(text,
                            BundleManager.getBundle().getString("customer.alert.header.validationException"),
                            BundleManager.getBundle().getString("customer.alert.title.validationException"));
                    saved = false;
                } catch (ServiceException e) {
                    LOGGER.error("Got a ServiceException!");
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.serviceException"),
                            BundleManager.getBundle().getString("customer.alert.header.serviceException"),
                            BundleManager.getBundle().getString("customer.alert.title.serviceException"));
                    saved = false;
                }
            } else {
                saved = false;
            }
        } else {
            LOGGER.error("There is a required value null!");
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.fieldNull"),
                    BundleManager.getBundle().getString("customer.alert.header.fieldNull"),
                    BundleManager.getBundle().getString("customer.alert.title.fieldNull"));
            saved = false;
        }

    }

    /**
     * Close new/edit customer window
     *
     * @param actionEvent Event to be handled
     */
    public void handleCancel(ActionEvent actionEvent) {
        saved = false;
        Stage stage = (Stage) newEditCustomerWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * Returns if the user has successfully saved a customer or not
     * @return true, if successfully saved customer, false otherwise
     */
    public boolean hasSaved(){
        return saved;
    }
}
