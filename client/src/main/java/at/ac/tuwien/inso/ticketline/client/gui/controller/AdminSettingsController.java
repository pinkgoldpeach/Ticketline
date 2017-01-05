package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AdminService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.DateFormat;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for the admin controller
 *
 */
@Component
public class AdminSettingsController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSettingsController.class);

    @FXML
    private AnchorPane adminSettingsPane;
    @FXML
    public TableView tableUserView;
    @FXML
    public TableColumn<UIEmployee, String> usernameColumn, firstnameColumn, lastnameColumn, birthdateColumn, emailColumn, employeedSinceColumn, insuranceColumn, blockedColumn;
    @FXML
    public Button btnNewUser, btnEditUser, btnNext, btnPrev;
    @FXML
    public Label pageText;
    @FXML
    public TextField pageInput;

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    public AdminService adminService;

    private Stage prevStage;
    private Integer pageNumber;
    private Integer totalPages;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.info("init admin table");

        btnNewUser.setText(BundleManager.getBundle().getString("generic.new"));
        btnEditUser.setText(BundleManager.getBundle().getString("generic.edit"));

        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastnameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        employeedSinceColumn.setCellValueFactory(cellData -> cellData.getValue().employeedSinceProperty());
        insuranceColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceProperty());
        blockedColumn.setCellValueFactory(cellData -> cellData.getValue().blockedProperty());

        pageNumber = 1;
        setPages(pageNumber);
        loadPage(pageNumber);

        tableUserView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleSelectionChange());


        tableUserView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.users")));
        pageInput.textProperty().addListener((observable, oldValue, newValue) -> { pageChangeInput(newValue);});

        birthdateColumn.setComparator(new Comparator<String>() {

            @Override
            public int compare(String t, String t1) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date d1 = format.parse(t);
                    Date d2 = format.parse(t1);

                    return Long.compare(d1.getTime(), d2.getTime());
                } catch (ParseException p) {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.header.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.title.serviceException"));
                }
                return -1;

            }

        });

        employeedSinceColumn.setComparator(new Comparator<String>() {

            @Override
            public int compare(String t, String t1) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date d1 = format.parse(t);
                    Date d2 = format.parse(t1);

                    return Long.compare(d1.getTime(), d2.getTime());
                } catch (ParseException p) {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.header.serviceException"),
                            BundleManager.getBundle().getString("admin.alert.title.serviceException"));
                }
                return -1;

            }

        });
    }

    /**
     * handles selection change in table
     */
    private void handleSelectionChange() {
        if (tableUserView.getItems()!=null && tableUserView.getSelectionModel().getSelectedItem()!=null) {
            btnEditUser.setDisable(false);
        } else  {
            btnEditUser.setDisable(true);
        }
    }

    /**
     * Method which will be executed when the newUser Button is clicked
     * This will open a new window with a new EmployeeDto as parameter
     *
     * @param actionEvent event to be handeled
     */
    public void handleNewUser(ActionEvent actionEvent) {
        LOGGER.info("create new user clicked");
        Stage newEditUser = new Stage();
        newEditUser.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newEditUser.fxml");
        newEditUser.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        NewEditUserController controller = (NewEditUserController) wrapper.getController();
        controller.setEditMode(false);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setAddress(new AddressDto());
        controller.setEmployeeDTO(employeeDto);
        controller.setController(this);
        newEditUser.setResizable(false);
        newEditUser.setWidth(300);
        newEditUser.setHeight(350);
        newEditUser.setTitle(BundleManager.getBundle().getString("generic.newUserTitle"));
        controller.setData();
        newEditUser.showAndWait();

    }

    /**
     * Method which will be executed when the editUser Button is clicked
     * This will open a edit window with the selected employee.
     * If no employee was selected, an alert will be shown
     *
     * @param actionEvent event to be handled
     */
    public void handleEditUser(ActionEvent actionEvent) {
        LOGGER.info("edit user clicked");

        Stage newEditUser = new Stage();
        newEditUser.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newEditUser.fxml");
        newEditUser.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        NewEditUserController controller = (NewEditUserController) wrapper.getController();
        controller.setController(this);
        newEditUser.setResizable(false);
        newEditUser.setWidth(300);
        newEditUser.setHeight(350);
        newEditUser.setTitle(BundleManager.getBundle().getString("generic.editUserTitle"));
        UIEmployee tmp = (UIEmployee) tableUserView.getSelectionModel().getSelectedItem();

        if (tmp != null) {
            EmployeeDto selectedUser = tmp.getEmployee();
            controller.setEmployeeDTO(selectedUser);
            controller.setEditMode(true);
            controller.setData();
            newEditUser.showAndWait();
        } else {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.noEmployee"),
                    BundleManager.getBundle().getString("admin.alert.header.noEmployee"),
                    BundleManager.getBundle().getString("admin.alert.title.noEmployee"));
        }
    }


    /**
     * Sets the previous stage so a return is possible
     *
     * @param stage the previous stage
     */
    public void setStage(Stage stage) {
        prevStage = stage;
    }

    /**
     * Method which loads the employee table with the current page number
     *
     * @param i - page which will be loaded
     */
    public void loadPage(int i) {
        ObservableList<UIEmployee> UIlist = FXCollections.observableArrayList();

        try {
            for (EmployeeDto e : adminService.getAllEmployeesOnPage(i)) {
                UIlist.add(new UIEmployee(e));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        tableUserView.setItems(UIlist);
    }

    /**
     * Requests and shows the next page, if a next one is possible
     */
    @FXML
    public void nextPage() {
        pageNumber++;
        if (pageNumber <= getTotalPages()){
            setPages(pageNumber);
            loadPage(pageNumber);
        } else {
            pageNumber--;
        }
    }

    /**
     * Requests and shows the previous page, if a previous one is possible
     */
    @FXML
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            setPages(pageNumber);
            loadPage(pageNumber);
        }
    }

    /**
     * Requests the totalpages which exist, if something goes wrong
     * the window will be closed due an error
     *
     * @return - total Page number
     */
    public Integer getTotalPages(){
        try{
            return adminService.getPageCount();
        } catch (ServiceException e){
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.serviceException"),
                    BundleManager.getBundle().getString("admin.alert.header.serviceException"),
                    BundleManager.getBundle().getString("admin.alert.title.serviceException"));
            return -1;
        }
    }

    /**
     * Sets the labels in the window and disables buttons if there is no next
     * or previous page
     *
     * @param page which will be set
     */
    public void setPages(int page) {
        if (getTotalPages() != totalPages) {
            totalPages = getTotalPages();
        }

        if(page <= totalPages && page > 0) {
            if (page == 1 && page == totalPages) {
                btnNext.setDisable(true);
                btnPrev.setDisable(true);
            } else if (page == 1) {
                btnPrev.setDisable(true);
            } else if (page == totalPages) {
                btnNext.setDisable(true);
            }

            if (page > 1 && btnPrev.isDisabled()) {
                btnPrev.setDisable(false);
            }
            if (page < totalPages && btnNext.isDisabled()) {
                btnNext.setDisable(false);
            }

            pageNumber = page;
            pageInput.setText(pageNumber.toString());
            pageText.setText("/" + totalPages);
        }
    }

    /**
     * Loads the new page after a change in the pagetextfield was made
     *
     * @param newValue the new value which was inputed to the text field
     */
    private void pageChangeInput(String newValue) {
        if (!(newValue == null || newValue.equals(""))) {
            try {
                int newPage = Integer.parseInt(newValue);
                if (newPage > 0 && newPage <= totalPages) {
                    setPages(newPage);
                    loadPage(newPage);
                } else {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.noValidNumber"),
                            BundleManager.getBundle().getString("admin.alert.header.noValidNumber"),
                            BundleManager.getBundle().getString("admin.alert.title.noValidNumber"));
                }
            } catch (NumberFormatException e) {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("admin.alert.content.noValidNumber"),
                        BundleManager.getBundle().getString("admin.alert.header.noValidNumber"),
                        BundleManager.getBundle().getString("admin.alert.title.noValidNumber"));
            }
        }
    }

    /**
     * Reloads the table
     */
    @FXML
    public void handleReload() {
        btnEditUser.setDisable(true);
        tableUserView.getSelectionModel().clearSelection();
        if(getCurrentPage() == 0){
            if(getTotalPages() > 0){
                setPages(1);
                loadPage(1);
            }
        } else {
            loadPage(getCurrentPage());
        }
    }

    /**
     * Loads the last page available
     */
    public void loadLastPage(){
        if(getTotalPages() > 0){
            setPages(getTotalPages());
            loadPage(getTotalPages());
        }
    }


    /**
     * Returns the currently selected page of this controller
     *
     * @return currently selected page
     */
    public int getCurrentPage(){
        return this.pageNumber;
    }

    /**
     *
     * A private helper class which represends an employee but with UIPropertys
     * The original employee is saved to the orgin variable and can be used later on
     *
     */
    private class UIEmployee {

        private EmployeeDto origin;
        private StringProperty username;
        private StringProperty firstname;
        private StringProperty lastname;
        private StringProperty birthdate;
        private StringProperty email;
        private StringProperty employeedSince;
        private StringProperty insurance;
        private StringProperty blocked;

        public UIEmployee(EmployeeDto employeeDto) {
            this.origin = employeeDto;
            this.username = new SimpleStringProperty(employeeDto.getUsername());
            this.firstname = new SimpleStringProperty(employeeDto.getFirstName());
            this.lastname = new SimpleStringProperty(employeeDto.getLastName());
            this.birthdate = new SimpleStringProperty(DateFormat.getDateFormat(employeeDto.getDateOfBirth()).toString());
            this.email = new SimpleStringProperty(employeeDto.getEmail());
            this.employeedSince = new SimpleStringProperty(DateFormat.getDateFormat(employeeDto.getEmployedSince()).toString());
            this.insurance = new SimpleStringProperty(employeeDto.getInsuranceNumber());
            if(employeeDto.isBlocked()){
                this.blocked = new SimpleStringProperty("✓");
            } else {
                this.blocked = new SimpleStringProperty("✗");
            }
        }

        public EmployeeDto getEmployee() {
            return origin;
        }

        public String getUsername() {
            return username.get();
        }

        public StringProperty usernameProperty() {
            return username;
        }

        public void setUsername(String username) {
            this.username.set(username);
        }

        public String getFirstname() {
            return firstname.get();
        }

        public StringProperty firstnameProperty() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname.set(firstname);
        }

        public String getLastname() {
            return lastname.get();
        }

        public StringProperty lastnameProperty() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname.set(lastname);
        }

        public String getBirthdate() {
            return birthdate.get();
        }

        public StringProperty birthdateProperty() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate.set(birthdate);
        }

        public String getEmail() {
            return email.get();
        }

        public StringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public String getEmployeedSince() {
            return employeedSince.get();
        }

        public StringProperty employeedSinceProperty() {
            return employeedSince ;
        }

        public void setEmployeedSince(String employeedSince) {
            this.employeedSince.set(employeedSince);
        }

        public EmployeeDto getOrigin() {
            return origin;
        }

        public void setOrigin(EmployeeDto origin) {
            this.origin = origin;
        }

        public String getBlocked() {
            return blocked.get();
        }

        public StringProperty blockedProperty() {
            return blocked;
        }

        public void setBlocked(String blocked) {
            this.blocked.set(blocked);
        }

        public String getInsurance() {
            return insurance.get();
        }

        public StringProperty insuranceProperty() {
            return insurance;
        }

        public void setInsurance(String insurance) {
            this.insurance.set(insurance);
        }
    }

}
