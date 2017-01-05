package at.ac.tuwien.inso.ticketline.client.gui.controller;


import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.client.util.*;
import at.ac.tuwien.inso.ticketline.dto.*;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Controller for performing Stripe creditcard payments
 */
@Component
public class PaymentController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @FXML
    private AnchorPane paymentPane;
    @FXML
    private TextField creditcardNumber, exp_month, exp_year, cvc;
    @FXML
    private Button btnPay, btnCancel;
    @FXML
    private Label labelCard, labelExpires, labelCvc;
    @FXML
    private ChoiceBox<String> choicePayment;

    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private StripePayment stripePayment;
    @Autowired
    private TicketsOverviewController ticketsOverviewController;
    @Autowired
    private ReceiptService receiptService;

    private Stage prevStage;
    private ShowDto show;
    private boolean sellMode = false;
    private ReservationDto reservation;
    private List<AreaDto> areaList;


    private static NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        ObservableList paymentTypes = FXCollections.observableArrayList();
        paymentTypes.add(BundleManager.getBundle().getString("payment.type.cash"));
        paymentTypes.add(BundleManager.getBundle().getString("payment.type.creditCard"));
        choicePayment.setItems(paymentTypes);
        choicePayment.getSelectionModel().select(0);
        choicePayment.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> changedValue(newValue));
        changedValue(BundleManager.getBundle().getString("payment.type.cash"));
        btnPay.setText(BundleManager.getBundle().getString("payment.button.buy"));
        btnCancel.setText(BundleManager.getBundle().getString("payment.button.cancel"));

    }

    /**
     * Sets the Mode of this controller, weather it handels a reservation payment or a direct one
     * @param sellMode true, if direct payment, false otherwise
     */
    public void setSellMode(Boolean sellMode){
        this.sellMode = sellMode;
    }

    /**
     * Shows the right form-fields depending on the currently selected value
     *
     * @param newValue the currently selected value of the choiceBox
     */
    private void changedValue(String newValue) {
        if (newValue.equals((BundleManager.getBundle().getString("payment.type.cash")))) {
            labelCard.setVisible(false);
            labelCvc.setVisible(false);
            labelExpires.setVisible(false);
            creditcardNumber.setVisible(false);
            exp_month.setVisible(false);
            exp_year.setVisible(false);
            cvc.setVisible(false);
        } else if (newValue.equals((BundleManager.getBundle().getString("payment.type.creditCard")))) {
            labelCard.setVisible(true);
            labelCard.setText(BundleManager.getBundle().getString("payment.label.creditCardNumber"));
            labelCvc.setVisible(true);
            labelCvc.setText(BundleManager.getBundle().getString("payment.label.cvc"));
            labelExpires.setVisible(true);
            labelExpires.setText(BundleManager.getBundle().getString("payment.label.expirationDate"));
            creditcardNumber.setVisible(true);
            exp_month.setVisible(true);
            exp_year.setVisible(true);
            cvc.setVisible(true);
        }
    }

    /**
     * Sets the previous stage for further use
     *
     * @param stage - The last stage
     */
    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    /**
     * Sets the reservation if the controller should sell reservated tickets
     *
     * @param reservation - The reservationDTO with the tickets to sell
     */
    public void setReservation(ReservationDto reservation){
        this.reservation = reservation;
    }

    /**
     * Handels the close of the current window
     *
     * @param actionEvent - Event to be handled
     */
    public void handleCancel(ActionEvent actionEvent) {
        if(sellMode) {
            prevStage.show();
            prevStage.toFront();
        }
        Stage stage = (Stage) paymentPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the show if the controller should sell directly tickets
     *
     * @param show - Show for which the tickets will be sold
     */
    public void setShow(ShowDto show) {
        this.show = show;
    }
    public void setAreaList(List<AreaDto> areaList) {
        this.areaList = areaList;
    }

    /**
     * Method, which is executed when the user clicks the buy button
     * Depending on the selected method, the payment will be executed
     * Closes all windows when successful
     *
     * @param actionEvent - Event to be handled
     */
    public void handlePay(ActionEvent actionEvent) {
        Long creditcardNumberLong = 0L;
        Integer cvcInt = 0;
        Integer yearInt = 0;
        Integer monthInt = 0;

        try {
            creditcardNumberLong = Long.parseLong(creditcardNumber.getText());
            cvcInt = Integer.parseInt(cvc.getText());
            yearInt = Integer.parseInt(exp_year.getText());
            monthInt = Integer.parseInt(exp_month.getText());
        } catch (NumberFormatException e) {
            LOGGER.error("Value wrong");
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("payment.alert.valueMessage"),
                    BundleManager.getBundle().getString("payment.alert.value"),
                    BundleManager.getBundle().getString("payment.alert.value"));
            return;
        }

        String text = "";
        if(sellMode) {
           text = "" + TicketHandler.getSeatsRepresentation() + " \n" + BundleManager.getBundle().getString("generic.totallingAt") + " " + formatter.format(TicketHandler.getTotal()) + " €";
        } else {
            double total = 0;
            for(int i = 0; i < reservation.getTickets().size(); i++){
                if(reservation.getTickets().get(i).getValid()) {
                    total += reservation.getTickets().get(i).getPrice();
                }
            }
            text = BundleManager.getBundle().getString("payment.alert.buy") + " " +
                    reservation.getTickets().size() + " " + BundleManager.getBundle().getString("payment.field.ticketsBuy") + " \n" + BundleManager.getBundle().getString("generic.totallingAt") + " " + formatter.format(total) + " €";
        }

        Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(text,
                BundleManager.getBundle().getString("generic.saleConfirmation"),
                BundleManager.getBundle().getString("payment.field.tickets"));
        if (result.get() == ButtonType.OK) {
            Token token = null;
            try {
                if ((BundleManager.getBundle().getString("payment.type.creditCard")).equals(choicePayment.getValue())) {
                    if(sellMode){
                        token = stripePayment.getToken(creditcardNumberLong, cvcInt, yearInt, monthInt, TicketHandler.getCustomer());
                    } else {
                        token = stripePayment.getToken(creditcardNumberLong, cvcInt, yearInt, monthInt, reservation.getCustomer());
                    }
                }
            } catch (StripeException ex){
                LOGGER.error("StripeException " + ex.getMessage());
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("payment.alert.invalidInput"),
                        BundleManager.getBundle().getString("payment.alert.stripeHeader"),
                        BundleManager.getBundle().getString("payment.alert.stripeException"));
                return;
            }

            try {
                if(sellMode) {
                    boolean success = false;
                    if (!TicketHandler.checkIfEnoughAreaTicketsAvailable(show.getId(), areaList))
                        return;
                    success = TicketHandler.sendTicketsAndReceipt(token, show, areaList);


                    TicketHandler.unlockAllSeats(show);
                    TicketHandler.removeButtonWithIDs();

                    TicketHandler.setButtonWithIDList(null);
                    TicketHandler.clearTicketList();
                    TicketHandler.resetCustomerAndEmployee();
                    prevStage.close();
                    if(!success)
                        return;
                } else { //Reservation
                    ReceiptDto receipt = new ReceiptDto();
                    receipt.setTransactionDate(new Date());
                    receipt.setTransactionState(TransactionStateDto.PAID);
                    receipt.setPerformanceName(reservation.getTickets().get(0).getShow().getPerformance().getName());

                    if (token != null) {
                        MethodOfPaymentDto methodOfPaymentDto = new MethodOfPaymentDto();
                        StripeDto mop = new StripeDto();
                        mop.setToken(token.getId());
                        methodOfPaymentDto.setStripeDto(mop);
                        receipt.setMethodOfPaymentDto(methodOfPaymentDto);
                    } else {
                        receipt.setMethodOfPaymentDto(new MethodOfPaymentDto());
                    }

                    List<ReceiptEntryDto> resEntries = new ArrayList<>();
                    receipt.setCustomerDto(reservation.getCustomer());
                    for(TicketDto t : reservation.getTickets()){
                        ReceiptEntryDto resEntry = new ReceiptEntryDto();
                        resEntry.setTicketId(t.getId());
                        resEntry.setAmount(1);
                        resEntry.setUnitPrice(t.getPrice());
                        resEntry.setPosition(1);
                        resEntries.add(resEntry);
                    }
                    receipt.setReceiptEntryDtos(resEntries);

                    receiptService.saveReceipt(receipt);

                    ticketsOverviewController.handlePaymentSuccess();
                }
                Stage stage = (Stage) paymentPane.getScene().getWindow();
                stage.close();

                LOGGER.info("Successful payment");
                CustomAlert.throwInformationWindow(BundleManager.getBundle().getString("payment.alert.successMessage"),
                        BundleManager.getBundle().getString("payment.alert.success"),
                        BundleManager.getBundle().getString("payment.alert.successful"));
            } catch (ServiceException ex) {
                LOGGER.error(ex.getMessage() + "Service Receipt");
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("payment.alert.problemCard"),
                        BundleManager.getBundle().getString("payment.alert.serviceHeader"),
                        BundleManager.getBundle().getString("payment.alert.serviceException"));
            }
        } else {
            return;
        }
    }

}
