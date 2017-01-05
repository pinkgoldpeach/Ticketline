package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.TicketlineClient;
import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.gui.JavaFXUtils;
import at.ac.tuwien.inso.ticketline.client.service.AuthService;
import at.ac.tuwien.inso.ticketline.client.service.ShowService;
import at.ac.tuwien.inso.ticketline.client.service.rest.AuthRestClient;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import org.controlsfx.control.NotificationPane;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller of Tabs and Menus
 */
@Component
public class ContainerController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerController.class);

    @FXML
    public Menu menuItemFile, menuItemNew;
    @FXML
    public MenuItem menuItemNewUser, menuItemCustomer, menuItemLogout, menuItemExit, menuItemNewNews;
    @FXML
    public Tab landingTabPage, searchEventsTabPage, customerTabPage, adminTabPage, ticketTabPage, invoiceTabPage, top10TabPage;

    @FXML
    private Pane root;

    @Autowired
    private AuthRestClient authRestClient;
    @Autowired
    private AuthService authService;
    @Autowired
    private Top10OverviewController top10OverviewController;
    @Autowired
    private LandingPageController landingPageController;
    @Autowired
    private InvoiceController invoiceController;

    @Autowired
    private AdminSettingsController adminController;

    @Autowired
    private TicketsOverviewController ticketController;

    @Autowired
    private CustomerSettingsController customerController;
    @Autowired
    private SearchEventsOverviewController searchEventsOverviewController;

    @Autowired
    private ShowService showService;


    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    private UserStatusDto userStatusDto;

    @FXML
    private TabPane containerTabs;

    //for logout
    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    /**
     * Init all labels, fields and setup validation support
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {

        userStatusDto = authRestClient.getUserStatus();
        if (!(userStatusDto.getRoles().get(0).equals("ROLE_ADMINISTRATOR"))) {
            menuItemNewUser.setVisible(false);
            menuItemNewNews.setVisible(false);
        } else {
            adminTabPage = new Tab();
            adminTabPage.setContent((Node) springFxmlLoader.load("/gui/fxml/adminUserSettings.fxml"));
            containerTabs.getTabs().add(adminTabPage);
            adminTabPage.setText(BundleManager.getBundle().getString("tabname.adminPage"));
        }


        //Set Strings of menu
        menuItemNewNews.setText(BundleManager.getBundle().getString("generic.news"));
        menuItemFile.setText(BundleManager.getBundle().getString("generic.file"));
        menuItemExit.setText(BundleManager.getBundle().getString("generic.exit"));
        menuItemNew.setText(BundleManager.getBundle().getString("generic.new"));
        menuItemNewUser.setText(BundleManager.getBundle().getString("generic.user"));
        menuItemCustomer.setText(BundleManager.getBundle().getString("generic.customer"));
        menuItemLogout.setText(BundleManager.getBundle().getString("generic.logout"));

        //set strings of tabs
        searchEventsTabPage.setText(BundleManager.getBundle().getString("tabname.searchEvents"));
        landingTabPage.setText(BundleManager.getBundle().getString("tabname.landingPage"));
        customerTabPage.setText(BundleManager.getBundle().getString("tabname.customerPage"));
        ticketTabPage.setText(BundleManager.getBundle().getString("tabname.ticketPage"));
        invoiceTabPage.setText(BundleManager.getBundle().getString("tabname.invoices"));
        if (BundleManager.getBundle().getLocale() == Locale.ENGLISH) {
            Locale.setDefault(Locale.ENGLISH);
        } else {
            Locale.setDefault(Locale.GERMAN);
        }

        containerTabs.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab == landingTabPage) {
                LOGGER.info("updating LandingPage");
                landingPageController.reloadPane();
            } else if(newTab  == top10TabPage){
                LOGGER.info("updating TOP10");
                top10OverviewController.reloadPage(BundleManager.getBundle().getString("generic.all"));
            } else if(newTab == invoiceTabPage){
                LOGGER.info("updating Invoice");
                invoiceController.handleReload();
            } else if(newTab == customerTabPage){
                LOGGER.info("updating customer");
                customerController.handleReload();
                customerController.setChooseMode(false);
            } else if(newTab == adminTabPage){
                LOGGER.info("updating adminuser page");
                adminController.handleReload();
            } else if(newTab == ticketTabPage){
                LOGGER.info("updating ticket page");
                ticketController.clearSearch();
            }
        });
    }

    /**
     * Method which loads the cache for the search events controller
     */
    public void loadCache(){
        searchEventsTabPage.setDisable(true);
        searchEventsOverviewController.cacheData();
        searchEventsTabPage.setDisable(false);
    }

    /**
     * Exits the application
     */
    @FXML
    public void handleExit() {
        LOGGER.info("Trying to log out");
        try {
            this.authService.logout();
        } catch (ServiceException e) {
            LOGGER.error("Logout failed: " + e.getMessage(), e);
        }
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Logsout current user, shows confirmation and after success, the login window
     */
    @FXML
    public void handleLogout() {
        LOGGER.info("user wants to log out");
        Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("generic.logoutSure"), BundleManager.getBundle().getString("generic.logout") + " " + userStatusDto.getFirstName() + " " + userStatusDto.getLastName(), BundleManager.getBundle().getString("generic.logout"));
        if (result.get() == ButtonType.OK) {
            try {
                authService.logout();
            } catch (ServiceException e) {
                LOGGER.error("Logout failed: " + e.getMessage(), e);
            }



            LOGGER.info("Starting Ticketline Client after logout");
            LocaleContextHolder.setLocale(Locale.getDefault());
            classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/spring/client-context.xml");
            classPathXmlApplicationContext.start();
            SpringFxmlLoader springFxmlLoader = (SpringFxmlLoader) classPathXmlApplicationContext.getBean("springFxmlLoader");
            Stage primaryStage = new Stage();
            try {
                primaryStage.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/login.fxml")));
                primaryStage.setResizable(false);
                primaryStage.setTitle(BundleManager.getBundle().getString("app.name"));
                primaryStage.getIcons().add(new Image(TicketlineClient.class.getResourceAsStream("/image/ticketlineLogo.png")));
                primaryStage.show();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                Alert alerti = JavaFXUtils.createAlert(e);
                alerti.showAndWait();
                Platform.exit();
            }

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Opens Window to create user
     */
    @FXML
    public void handleNewUser() {
        LOGGER.info("create new user clicked");
        Stage newUser = new Stage();
        newUser.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/newEditUser.fxml");
        newUser.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        NewEditUserController controller = (NewEditUserController) wrapper.getController();
        controller.setEditMode(false);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setAddress(new AddressDto());
        controller.setEmployeeDTO(employeeDto);
        controller.setController(this);
        newUser.setResizable(false);
        newUser.setWidth(300);
        newUser.setHeight(350);
        newUser.setTitle(BundleManager.getBundle().getString("generic.newUserTitle"));
        controller.setData();
        newUser.showAndWait();

        if(controller.hasSaved()) {
            adminController.loadLastPage();
            containerTabs.getSelectionModel().select(adminTabPage);
        }
    }


    /**
     * Opens Window to create customers
     */
    @FXML
    public void handleNewCustomer() {
        LOGGER.info("new customer clicked");
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

        if(controller.hasSaved()) {
            customerController.loadLastPage();
            containerTabs.getSelectionModel().select(customerTabPage);
        }
    }

    /**
     * Triggered of admin wants to publish news, opens new window
     */
    @FXML
    public void handleNewNews() {
        LOGGER.info("new news clicked");
        Stage newNews = new Stage();
        newNews.initModality(Modality.APPLICATION_MODAL);
        newNews.setScene(new Scene((Parent) springFxmlLoader.load("/gui/fxml/createNewsAdmin.fxml")));
        newNews.setResizable(false);
        newNews.setTitle(BundleManager.getBundle().getString("generic.newNewsTitle"));

        newNews.showAndWait();


        SpringFxmlLoader.LoadWrapper wrapper2 = springFxmlLoader.loadAndWrap("/gui/fxml/landingPage.fxml");
        LandingPageController controller = (LandingPageController) wrapper2.getController();

        if (null != controller.getNewsBox()) {
            controller.initNewsBox();
        }
    }


    /**
     * Set DTO for User status
     * @param userStatusDto new user status
     */
    public void setUserStatusDto(UserStatusDto userStatusDto) {
        this.userStatusDto = userStatusDto;
    }
}

