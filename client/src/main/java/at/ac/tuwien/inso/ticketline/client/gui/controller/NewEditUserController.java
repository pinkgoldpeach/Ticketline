package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.AdminService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.DatePickerHelper;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import at.ac.tuwien.inso.ticketline.dto.EmployeeRoles;
import at.ac.tuwien.inso.ticketline.dto.GenderDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for new/edit user window
 */
@Component
public class NewEditUserController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewEditUserController.class);

    @FXML
    private Label titleNewEditUser;
    @FXML
    private AnchorPane newEditUserWindow;
    @FXML
    private Button btnSaveUser, btnCancel;
    @FXML
    private TextField labelFirstname, labelLastname, labelInsuranceNo, labelEMail, labelUsername, labelPassword, labelStreet, labelPostalCode, labelCity, labelCountry;
    @FXML
    private DatePicker datePickerBirthday, datePickerEmployeedSince;
    @FXML
    private ChoiceBox choiceGender, choiceRole;
    @FXML
    private CheckBox isBlocked;

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PasswordEncoder encoder;

    private boolean editMode = false;
    private EmployeeDto employee;
    private AdminSettingsController adminSettingsControllercontroller;
    private ContainerController containerPageController;
    private ValidationSupport validationSupport;
    private boolean saved = false;

    /**
     * Initializes the window, sets the date format to european date style and fills the
     * choiceBoxes with the values of the enum-classes
     *
     * @param url       to be called
     * @param resBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.info("init new/edit employee window");
        btnSaveUser.setText(BundleManager.getBundle().getString("generic.save"));
        btnCancel.setText(BundleManager.getBundle().getString("generic.cancel"));

        DatePickerHelper.setEnhancedDatePicker(datePickerBirthday);

        DatePickerHelper.setEnhancedDatePicker(datePickerEmployeedSince);

        ObservableList genderList = FXCollections.observableArrayList();

        for (GenderDto gender : GenderDto.values()) {
            genderList.add(gender);
        }
        choiceGender.setItems(genderList);
        choiceGender.getSelectionModel().select(0);

        ObservableList roleList = FXCollections.observableArrayList();

        for (EmployeeRoles roles : EmployeeRoles.values()) {
            roleList.add(roles);
        }
        choiceRole.setItems(roleList);
        choiceRole.getSelectionModel().select(1);
    }

    /**
     * Sets the AdminSettingsController, which requested this page
     * Has to be called before the window is shown to the user (in case requestes from adminSettings
     *
     * @param controller - controller of the previous stage
     */
    public void setController(AdminSettingsController controller) {
        LOGGER.info("Setting new controller");
        this.adminSettingsControllercontroller = controller;
    }

    /**
     * Sets the ContainerController, which requested this page
     * Has to be called before the window is shown to the user (in case requested from container Controller)
     *
     * @param controller - controller of the previous stage
     */
    public void setController(ContainerController controller) {
        LOGGER.info("Setting new controller");
        this.containerPageController = controller;
    }

    /**
     * Sets a validator on each necessary field (if a new user is created password field is also required)
     * Also sets the title of the page depending if the page should be an add user or an edit user page
     * If the page is an edit user page, all the fields are filled with the values of the selected user
     * Has to be called before showing the window to the user
     */
    public void setData() {
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(false);

        validationSupport.registerValidator(labelUsername, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.username")));
        validationSupport.registerValidator(labelFirstname, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.firstname")));
        validationSupport.registerValidator(labelLastname, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.lastname")));
        validationSupport.registerValidator(labelEMail, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.email")));
        validationSupport.registerValidator(labelInsuranceNo, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.insurance")));
        validationSupport.registerValidator(datePickerBirthday, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.birthday")));
        validationSupport.registerValidator(datePickerEmployeedSince, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.employeeSince")));
        validationSupport.registerValidator(choiceGender, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.gender")));
        validationSupport.registerValidator(labelStreet, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.street")));
        validationSupport.registerValidator(labelPostalCode, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.postalCode")));
        validationSupport.registerValidator(labelCity, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.city")));
        validationSupport.registerValidator(labelCountry, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.country")));
        validationSupport.registerValidator(choiceRole, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.role")));

        if (employee != null) {
            if (!editMode) {
                validationSupport.registerValidator(labelPassword, Validator.createEmptyValidator(BundleManager.getBundle().getString("admin.required.password")));
                titleNewEditUser.setText(BundleManager.getBundle().getString("admin.newUserTitle"));

            } else {
                titleNewEditUser.setText(BundleManager.getBundle().getString("admin.editUserTitle"));


                labelFirstname.setText(employee.getFirstName());
                labelLastname.setText(employee.getLastName());
                labelInsuranceNo.setText(employee.getInsuranceNumber());
                labelEMail.setText(employee.getEmail());
                datePickerBirthday.setValue(employee.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                labelUsername.setText(employee.getUsername());
                datePickerEmployeedSince.setValue(employee.getEmployedSince().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                labelStreet.setText(employee.getAddress().getStreet());
                labelPostalCode.setText(employee.getAddress().getPostalCode());
                labelCity.setText(employee.getAddress().getCity());
                labelCountry.setText(employee.getAddress().getCountry());

                if (employee.isBlocked()) {
                    isBlocked.setSelected(true);
                } else {
                    isBlocked.setSelected(false);
                }

                if (employee.getGender() == GenderDto.MALE) {
                    choiceGender.getSelectionModel().select(0);
                } else {
                    choiceGender.getSelectionModel().select(1);
                }

                if (employee.getRole() == EmployeeRoles.ADMIN) {
                    choiceRole.getSelectionModel().select(0);
                } else {
                    choiceRole.getSelectionModel().select(1);
                }
            }
        } else {
            LOGGER.error("Given employee is null");
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.selectedUser"),
                    BundleManager.getBundle().getString("admin.alert.header.selectedUser"),
                    BundleManager.getBundle().getString("admin.alert.title.problem"));
            Stage stage = (Stage) newEditUserWindow.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Sets Mode of window. Has to be called while creating stage
     *
     * @param editMode true if editMode, false if new employee should be created
     */
    public void setEditMode(boolean editMode) {
        LOGGER.info("Setting edit mode to " + editMode);
        this.editMode = editMode;
    }

    /**
     * Sets the EmployeeDTO which will be edited and passed to the service, if the user wants to
     * Has to be called before the window is shown to the user
     *
     * @param employee - an empty or filled employee dto which will be passed to the service-layer
     */
    public void setEmployeeDTO(EmployeeDto employee) {
        LOGGER.info("Setting employeeDTO");
        this.employee = employee;
    }

    /**
     * Method which will be called if the user clicks the save button. This method will check if all
     * required fields have an input and if so, will add those inputs to the given employeeDto.
     * If the user confirms the changes, those changes will be given to the service-layer in order to
     * send them to the server
     *
     * @param actionEvent event to be handled
     */
    public void handleSaveUser(ActionEvent actionEvent) {
        LOGGER.info("User clicked save user!");

        validationSupport.setErrorDecorationEnabled(true);
        if (!(validationSupport.isInvalid())) {

            employee.setUsername(labelUsername.getText());
            employee.setFirstName(labelFirstname.getText());
            employee.setLastName(labelLastname.getText());
            employee.setEmail(labelEMail.getText());
            employee.setInsuranceNumber(labelInsuranceNo.getText());
            employee.setDateOfBirth(Date.valueOf(datePickerBirthday.getValue()));
            employee.setEmployedSince(Date.valueOf(datePickerEmployeedSince.getValue()));

            AddressDto address = employee.getAddress();
            address.setStreet(labelStreet.getText());
            address.setPostalCode(labelPostalCode.getText());
            address.setCity(labelCity.getText());
            address.setCountry(labelCountry.getText());

            if (labelPassword.getText() != null) {
                if(labelPassword.getText().length() != 0 && !(labelPassword.getText().matches(".*\\s+.*"))){
                    employee.setPasswordHash(encoder.encode(labelPassword.getText()));
                } else {
                    if(!editMode){
                        LOGGER.error("Password not accepted!");
                        CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.missingField"),
                                BundleManager.getBundle().getString("admin.alert.header.missingField"),
                                BundleManager.getBundle().getString("admin.alert.title.missingField"));
                        return;
                    }
                    employee.setPasswordHash(null);
                }
            } else {
                if(!editMode){
                    LOGGER.error("Password not accepted!");
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.missingField"),
                            BundleManager.getBundle().getString("admin.alert.header.missingField"),
                            BundleManager.getBundle().getString("admin.alert.title.missingField"));
                    return;
                }
                employee.setPasswordHash(null);
            }

            if (isBlocked.isSelected()) {
                employee.setBlocked(true);
            } else {
                employee.setBlocked(false);
            }

            if (choiceGender.getSelectionModel().getSelectedItem().equals(GenderDto.MALE)) {
                employee.setGender(GenderDto.MALE);
            } else {
                employee.setGender(GenderDto.FEMALE);
            }

            if (choiceRole.getSelectionModel().getSelectedItem().equals(EmployeeRoles.ADMIN)) {
                employee.setRole(EmployeeRoles.ADMIN);
            } else {
                employee.setRole(EmployeeRoles.STANDARD);
            }

            LOGGER.info("All values set. Showing confirmation alert");

            Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("admin.alert.content.confirm"),
                    BundleManager.getBundle().getString("admin.alert.header.confirm"),
                    BundleManager.getBundle().getString("admin.alert.title.confirm"));
            if (result.get() == ButtonType.OK) {
                LOGGER.info("User wants to save the data");
                try {
                    adminService.createOrUpdateEmployee(employee);
                    if (adminSettingsControllercontroller != null) {
                        if (!editMode) {
                            adminSettingsControllercontroller.setPages(adminSettingsControllercontroller.getTotalPages());
                            adminSettingsControllercontroller.loadPage(adminSettingsControllercontroller.getTotalPages());
                        } else {
                            adminSettingsControllercontroller.setPages(adminSettingsControllercontroller.getCurrentPage());
                            adminSettingsControllercontroller.loadPage(adminSettingsControllercontroller.getCurrentPage());
                        }
                    }
                    saved = true;
                    Stage stage = (Stage) newEditUserWindow.getScene().getWindow();
                    stage.close();
                } catch (ValidationException e) {
                    LOGGER.error("Got a ValidationException: " + e.getMessage());
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
                        text = BundleManager.getBundle().getString("admin.alert.content.validationExcpetion");
                    } else {
                        text = BundleManager.getBundle().getString("admin.alert.content.validationExcpetion") + " " + errors;
                    }
                    CustomAlert.throwErrorWindow(text,
                            BundleManager.getBundle().getString("admin.alert.header.validationException"),
                            BundleManager.getBundle().getString("admin.alert.title.validationException"));
                    saved = false;
                } catch (ServiceException e) {
                    LOGGER.error("Got a ServiceException!");
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.header.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.title.serviceException"));
                    saved = false;
                }
            } else {
                saved = false;
            }
        } else {
            LOGGER.error("There was an missing field in the form!");
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.missingField"),
                    BundleManager.getBundle().getString("admin.alert.header.missingField"),
                    BundleManager.getBundle().getString("admin.alert.title.missingField"));
            saved = false;
        }
    }

    /**
     * Method which will be called if the user clicks the close button
     * This will close the current window
     *
     * @param actionEvent event to be handled
     */
    public void handleCancel(ActionEvent actionEvent) {
        LOGGER.info("User clicked close window!");
        Stage stage = (Stage) newEditUserWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * Returns if the user has successfully saved a user or not
     * @return true, if successfully saved user, false otherwise
     */
    public boolean hasSaved(){
        return saved;
    }
}
