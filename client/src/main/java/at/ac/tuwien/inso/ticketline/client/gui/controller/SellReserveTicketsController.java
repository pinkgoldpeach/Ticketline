package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AreaService;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.util.*;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hannes on 20.05.2016.
 */
@Component
public class SellReserveTicketsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellReserveTicketsController.class);

    @FXML
    private Button buttonReserveTickets,buttonSellTickets,buttonAnonymousCustomer;

    @FXML
    private Label labelSelectedShow,labelShowDate,labelSeats,labelActualPriceTotal, labelCustomer;

    @FXML
    private Button buttonCancel, buttonSelectCustomer,buttonSelectSeats;

    @FXML
    private Label labelActualShowDate,labelActualShowName,labelHeader,labelActualCustomer, labelPriceTotal;

    @FXML
    private TextArea labelSelectedSeats;

    @FXML
    private AnchorPane sellReserveTicketsPane;


    @Autowired
    private CustomerService customerService;

    @Autowired
    private AreaService areaService;
    @Autowired
    private CustomerSettingsSalesPageController customerSettingsController;


    @Autowired
    private SpringFxmlLoader springFxmlLoader;


    private static NumberFormat formatter = new DecimalFormat("#0.00");
    private ShowDto show;

    /**
     * Initializes the GUI
     */
    public void initialize(){
        buttonCancel.setText(BundleManager.getBundle().getString("generic.cancel"));
        labelHeader.setText(BundleManager.getBundle().getString("sellReservePage.label.header"));
        labelHeader.setFont(Font.font(null, FontWeight.BOLD, 18));
        labelSelectedShow.setText(BundleManager.getBundle().getString("sellReservePage.label.selectedEvent"));
        labelActualShowName.setFont(Font.font(null, FontWeight.BOLD, 15));

        labelShowDate.setText(BundleManager.getBundle().getString("sellReservePage.label.showDate"));

        labelActualShowDate.setFont(Font.font(null, FontWeight.BOLD, 15));

        labelCustomer.setText(BundleManager.getBundle().getString("sellReservePage.label.customer"));

        labelActualCustomer.setFont(Font.font(null, FontWeight.BOLD, 15));

        labelActualCustomer.setText(BundleManager.getBundle().getString("sellReservePage.label.noCustomerSelected"));
        buttonSelectCustomer.setText(BundleManager.getBundle().getString("sellReservePage.button.selectCustomer"));
        //buttonCreateNewCustomer.setText(BundleManager.getBundle().getString("sellReservePage.button.createNewCustomer"));
        buttonAnonymousCustomer.setText(BundleManager.getBundle().getString("sellReservePage.button.anonymousCustomer"));
        labelSeats.setText(BundleManager.getBundle().getString("sellReservePage.label.seats"));

        labelSelectedSeats.setFont(Font.font(null, FontWeight.BOLD, 15));

        labelPriceTotal.setText(BundleManager.getBundle().getString("sellReservePage.label.priceTotal"));

        labelActualPriceTotal.setFont(Font.font(null, FontWeight.BOLD, 15));

//        labelPayingOption.setText(BundleManager.getBundle().getString("sellReservePage.label.paymentMethod"));

        //labelPayingOption.setFont(Font.font(null, FontWeight.BOLD, 15));

        buttonSellTickets.setText(BundleManager.getBundle().getString("sellReservePage.button.sellTickets"));
        buttonReserveTickets.setText(BundleManager.getBundle().getString("sellReservePage.button.reserveTickets"));

    }

    /**
     * Sets the show which to sell/reserve tickets for
     * and initializes a few GUI elements based on that
     * @param show the show
     */
    public void setShow(ShowDto show){
        this.show = show;

        if(show.getRoom().getSeatChoice()){
            buttonSelectSeats.setText(BundleManager.getBundle().getString("sellReservePage.button.selectSeats"));
            labelSelectedSeats.setText(BundleManager.getBundle().getString("sellReservePage.label.noSeatsSelected"));
            labelSelectedSeats.setPrefColumnCount(15);
            labelSeats.setText(BundleManager.getBundle().getString("sellReservePage.label.seats"));
        } else {
            buttonSelectSeats.setText(BundleManager.getBundle().getString("sellReservePage.button.selectAreas"));
            labelSelectedSeats.setText(BundleManager.getBundle().getString("sellReservePage.label.noAreasSelected"));
            labelSeats.setText(BundleManager.getBundle().getString("sellReservePage.label.areas"));
            labelSelectedSeats.setPrefColumnCount(3);
        }


        Stage stage = (Stage) buttonSelectCustomer.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                handleCancel();
            }
        });

        labelActualShowName.setText(show.getPerformance().getName());
        labelActualShowDate.setText(DateFormat.getDateFormatWithTime(show.getDateOfPerformance()));
        labelActualPriceTotal.setText("0.00 €");
    }

    /**
     * Calls the Customer-Selection window
     * @param actionEvent event
     */
    public void handleSelectCustomer(ActionEvent actionEvent) {
        LOGGER.info("select customer on salesPage clicked");
        Stage customerSettings = new Stage();
        customerSettings.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/customerSettingsSalesPage.fxml");
        customerSettings.setScene(new Scene((Parent) wrapper.getLoadedObject()));
        customerSettingsController.setChooseMode(true);
        customerSettings.setResizable(false);
        customerSettings.setTitle(BundleManager.getBundle().getString("generic.selectCustomer"));
        customerSettings.showAndWait();

        if(customerSettingsController.getChosenCustomer() != null) {
            TicketHandler.setCustomer(customerSettingsController.getChosenCustomer());
        }

        CustomerDto customer = TicketHandler.getCustomer();
        if(customer != null) {
            labelActualCustomer.setText(customer.getFirstName() + " " + customer.getLastName());
        }
    }

    /**
     * Opens the room plan
     */
    public void handleSelectSeats() {

        Stage roomOverview = new Stage();
        roomOverview.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = null;
        wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/roomOverview.fxml");
        roomOverview.setScene(new Scene((Parent) wrapper.getLoadedObject()));
        RoomOverviewController controller = (RoomOverviewController) wrapper.getController();
        controller.setShow(show);
        roomOverview.setResizable(true);
        roomOverview.setTitle(BundleManager.getBundle().getString("salesPage.roomplan"));

        roomOverview.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                if (newSceneWidth.intValue()< 1168) {
                    roomOverview.setWidth(1168);
                }
            }
        });
        roomOverview.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                if (newSceneHeight.intValue()< 620) {
                    roomOverview.setHeight(620);
                }
            }
        });

        roomOverview.showAndWait();



        String seats = TicketHandler.getSeatsRepresentation();

        labelSelectedSeats.setText(seats);
        labelActualPriceTotal.setText(""+formatter.format(TicketHandler.getTotal())+" €");

    }

    /**
     * Chooses an/the anonymous customer
     * @param actionEvent event
     */
    public void handleAnonymousCustomer(ActionEvent actionEvent) {
        List<CustomerDto> customerList = null;
        try {
            customerList = customerService.getSpecificPage(1);
            customerList = customerService.getSpecificPage(1);
        } catch (ServiceException e) {
            LOGGER.error("customerService.getSpecificPage(1) failed: "+e.getMessage());
        }
        for(CustomerDto c : customerList){
            if(c.getFirstName().equals("Anonymous")) {
                TicketHandler.setCustomer(c);
                labelActualCustomer.setText(c.getFirstName()+" "+c.getLastName());
            }
        }
    }

    /**
     * Handles the payment process. Calls the paymentOverview window
     */
    public void handleSellTickets() {

        if(TicketHandler.getCustomer() == null){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("generic.customerMissing"),BundleManager.getBundle().getString("generic.missing"),BundleManager.getBundle().getString("generic.missing"));
            return;
        }
        if(!TicketHandler.hasTickets()){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("generic.seatsMissing"),BundleManager.getBundle().getString("generic.seatsMissing2"),BundleManager.getBundle().getString("generic.seatsMissing2"));
            return;
        }

        Stage stage = (Stage) sellReserveTicketsPane.getScene().getWindow();
        Stage payment = new Stage();
        payment.initModality(Modality.APPLICATION_MODAL);
        SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/paymentOverview.fxml");
        payment.setScene(new Scene((Parent) wrapper.getLoadedObject()));

        List<AreaDto> areaList = null;
        try {
            areaList = areaService.getAreasByRoom(show.getRoom());
        } catch (ServiceException e) {
            LOGGER.error("areaService.getAreasByRoom failed: "+e.getMessage());
        }

        PaymentController controller = (PaymentController) wrapper.getController();
        controller.setPrevStage(stage);
        controller.setSellMode(true);
        controller.setShow(show);
        controller.setAreaList(areaList);
        payment.setResizable(false);
        payment.setTitle(BundleManager.getBundle().getString("payment.label.payment"));
        payment.showAndWait();
    }

    /**
     * Sends the reservation via the TicketHandler and clears the TicketHandler for a new sale/reservation
     * @param actionEvent event
     */
    public void handleReserveTickets(ActionEvent actionEvent) {
        if(TicketHandler.getCustomer() == null){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("generic.customerMissing"),BundleManager.getBundle().getString("generic.missing"),BundleManager.getBundle().getString("generic.missing"));
            return;
        }
        if(!TicketHandler.hasTickets()){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("generic.seatsMissing"),BundleManager.getBundle().getString("generic.seatsMissing2"),BundleManager.getBundle().getString("generic.seatsMissing2"));
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tickets");
        alert.setHeaderText(BundleManager.getBundle().getString("generic.reservationConfirmation"));
        alert.setContentText(""+TicketHandler.getSeatsRepresentation()+" "+BundleManager.getBundle().getString("generic.totallingAt")+" "+formatter.format(TicketHandler.getTotal())+" €");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            List<AreaDto> areaList = null;
            try {
                areaList = areaService.getAreasByRoom(show.getRoom());
            } catch (ServiceException e) {
                LOGGER.error("areaService.getAreasByRoom failed: "+e.getMessage());
            }

            try {
                if(!TicketHandler.sendReservation(show.getRoom().getSeatChoice(), show.getId(),  areaList)){
                    return;
                }
            } catch (ServiceException e) {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("locking.errorMessage"),BundleManager.getBundle().getString("locking.error"),BundleManager.getBundle().getString("generic.error"));
            }
            TicketHandler.clearTicketList();
            if(show.getRoom().getSeatChoice()) {
                TicketHandler.unlockAllSeats(show);
                TicketHandler.removeButtonWithIDs();
            }else{

            }

            Stage stage = (Stage) buttonReserveTickets.getScene().getWindow();
            stage.close();

        } else {
            return;
        }
        TicketHandler.setButtonWithIDList(null);
        TicketHandler.clearTicketList();
        TicketHandler.resetCustomerAndEmployee();
    }

    /**
     * closes sell/reservation window
     */
    public void handleCancel() {
        TicketHandler.unlockAllSeats(show);
        TicketHandler.setButtonWithIDList(null);
        TicketHandler.clearTicketList();
        TicketHandler.resetCustomerAndEmployee();

        Stage stage = (Stage) sellReserveTicketsPane.getScene().getWindow();
        stage.close();
    }
}

