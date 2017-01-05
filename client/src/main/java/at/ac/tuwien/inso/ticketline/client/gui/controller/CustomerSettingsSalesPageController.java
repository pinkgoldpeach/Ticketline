package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;

import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.DateFormat;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for customer overview in the sales page, user are able to edit and create customer and choose customer
 */
@Component
public class CustomerSettingsSalesPageController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSettingsSalesPageController.class);

    @FXML
    private AnchorPane customerSettingsPane;
    @FXML
    private TableView tableCustomerView;
    @FXML
    private TableColumn<UICustomer, String> tableColumnFirstname, tableColumnLastname, tableColumnEmail, tableColumnCity, tableColumnPostalcode, tableColumnBirthdate;
    @FXML
    public Button btnNewCustomer, btnEditCustomer, btnNext, btnPrev, buttonChooseCustomer, buttonCancel;
    @FXML
    private Label pageText;
    @FXML
    private TextField pageInput;

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    public CustomerService customerService;

    private Stage prevStage;
    private Integer pageNumber;
    private Integer totalPages;

    private CustomerDto customer;
    private boolean chooseMode;


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.info("init customer table");

        btnNewCustomer.setText(BundleManager.getBundle().getString("generic.new"));
        btnEditCustomer.setText(BundleManager.getBundle().getString("generic.edit"));

        btnEditCustomer.setDisable(true);
        buttonChooseCustomer.setDisable(true);

        tableCustomerView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleSelectionCustomer());


        tableColumnBirthdate.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        tableColumnPostalcode.setCellValueFactory(cellData -> cellData.getValue().postalcodeProperty());
        tableColumnCity.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        tableColumnFirstname.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        tableColumnEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tableColumnLastname.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());

        pageNumber = 1;
        setPages(pageNumber);
        loadPage(pageNumber);
        pageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            pageChangeInput(newValue);
        });

        tableColumnBirthdate.setComparator(new Comparator<String>() {

            @Override
            public int compare(String t, String t1) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date d1 = format.parse(t);
                    Date d2 = format.parse(t1);

                    return Long.compare(d1.getTime(), d2.getTime());
                } catch (ParseException p) {
                    p.printStackTrace();
                }
                return -1;

            }

        });

        tableCustomerView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.customer")));

        buttonChooseCustomer.setVisible(false);
        buttonCancel.setVisible(false);
    }

    /**
     * called if user selects a customer in table
     */
    private void handleSelectionCustomer() {
        buttonChooseCustomer.setDisable(false);
        btnEditCustomer.setDisable(false);
    }

    /**
     * Method which will be executed when the newCustomerButton is clicked
     * This will open a new window with a new CustomerDto as parameter
     */
    public void handleNewCustomer() {
        LOGGER.info("create new customer clicked");
        Stage newEditCustomer = new Stage();
        newEditCustomer.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newEditCustomer.fxml");
        newEditCustomer.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        NewEditCustomerController controller = (NewEditCustomerController) wrapper.getController();
        controller.setEditMode(false);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddress(new AddressDto());
        controller.setCustomerDto(customerDto);
        controller.setController(this);
        newEditCustomer.setResizable(false);
        newEditCustomer.setWidth(300);
        newEditCustomer.setHeight(350);
        newEditCustomer.setTitle(BundleManager.getBundle().getString("generic.newCustomerTitle"));
        controller.setData();
        newEditCustomer.showAndWait();
    }

    /**
     * Method which will be executed when the editCustomerButton is clicked
     * This will open a edit window with the selected customer.
     * If no customer was selected, a alert will be shown
     */
    public void handleEditCustomer() {
        LOGGER.info("edit customer clicked");

        Stage newEditCustomer = new Stage();
        newEditCustomer.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newEditCustomer.fxml");
        newEditCustomer.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        NewEditCustomerController controller = (NewEditCustomerController) wrapper.getController();
        controller.setController(this);
        newEditCustomer.setResizable(false);
        newEditCustomer.setWidth(300);
        newEditCustomer.setHeight(350);
        newEditCustomer.setTitle(BundleManager.getBundle().getString("generic.editCustomerTitle"));
        UICustomer tmp = (UICustomer) tableCustomerView.getSelectionModel().getSelectedItem();

        if (tmp != null) {
            CustomerDto selectedCustomer = tmp.getCustomer();
            controller.setCustomerDto(selectedCustomer);
            controller.setEditMode(true);
            controller.setData();
            newEditCustomer.showAndWait();
        } else {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.noCustomer"),
                    BundleManager.getBundle().getString("customer.alert.header.noCustomer"),
                    BundleManager.getBundle().getString("customer.alert.title.noCustomer"));
        }
    }

    /**
     * Method which will be executed when Reload Button is clicked
     * Reloads the table
     */
    @FXML
    public void handleReload() {
        if (getCurrentPage() == 0) {
            if (getTotalPages() > 0) {
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
     * Sets the mode of the controller weather you can chose a customer or not
     * @param chooseMode true if choosemode, false otherwise
     */
    public void setChooseMode(boolean chooseMode){
        this.chooseMode = chooseMode;
        if(chooseMode) {
            buttonCancel.setVisible(true);
            buttonChooseCustomer.setVisible(true);
        } else {
            buttonCancel.setVisible(false);
            buttonChooseCustomer.setVisible(false);
        }
    }

    /**
     * Sets the previous stage
     *
     * @param stage the previous stage
     */
    public void setStage(Stage stage) {
        prevStage = stage;
    }

    /**
     * Method which loads the custpmer table with the current page number
     *
     * @param i - page which will be loaded
     */
    public void loadPage(int i) {
        ObservableList<UICustomer> UIlist = FXCollections.observableArrayList();

        try {
            for (CustomerDto d : customerService.getSpecificPage(i)) {
                if (!(d.getFirstName().equals("Anonymous") && d.getLastName().equals("Customer"))) {
                    UIlist.add(new UICustomer(d));
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Error when loading page: " + e.getMessage());
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.serviceException"),
                    BundleManager.getBundle().getString("customer.alert.header.serviceException"),
                    BundleManager.getBundle().getString("customer.alert.title.serviceException"));
        }

        tableCustomerView.setItems(UIlist);
    }

    /**
     * Requests and shows the next page, if a next one is possible
     */
    @FXML
    public void nextPage() {
        pageNumber++;
        if (pageNumber <= getTotalPages()) {
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
    public Integer getTotalPages() {
        try {
            return customerService.getPageCount();
        } catch (ServiceException e) {
            LOGGER.error("Error getting total pages: " + e.getMessage());
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.serviceException"),
                    BundleManager.getBundle().getString("customer.alert.header.serviceException"),
                    BundleManager.getBundle().getString("customer.alert.title.serviceException"));
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

        if (page <= totalPages && page > 0) {
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
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.noValidNumber"),
                            BundleManager.getBundle().getString("customer.alert.header.noValidNumber"),
                            BundleManager.getBundle().getString("customer.alert.title.noValidNumber"));
                }

            } catch (NumberFormatException e) {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("customer.alert.content.noValidNumber"),
                        BundleManager.getBundle().getString("customer.alert.header.noValidNumber"),
                        BundleManager.getBundle().getString("customer.alert.title.noValidNumber"));
            }
        }
    }

    /**
     * Returns the chosen customer
     * @return the chosen customer
     */
    public CustomerDto getChosenCustomer(){
        return customer;
    }

    /**
     * If in choose mode, the currently selected customer gets saved, so the other controller can
     * access the customer
     */
    public void handleChooseCustomer(){
        if(chooseMode){
            UICustomer tmp = (UICustomer) tableCustomerView.getSelectionModel().getSelectedItem();
            if(tmp == null){
                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("generic.customerMissing"),BundleManager.getBundle().getString("generic.missing"),BundleManager.getBundle().getString("generic.missing"));
                return;
            } else {
                customer = tmp.getCustomer();
                Stage stage = (Stage) buttonChooseCustomer.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * If in choosemode, this method is called when the user clicks the close window button
     */
    public void handleCancel(){
        if(chooseMode) {
            LOGGER.info("User clicked close window!");
            customer = null;
            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Returns the currently selected page of this controller
     *
     * @return currently selected page
     */
    public int getCurrentPage() {
        return this.pageNumber;
    }

    /**
     * A private helper class which represends a customer but with UIPropertys
     * The original customer is saved to the orgin variable and can be used later on
     */
    private class UICustomer {
        private StringProperty firstname;
        private StringProperty lastname;
        private StringProperty birthdate;
        private StringProperty email;
        private StringProperty postalcode;
        private StringProperty city;

        private CustomerDto origin;

        public UICustomer(CustomerDto customer) {
            this.origin = customer;
            this.firstname = new SimpleStringProperty(customer.getFirstName());
            this.lastname = new SimpleStringProperty(customer.getLastName());
            this.birthdate = new SimpleStringProperty(DateFormat.getDateFormat(customer.getDateOfBirth()).toString());
            this.email = new SimpleStringProperty(customer.getEmail());
            this.postalcode = new SimpleStringProperty(customer.getAddress().getPostalCode());
            this.city = new SimpleStringProperty(customer.getAddress().getCity());
        }

        public CustomerDto getCustomer() {
            return origin;
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

        public String getPostalcode() {
            return postalcode.get();
        }

        public StringProperty postalcodeProperty() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode.set(postalcode);
        }

        public String getCity() {
            return city.get();
        }

        public StringProperty cityProperty() {
            return city;
        }

        public void setCity(String city) {
            this.city.set(city);
        }

    }

}
