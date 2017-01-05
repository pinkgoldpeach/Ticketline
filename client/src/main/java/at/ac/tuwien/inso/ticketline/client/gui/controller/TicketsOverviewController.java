package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;

import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.client.service.ReservationService;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.*;

import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;

import at.ac.tuwien.inso.ticketline.dto.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for the find Reservations window
 */
@Component
public class TicketsOverviewController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketsOverviewController.class);

    final private ToggleGroup group = new ToggleGroup();

    @FXML
    public Rectangle rectReservations,rectCustomer;

    @FXML
    private Button buttonCancelReservation,buttonBuyReservation,buttonCancelTicket;

    @FXML
    private RadioButton radioFindByCustomer,radioTickets,radioReservation,radioFindByReservationNo;

    @FXML
    private Label labelTickets,labelCancellationReason,labelTickets1;

    @FXML
    private TextField fieldReservationno,fieldPerformance,fieldCancellationReason,fieldCustomername;

    @FXML
    private TableColumn<UIReservation, String> columnFirstname;
    @FXML
    private TableColumn<UIReservation, Number> columnNumberOfTickets;
    @FXML
    private TableColumn<UITicket, String> columnDate,columnTicketPlace;
      @FXML
    private TableView<UITicket> tableViewTickets;
    @FXML
    private TableView<UIReservation> tableViewReservations;
    @FXML
    private TableColumn<UIReservation, String> columnLastname;
    @FXML
    private TableColumn<UITicket, Number> columnTicketPrice;
    @FXML
    private TableColumn<UIReservation, String> columnPerformanceName1;
    @FXML
    private TableColumn<UITicket, String> columnPerformanceName2;
    @FXML
    private TableColumn<UITicket, String> columnCustomerName;


    private final ToggleGroup group1 = new ToggleGroup();

    private final ToggleGroup group2 = new ToggleGroup();


    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReceiptService receiptService;


    @Autowired
    private TicketService ticketService;

    private boolean searchTickets; //choice between ticket and reservation (through customer and performance)
    private boolean searchReservationNo; //choice between reservation number or customer and performance
    private boolean ticketReload = false;
    private boolean lastTicketRemoved = false;
    private int row = 0;

    /**
     * Init all labels, fields and setup validation support
     */
    public void initialize(URL url, ResourceBundle resBundle) {
        tableViewReservations.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.reservation")));
        tableViewTickets.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.tickets")));

        initLabelsAndProperties();

        initToggleListeners();

        initFieldListeners();

        initTableViewListeners();

        searchTickets = false;
        buttonCancelReservation.setDisable(true);
        buttonBuyReservation.setDisable(true);
        radioReservation.setSelected(true);
        rectCustomer.setVisible(false);
        rectReservations.setVisible(true);
        searchReservationNo = true;
        radioFindByReservationNo.setSelected(true);

        buttonCancelTicket.setDisable(true);
        fieldCancellationReason.setDisable(true);
        fieldCancellationReason.setText("");

       columnDate.setComparator(new Comparator<String>() {

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

    }


    private void initLabelsAndProperties() {
        radioFindByCustomer.setToggleGroup(group);
        radioFindByReservationNo.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {tableViewReservations.getItems().removeAll();
                   tableViewTickets.getItems().removeAll();
                }
            }
        });

        labelCancellationReason.setText((BundleManager.getBundle().getString("findTicket.label.cancel")));
        labelTickets.setText((BundleManager.getBundle().getString("findTicket.label.tickets")));
        buttonBuyReservation.setText((BundleManager.getBundle().getString("findTicket.button.buyreservation")));
        columnFirstname.setText((BundleManager.getBundle().getString("findTicket.column.firstname")));
        columnNumberOfTickets.setText((BundleManager.getBundle().getString("findTicket.column.numberoftickets")));
        columnDate.setText((BundleManager.getBundle().getString("findTicket.column.date")));
        columnTicketPlace.setText((BundleManager.getBundle().getString("findticket.column.ticketplace")));
        buttonCancelTicket.setText((BundleManager.getBundle().getString("findTicket.button.cancelticket")));
        buttonCancelReservation.setText(BundleManager.getBundle().getString("findTicket.button.cancelreservation"));
        labelTickets1.setText((BundleManager.getBundle().getString("findTicket.label.tickets1")));
        columnLastname.setText((BundleManager.getBundle().getString("findTicket.column.lastname")));
        columnTicketPrice.setText((BundleManager.getBundle().getString("findTicket.column.price")));
        columnPerformanceName1.setText((BundleManager.getBundle().getString("findTicket.column.performancename")));
        columnPerformanceName2.setText((BundleManager.getBundle().getString("findTicket.column.performancename")));
        columnCustomerName.setText((BundleManager.getBundle().getString("findticket.column.customername")));

        radioFindByCustomer.setText(BundleManager.getBundle().getString("findTicket.radio.customer"));
        radioFindByReservationNo.setText(BundleManager.getBundle().getString("findTicket.radio.reservationno"));
        radioReservation.setText(BundleManager.getBundle().getString("findTicket.radio.reservation"));
        radioTickets.setText(BundleManager.getBundle().getString("findTicket.radio.tickets"));

        fieldCustomername.setPromptText(BundleManager.getBundle().getString("generic.lastname"));
        fieldPerformance.setPromptText(BundleManager.getBundle().getString("generic.performance"));
        fieldReservationno.setPromptText(BundleManager.getBundle().getString("findby.reservationno"));

        fieldCustomername.setDisable(true);
        fieldPerformance.setDisable(true);

        radioFindByCustomer.setToggleGroup(group1);
        radioFindByReservationNo.setToggleGroup(group1);
        radioFindByCustomer.setUserData("findByCustomer");
        radioFindByReservationNo.setUserData("findByReservationNo");

        radioReservation.setToggleGroup(group2);
        radioTickets.setToggleGroup(group2);
        radioReservation.setUserData("reservation");
        radioTickets.setUserData("tickets");

        columnFirstname.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        columnLastname.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        columnPerformanceName1.setCellValueFactory(cellData -> cellData.getValue().showDescriptionProperty());
        columnNumberOfTickets.setCellValueFactory(cellData -> cellData.getValue().numberOfTicketsProperty());

        columnCustomerName.setCellValueFactory(cellData -> cellData.getValue().getCustomerName());
        columnPerformanceName2.setCellValueFactory(cellData -> cellData.getValue().performanceNameProperty());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        columnTicketPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        columnTicketPlace.setCellValueFactory(cellData -> cellData.getValue().getPlaceProperty());

        tableViewTickets.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE);
    }

    public void initToggleListeners(){
        group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group1.getSelectedToggle() != null) {
                    String searchType = new_toggle.getUserData().toString();
                    if (searchType.equals("findByReservationNo")) {

                        searchReservationNo = true;
                        tableViewReservations.setDisable(false);

                        fieldCustomername.setText("");
                        fieldPerformance.setText("");
                        fieldReservationno.setText("");
                        fieldCustomername.setPromptText(BundleManager.getBundle().getString("generic.lastname"));
                        fieldPerformance.setPromptText(BundleManager.getBundle().getString("generic.performance"));
                        fieldReservationno.setPromptText(BundleManager.getBundle().getString("findby.reservationno"));

                        fieldReservationno.setDisable(false);
                        fieldCustomername.setDisable(true);
                        fieldPerformance.setDisable(true);
                        radioReservation.setDisable(true);
                        radioTickets.setDisable(true);
                        radioReservation.setDisable(true);

                        tableViewReservations.setItems(null);
                        tableViewTickets.setItems(null);
                        rectReservations.setVisible(true);
                        rectCustomer.setVisible(false);

                    } else if (searchType.equals("findByCustomer")) {
                        searchReservationNo = false;
                        if(searchTickets){
                            LOGGER.info("isDiabled: "+tableViewReservations.isDisabled());
                            tableViewReservations.setDisable(true);
                            int a = 1+3;
                        }
                        else{
                            updateReservationTable();
                        }

                        rectReservations.setVisible(false);
                        rectCustomer.setVisible(true);
                        fieldCustomername.setText("");
                        fieldPerformance.setText("");
                        fieldReservationno.setText("");
                        fieldCustomername.setPromptText(BundleManager.getBundle().getString("generic.lastname"));
                        fieldPerformance.setPromptText(BundleManager.getBundle().getString("generic.performance"));
                        fieldReservationno.setPromptText(BundleManager.getBundle().getString("findby.reservationno"));

                        fieldReservationno.setDisable(true);
                        fieldCustomername.setDisable(false);
                        fieldPerformance.setDisable(false);
                        radioReservation.setDisable(false);
                        radioTickets.setDisable(false);
                        radioReservation.setDisable(false);

                        tableViewReservations.setItems(null);
                        tableViewTickets.setItems(null);
                    }
                }
            }
        });

        group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group2.getSelectedToggle() != null) {
                    String searchType = new_toggle.getUserData().toString();
                    if (searchType.equals("tickets")) {
                        searchTickets = true;
                        fieldCustomername.setText("");
                        fieldPerformance.setText("");

                        tableViewReservations.setDisable(true);
                        buttonBuyReservation.setDisable(true);
                        buttonCancelReservation.setDisable(true);

                        tableViewReservations.setItems(null);

                        updateTicketTable();

                    } else if (searchType.equals("reservation")) {
                        searchTickets = false;
                        tableViewTickets.setItems(null);
                        //buttonCancelReservation.setDisable(false);
                        //buttonBuyReservation.setDisable(false);
                        fieldCustomername.setText("");
                        fieldPerformance.setText("");

                        tableViewReservations.setDisable(false);
                        tableViewReservations.setItems(null);
                        updateReservationTable();
                    }
                }
            }
        });
    }

    public void initFieldListeners(){
        fieldCustomername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                updateReservationTable();
            } else {
                tableViewReservations.setItems(null);
                tableViewTickets.setItems(null);
            }
        });
        fieldPerformance.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                updateReservationTable();
            } else {
                tableViewReservations.setItems(null);
                tableViewTickets.setItems(null);
            }
        });
        fieldReservationno.textProperty().addListener((observable, oldValue, newValue) -> {
            //if (newValue.length() >= 2) {
            if (newValue.length() == 6) {
                updateReservationTable();
            } else {
                tableViewReservations.setItems(null);
                tableViewTickets.setItems(null);
            }
        });
    }

    public void initTableViewListeners(){
        tableViewReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                updateTicketTable();
                buttonBuyReservation.setDisable(false);
                buttonCancelReservation.setDisable(false);
            } else {
                buttonBuyReservation.setDisable(true);
                buttonCancelReservation.setDisable(true);
            }
        });

        tableViewTickets.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                buttonCancelTicket.setDisable(false);
                fieldCancellationReason.setDisable(false);
            } else {
                buttonCancelTicket.setDisable(true);
                fieldCancellationReason.setDisable(true);
            }
            fieldCancellationReason.setText("");
        });
    }

    private void updateReservationTable(){
        tableViewReservations.setItems(null);
        ObservableList<UIReservation> uilist = FXCollections.observableArrayList();

        if(searchReservationNo){
            ReservationDto reservation = null;

            try {
                if(!fieldReservationno.getText().trim().isEmpty()){
                reservation = reservationService.findByReservationNumber(fieldReservationno.getText());
                }
            } catch (ValidationException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.notValidInput"), BundleManager.getBundle().getString("generic.error"));
            } catch (ServiceException e) {
                LOGGER.info("Reservation Number: "+fieldReservationno.getText()+" nicht gefunden");
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("findTicket.numberNotValid"), BundleManager.getBundle().getString("search.failed"), BundleManager.getBundle().getString("generic.error"));
            }
            if(reservation!=null) {
                UIReservation uiReservation = new UIReservation(reservation);
                uilist.add(uiReservation);
            }
        } else {
            if(!searchTickets){
                List<ReservationDto> reservations = null;
                if(fieldCustomername.getText().isEmpty() && fieldPerformance.getText().isEmpty()) {//beide felder empty
                  /*  try {
                        reservations = reservationService.getAllReservations();

                    } catch (ServiceException e) {
                        LOGGER.info("Keine reservations gefunden");
                        return;
                    }
                    LOGGER.info("add all reservations");
                    for (ReservationDto reservation : reservations) {
                        UIReservation uiReservation = new UIReservation(reservation);
                        uilist.add(uiReservation);
                        //tableViewReservations.setItems(uilist);
                    }*/
                } else if (fieldPerformance.getText().isEmpty()) { //customer befüllt performance empty
                    try {
                            reservations = reservationService.findReservationByLastname(fieldCustomername.getText());

                    } catch (ValidationException e) {
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.notValidInput"), BundleManager.getBundle().getString("generic.error"));
                    } catch (ServiceException e) {
                        LOGGER.info("Keine reservations von "+fieldCustomername.getText()+" gefunden");
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("validation.invalidCustomername"), BundleManager.getBundle().getString("generic.error"));
                    }

                    if (reservations!=null) {
                        for (ReservationDto reservation : reservations) {
                            if (reservation.getCustomer().getLastName().toLowerCase().contains(fieldCustomername.getText().toLowerCase())) {
                                UIReservation uiReservation = new UIReservation(reservation);
                                uilist.add(uiReservation);

                            }
                        }
                    }
                } else if (fieldCustomername.getText().isEmpty()) { //performance befüllt, customer empty
                    try {
                        reservations = reservationService.findReservationByPerformanceName(fieldPerformance.getText());
                    } catch (ValidationException e) {
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.notValidInput"), BundleManager.getBundle().getString("generic.error"));
                    } catch (ServiceException e) {
                        LOGGER.info("Keine reservations gefunden");
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("validation.invalidCustomername"), BundleManager.getBundle().getString("generic.error"));
                    }

                    if (reservations!=null) {
                        for (ReservationDto reservation : reservations) {
                            String resTemp = reservation.getTickets().get(0).getShow().getPerformance().getName().toLowerCase();
                            if (resTemp.contains(fieldPerformance.getText().toLowerCase())) {
                                UIReservation uiReservation = new UIReservation(reservation);
                                uilist.add(uiReservation);

                            }
                        }
                    }
                } else { //beide felder befüllt
                    try {
                        reservations = reservationService.findReservationByLastname(fieldCustomername.getText());

                    } catch (ValidationException e) {
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.notValidInput"), BundleManager.getBundle().getString("generic.error"));
                    } catch (ServiceException e) {
                        LOGGER.info("Keine reservations von "+fieldCustomername.getText()+" gefunden");
                        CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("validation.invalidCustomername"), BundleManager.getBundle().getString("generic.error"));
                    }

                    if (reservations!=null) {
                        for (ReservationDto reservation : reservations) {
                            if (reservation.getCustomer().getLastName().toLowerCase().contains(fieldCustomername.getText().toLowerCase()) &&
                                    reservation.getTickets().get(0).getShow().getPerformance().getName().toLowerCase().contains(fieldPerformance.getText().toLowerCase())) {
                                UIReservation uiReservation = new UIReservation(reservation);
                                uilist.add(uiReservation);

                            }
                        }
                    }
                }

            } else {
                updateTicketTable();
            }
        }
        tableViewReservations.setItems(uilist);
        if(ticketReload && !lastTicketRemoved){
            tableViewReservations.requestFocus();
            tableViewReservations.getSelectionModel().select(0);
            tableViewReservations.getFocusModel().focus(0);
            ticketReload = false;
        }
    }

    private void updateTicketTable(){
        tableViewTickets.setItems(null);

        ObservableList<UITicket> uilist = FXCollections.observableArrayList();

        if(searchReservationNo || !searchTickets){
            ReservationDto reservationDto = null;

            if(null != tableViewReservations.getSelectionModel().getSelectedItem()){
                reservationDto = tableViewReservations.getSelectionModel().getSelectedItem().getOrigin();
                for(TicketDto t : reservationDto.getTickets()){
                    UITicket uit = new UITicket(t);
                    uit.addCustomer(reservationDto);
                    uilist.add(uit);
                    tableViewTickets.setItems(uilist);
                }
            }
        } else {
            if(fieldCustomername.getText().isEmpty() && fieldPerformance.getText().isEmpty()){ // both fields empty
               /* List<TicketDto> ticketlist = null;
                try {
                    ticketlist = ticketService.getAllTickets();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                for(TicketDto t : ticketlist ){
                    if(t.getReservation() == null && t.getValid() == true) {
                        UITicket uiticket = new UITicket(t);
                        uilist.add(uiticket);
                        tableViewTickets.setItems(uilist);
                    }
                }*/
            }else if(fieldPerformance.getText().isEmpty()){ // customer filled, performance empty
                List<TicketDto> ticketlist = null;
                try {
                    ticketlist = ticketService.getAllTickets();
                } catch (ServiceException e) {
                    LOGGER.error("ticketService.getAllTickets failed: "+e.getMessage());
                }

                List<ReceiptDto> receiptlist = null;
                try {
                    receiptlist = receiptService.getAllReceipts();
                } catch (ServiceException e) {
                    LOGGER.error("receiptService.getAllReceipts failed: "+e.getMessage());
                }
                LOGGER.info("add tickets to table");
                for(TicketDto t : ticketlist){
                    if(t.getReservation() == null && t.getValid() == true) {
                        String customername = getCustomerName(receiptlist, t);
                        String fieldcustomername = fieldCustomername.getText().toLowerCase();
                        if (customername.toLowerCase().contains(fieldcustomername)) {
                            UITicket uiticket = new UITicket(t);
                            if (customername.equals("Customer")) {
                                customername = BundleManager.getBundle().getString("generic.anonym");
                            }
                            uiticket.addCustomer(customername);
                            uilist.add(uiticket);
                            tableViewTickets.setItems(uilist);
                        }
                    }
                }

            } else if(fieldCustomername.getText().isEmpty()){  //customer empty, performance filled
                List<TicketDto> ticketlist = null;
                try {
                    ticketlist = ticketService.getAllTickets();
                } catch (ServiceException e) {
                    LOGGER.error("ticketService.getAllTickets failed: "+e.getMessage());
                }

                List<ReceiptDto> receiptlist = null;
                try {
                    receiptlist = receiptService.getAllReceipts();
                } catch (ServiceException e) {
                    LOGGER.error("ticketService.getAllTickets failed: "+e.getMessage());;
                }
                LOGGER.info("add tickets to table according to performance name");
                for(TicketDto t : ticketlist){
                    if(t.getReservation() == null && t.getValid() == true) {
                        String performancename = t.getShow().getPerformance().getName().toLowerCase();
                        String fieldperformance = fieldPerformance.getText().toLowerCase();
                        if (performancename.toLowerCase().contains(fieldperformance)) {
                            UITicket uiticket = new UITicket(t);
                            uilist.add(uiticket);
                            String customername = getCustomerName(receiptlist, t);
                            if (customername.equals("Customer")) {
                                customername = BundleManager.getBundle().getString("generic.anonym");
                            }
                            uiticket.addCustomer(customername);
                            tableViewTickets.setItems(uilist);
                        }
                    }
                }

            } else { //customer filled, performance filled
                List<TicketDto> ticketlist = null;
                try {
                    ticketlist = ticketService.getAllTickets();
                } catch (ServiceException e) {
                    LOGGER.error("ticketService.getAllTickets failed: "+e.getMessage());
                }

                List<ReceiptDto> receiptlist = null;
                try {
                    receiptlist = receiptService.getAllReceipts();
                } catch (ServiceException e) {
                    LOGGER.error("receiptService.getAllReceipts failed: "+e.getMessage());
                }
                for(TicketDto t : ticketlist){
                    if(t.getReservation() == null && t.getValid() == true) {
                        String customername =getCustomerName(receiptlist, t);
                        if (customername.toLowerCase().contains(fieldCustomername.getText().toLowerCase())) {
                            if (t.getShow().getPerformance().getName().toLowerCase().contains(fieldPerformance.getText().toLowerCase())) {
                                UITicket uiticket = new UITicket(t);
                                uilist.add(uiticket);
                                if (customername.equals("Customer")) {
                                    customername = BundleManager.getBundle().getString("generic.anonym");
                                }
                                uiticket.addCustomer(customername);
                                tableViewTickets.setItems(uilist);
                            }
                        }
                    }
                }
            }
        }
        if(tableViewTickets.getSelectionModel().getSelectedItem() == null){
            if (tableViewReservations.getSelectionModel().getSelectedItems()==null) {
                buttonCancelReservation.setDisable(true);
                buttonBuyReservation.setDisable(true);
            }
            buttonCancelTicket.setDisable(true);
            fieldCancellationReason.setText("");
            fieldCancellationReason.setDisable(true);
        }
    }

    public void handleSellReservation(ActionEvent actionEvent) {
        if(null != tableViewReservations.getSelectionModel().getSelectedItem()){

            ReservationDto res = tableViewReservations.getSelectionModel().getSelectedItem().getOrigin();
            double total = 0;
            for(TicketDto t : res.getTickets()){
                total += t.getPrice();
            }

            Stage payment = new Stage();
            payment.initModality(Modality.APPLICATION_MODAL);
            SpringFxmlLoader.LoadWrapper wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/paymentOverview.fxml");
            payment.setScene(new Scene((Parent) wrapper.getLoadedObject()));

            PaymentController controller = (PaymentController) wrapper.getController();
            controller.setSellMode(false);
            payment.setResizable(false);
            controller.setReservation(res);
            payment.setTitle(BundleManager.getBundle().getString("payment.label.payment"));
            payment.showAndWait();
        }
    }

    public void handlePaymentSuccess(){
        ticketReload = true;
        row = tableViewReservations.getSelectionModel().getSelectedIndex();
        row = 0;
        lastTicketRemoved = true;
        buttonBuyReservation.setDisable(true);
        buttonCancelReservation.setDisable(true);
        buttonCancelTicket.setDisable(true);
        fieldCancellationReason.setDisable(true);
        fieldCancellationReason.setText("");
        fieldReservationno.setText("");
        updateReservationTable();
        updateTicketTable();
    }

    public void handleCancelTicket(ActionEvent actionEvent) {

        if(searchReservationNo || !searchTickets) {

            if(fieldCancellationReason.getText().isEmpty()){
                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("cancelTicket.alert.reason"),BundleManager.getBundle().getString("cancelTicket.alert.reasonMissing"),BundleManager.getBundle().getString("cancelTicket.alert.reasonMissing"));
                return;
            }
            if (tableViewTickets.getSelectionModel().getSelectedItems().size()==tableViewTickets.getItems().size()) {
                handleCancelReservation(null);
                return;
            }

            ObservableList<UITicket> ticketSelectionList = tableViewTickets.getSelectionModel().getSelectedItems();

            ArrayList<Integer> idlist = new ArrayList<>();
            CancelTicketDto cancelTicketDto = new CancelTicketDto();
            if (!ticketSelectionList.isEmpty()) {
                for (UITicket ticket : ticketSelectionList) {
                    TicketDto ticketDto = ticket.getOrigin();
                    idlist.add(ticketDto.getId());
                }
            }
            cancelTicketDto.setIds(idlist);
            cancelTicketDto.setCancellationReason(fieldCancellationReason.getText());
            cancelTicketDto.setReservationNumber(tableViewReservations.getSelectionModel().getSelectedItem().getOrigin().getReservationNumber());

            if(tableViewReservations.getSelectionModel().getSelectedItem().getNumberOfTickets() == 1){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle(BundleManager.getBundle().getString("findTicket.label.tickets1"));
                alert.setHeaderText(BundleManager.getBundle().getString("cancelReservation.alert.reservation"));
                alert.setContentText(BundleManager.getBundle().getString("cancelReservation.alert.lastReservation"));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        MessageDto msg = new MessageDto();
                        msg.setText(tableViewReservations.getSelectionModel().getSelectedItem().getOrigin().getReservationNumber());
                        msg.setType(MessageType.INFO);
                        MessageDto response = reservationService.cancelReservationByNumber(msg);

                    } catch (ServiceException e) {
                        LOGGER.error("Service Exception when canceling ticket: " + e.getMessage());
                        CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("ticketOverview.serviceException.cancelTicket"), "ServiceException", "ServiceException");
                    return;
                    }
                    ticketReload = true;
                    row = tableViewReservations.getSelectionModel().getSelectedIndex();
                    row = 0;
                    lastTicketRemoved = true;



                    updateReservationTable();
                    tableViewReservations.requestFocus();
                    tableViewTickets.getSelectionModel().select(0);
                    tableViewReservations.getFocusModel().focus(0);
                    updateTicketTable();

                    if (tableViewTickets.getItems().isEmpty()){
                        buttonBuyReservation.setDisable(true);
                        buttonCancelTicket.setDisable(true);
                        fieldCancellationReason.setDisable(true);
                        fieldCancellationReason.setText("");
                    }

                } else {
                    return;
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(BundleManager.getBundle().getString("findTicket.label.tickets1"));
                alert.setHeaderText(BundleManager.getBundle().getString("cancelTicket.alert.ticket"));
                alert.setContentText(BundleManager.getBundle().getString("cancelTicket.alert.ticketMessage"));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        reservationService.cancelReservationPosition(cancelTicketDto);
                    } catch (ServiceException e) {
                        LOGGER.error("Service Exception when canceling ticket: " + e.getMessage());
                        CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("ticketOverview.serviceException.cancelTicket"), "ServiceException", "ServiceException");
                        return;
                    }
                    ticketReload = true;
                    row = tableViewReservations.getSelectionModel().getSelectedIndex();
                    tableViewReservations.requestFocus();
                    tableViewTickets.getSelectionModel().select(0);
                    tableViewReservations.getFocusModel().focus(0);
                    updateReservationTable();
                    updateTicketTable();
                } else {
                    return;
                }
            }
        } else {
            if(fieldCancellationReason.getText().isEmpty()){
                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("cancelTicket.alert.ticket"),BundleManager.getBundle().getString("cancelTicket.alert.reason"),BundleManager.getBundle().getString("cancelTicket.alert.reasonMissing"));
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(BundleManager.getBundle().getString("findTicket.label.tickets1"));
            alert.setHeaderText(BundleManager.getBundle().getString("cancelTicket.alert.ticket"));
            alert.setContentText(BundleManager.getBundle().getString("cancelTicket.alert.ticketMessage"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CancelTicketDto canceldto = new CancelTicketDto();
                canceldto.setCancellationReason(fieldCancellationReason.getText());
                List<ReceiptDto> receiptlist = null;
                try {
                    receiptlist = receiptService.getAllReceipts();
                } catch (ServiceException e) {
                    LOGGER.error("Service Exception when canceling ticket: " + e.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("ticketOverview.serviceException.cancelTicket"), "ServiceException", "ServiceException");
                    return;
                }
                ObservableList<UITicket> ticketCancelList= tableViewTickets.getSelectionModel().getSelectedItems();

                canceldto.setReceiptID(getReceiptId(receiptlist, tableViewTickets.getSelectionModel().getSelectedItem().getOrigin()));
                List<Integer> idlist = new ArrayList<>();
                if (!ticketCancelList.isEmpty()) {
                    for (UITicket ticket: ticketCancelList){
                        idlist.add(new Integer(ticket.getOrigin().getId()));
                    }
                }

                canceldto.setIds(idlist);
                try {
                    ticketService.cancelSoldTicket(canceldto);
                } catch (ServiceException e) {
                    LOGGER.error("Service Exception when canceling ticket: " + e.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("ticketOverview.serviceException.cancelTicket"), "ServiceException", "ServiceException");
                    return;
                }
                tableViewReservations.requestFocus();
                tableViewTickets.getSelectionModel().select(0);
                tableViewReservations.getFocusModel().focus(0);
                updateTicketTable();
            }

        }
    }

    public void handleCancelReservation(ActionEvent actionEvent) {
        if(null != tableViewReservations.getSelectionModel().getSelectedItem()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(BundleManager.getBundle().getString("findTicket.label.tickets1"));
            alert.setHeaderText(BundleManager.getBundle().getString("cancelTicket.alert.ticket"));
            alert.setContentText(BundleManager.getBundle().getString("cancelTicket.alert.ticketMessage"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                MessageDto msg = new MessageDto();
                msg.setText(tableViewReservations.getSelectionModel().getSelectedItem().getOrigin().getReservationNumber());
                msg.setType(MessageType.INFO);
                try {
                    MessageDto response = reservationService.cancelReservationByNumber(msg);
                } catch (ServiceException e) {
                    LOGGER.error("Service Exception when canceling reservation: " + e.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("ticketOverview.serviceException.cancelReservation"), "ServiceException", "ServiceException");
                    return;
                }
            }
            ticketReload = true;
            row = tableViewReservations.getSelectionModel().getSelectedIndex();
            row = 0;
            lastTicketRemoved = true;
            buttonBuyReservation.setDisable(true);
            fieldCancellationReason.setDisable(true);
            fieldCancellationReason.setText("");
            fieldReservationno.setText("");
            updateReservationTable();
            updateTicketTable();

            tableViewReservations.requestFocus();
            tableViewReservations.getSelectionModel().select(0);
            tableViewReservations.getFocusModel().focus(0);
            if(tableViewReservations.getSelectionModel().getSelectedItem() == null){
                buttonBuyReservation.setDisable(true);
                buttonCancelReservation.setDisable(true);

            }
        }
    }

    private String getCustomerName(List<ReceiptDto> list, TicketDto ticket){
        for(ReceiptDto res : list){
            for(ReceiptEntryDto resentry : res.getReceiptEntryDtos()){
                if(ticket.getId() == 212 || ticket.getId() == 213 || ticket.getId() == 214){
                    int a = 1+2;
                }

                if(ticket.getId().equals(resentry.getTicketId()) && ticket.getReservation() == null){

                    return res.getCustomerDto().getLastName();
                }
            }
        }
        return "";
    }

    private int getReceiptId(List<ReceiptDto> list, TicketDto ticket){
        for(ReceiptDto res : list){
            for(ReceiptEntryDto resentry : res.getReceiptEntryDtos()){
                if(ticket.getId().equals(resentry.getTicketId()) && ticket.getReservation() == null){
                    return res.getId();
                }
            }
        }
        return 0;
    }


    /**
     * Clear search inputs
     */
    public void clearSearch() {
        fieldCustomername.setText("");
        fieldPerformance.setText("");
        fieldReservationno.setText("");
    }
}