package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AdminService;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.CustomAlert;
import at.ac.tuwien.inso.ticketline.client.util.PDFInvoiceCreator;
import at.ac.tuwien.inso.ticketline.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.ticketline.dto.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.*;
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
import java.util.*;

/**
 * Controller for showing all Invoices
 *
 * @author Christoph Hafner 1326088
 */
@Component
public class InvoiceController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @FXML
    private AnchorPane invoicePane;
    @FXML
    private TableView tableInvoiceView;
    @FXML
    private TableColumn<UIReceipt, String> invoiceNumberColumn, invoiceDateColumn, invoiceStatusColumn, invoicePaymentType, invoiceCancelationColumn;
    @FXML
    private Button btnCreateInvoice, btnNext, btnPrev, btnCreateCancelationInvoice;
    @FXML
    private Label pageText;
    @FXML
    private TextField pageInput;

    @Autowired
    private PDFInvoiceCreator pdfInvoiceCreator;
    @Autowired
    private SpringFxmlLoader springFxmlLoader;
    @Autowired
    private ReceiptService receiptService;

    private Stage prevStage;
    private Integer pageNumber;
    private Integer totalPages;


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        LOGGER.info("init invoice table");

        btnCreateInvoice.setText(BundleManager.getBundle().getString("invoice.createInvoice"));

        invoiceNumberColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceNumberProperty());
        invoiceDateColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceDateProperty());
        invoiceStatusColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceStatusProperty());
        invoiceCancelationColumn.setCellValueFactory(cellData -> cellData.getValue().hasCancelationProperty());
        invoicePaymentType.setCellValueFactory(cellData -> cellData.getValue().paymentTypeProperty());

        if(getTotalPages() != 0){
            pageNumber = 1;
            setPages(pageNumber);
            loadPage(pageNumber);
        } else {
            pageNumber = 0;
            totalPages = 0;
            btnNext.setDisable(true);
            btnPrev.setDisable(true);
            pageInput.setText(pageNumber.toString());
            pageText.setText("/" + 0);
        }


        btnCreateCancelationInvoice.setDisable(true);
        btnCreateInvoice.setDisable(true);

        pageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            pageChangeInput(newValue);
        });

        tableInvoiceView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(((UIReceipt) tableInvoiceView.getSelectionModel().getSelectedItem()).getCurrentStatus().equals(TransactionStateDto.CANCELLED_POSITIONS)){
                    btnCreateInvoice.setDisable(false);
                    btnCreateCancelationInvoice.setDisable(false);
                } else if(((UIReceipt) tableInvoiceView.getSelectionModel().getSelectedItem()).getCurrentStatus().equals(TransactionStateDto.CANCELED)){
                    btnCreateInvoice.setDisable(true);
                    btnCreateCancelationInvoice.setDisable(false);
                } else {
                    btnCreateInvoice.setDisable(false);
                    btnCreateCancelationInvoice.setDisable(true);
                }
            } else {
                btnCreateInvoice.setDisable(true);
                btnCreateCancelationInvoice.setDisable(true);
            }
        });

        tableInvoiceView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.invoice")));


        invoiceDateColumn.setComparator(new Comparator<String>() {

            @Override
            public int compare(String t, String t1) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date d1 = format.parse(t);
                    Date d2 = format.parse(t1);

                    return Long.compare(d1.getTime(), d2.getTime());
                } catch (ParseException p) {
                    LOGGER.error("Got Parse Exception: " + p.getMessage());
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.header.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.title.serviceException"));
                }
                return -1;

            }

        });
    }

    /**
     * Method which will be executed when the createInvoiceButton is clicked
     * This will create a new invoice and give the user a alert information,
     * weather the progress was successfull or not
     *
     * @param actionEvent event to be handled
     */
    @FXML
    private void handleCreateInvoice(ActionEvent actionEvent) {
        UIReceipt temp = (UIReceipt) tableInvoiceView.getSelectionModel().getSelectedItem();
        if (temp != null){
            Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("invoice.alert.content.confirm"),
                    BundleManager.getBundle().getString("invoice.alert.header.confirm"),
                    BundleManager.getBundle().getString("invoice.alert.title.confirm"));
            if (result.get() == ButtonType.OK) {
                try {
                    pdfInvoiceCreator.createPdf(temp.getOrigin(), getTicket(temp.getOrigin().getReceiptEntryDtos()), false);
                    CustomAlert.throwInformationWindow(BundleManager.getBundle().getString("invoice.alert.content.saved"),
                            BundleManager.getBundle().getString("invoice.alert.header.saved"),
                            BundleManager.getBundle().getString("invoice.alert.title.saved"));
                } catch (ServiceException e) {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.header.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.title.serviceException"));
                }
            }
        } else {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.noInvoice"),
                    BundleManager.getBundle().getString("invoice.alert.header.noInvoice"),
                    BundleManager.getBundle().getString("invoice.alert.title.noInvoice"));
        }

    }

    /**
     * Method which will be executed when the createCancelationInvoiceButton is clicked
     * This will create a new cancelationinvoice and give the user a alert information,
     * weather the progress was successfull or not
     *
     * @param actionEvent event to be handled
     */
    @FXML
    private void handleCreateCancelationInvoice(ActionEvent actionEvent) {
        UIReceipt temp = (UIReceipt) tableInvoiceView.getSelectionModel().getSelectedItem();
        if (temp != null) {
            Optional<ButtonType> result = CustomAlert.throwConfirmationWindow(BundleManager.getBundle().getString("invoice.alert.content.confirmCancelation"),
                    BundleManager.getBundle().getString("invoice.alert.header.confirmCancelation"),
                    BundleManager.getBundle().getString("invoice.alert.title.confirmCancelation"));
            if (result.get() == ButtonType.OK) {
                try {
                    pdfInvoiceCreator.createPdf(temp.getOrigin(), getTicket(temp.getOrigin().getReceiptEntryDtos()), true);
                    CustomAlert.throwInformationWindow(BundleManager.getBundle().getString("invoice.alert.content.savedCanceled"),
                            BundleManager.getBundle().getString("invoice.alert.header.savedCanceled"),
                            BundleManager.getBundle().getString("invoice.alert.title.savedCanceled"));
                } catch (ServiceException e) {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.header.serviceException"),
                            BundleManager.getBundle().getString("invoice.alert.title.serviceException"));
                }
            }
        } else {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.noInvoice"),
                    BundleManager.getBundle().getString("invoice.alert.header.noInvoice"),
                    BundleManager.getBundle().getString("invoice.alert.title.noInvoice"));
        }
    }

    /**
     * Helper Method which requests for each ticket id a full ticket and saves this to a Hashmap
     *
     * @param receiptEntryDtos The list of tickets in this reciepts
     * @return A HashMap of tickets with the ticketID as key
     * @throws ServiceException thrown if something goes wrong with the communication in the rest classes
     */
    private HashMap<Integer, TicketDto> getTicket(List<ReceiptEntryDto> receiptEntryDtos) throws ServiceException {
        HashMap<Integer, TicketDto> returnMap = new HashMap<>();

        for (int i = 0; i < receiptEntryDtos.size(); i++) {
            TicketDto ticketDto = receiptService.getTicket(receiptEntryDtos.get(i).getTicketId());
            returnMap.put(receiptEntryDtos.get(i).getTicketId(), ticketDto);
        }
        return returnMap;
    }

    /**
     * FXML Method which is executed when the reload button is pushed.
     * Reloads the table
     */
    @FXML
    public void handleReload() {
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
     * Sets the previous stage so a return is possible
     *
     * @param stage the previous stage
     */
    public void setStage(Stage stage) {
        prevStage = stage;
    }

    /**
     * Method which loads all receipts of the page to the table
     *
     * @param i - page which will be loaded
     */
    public void loadPage(int i) {
        ObservableList<UIReceipt> UIlist = FXCollections.observableArrayList();

        try {

            for (ReceiptDto r : receiptService.getAllReceipts(i)) {
                UIlist.add(new UIReceipt(r));
            }
        } catch (ServiceException e) {
            LOGGER.error("Got Service Exception: " + e.getMessage());
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.serviceException"),
                    BundleManager.getBundle().getString("invoice.alert.header.serviceException"),
                    BundleManager.getBundle().getString("invoice.alert.title.serviceException"));
        }

        tableInvoiceView.setItems(UIlist);
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
            return receiptService.getPageCount();
        } catch (ServiceException e) {
            CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.serviceException"),
                    BundleManager.getBundle().getString("invoice.alert.header.serviceException"),
                    BundleManager.getBundle().getString("invoice.alert.title.serviceException"));
            Stage stage = (Stage) invoicePane.getScene().getWindow();
            stage.close();
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
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.noValidNumber"),
                            BundleManager.getBundle().getString("invoice.alert.header.noValidNumber"),
                            BundleManager.getBundle().getString("invoice.alert.title.noValidNumber"));
                }
            } catch (NumberFormatException e) {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("invoice.alert.content.noValidNumber"),
                        BundleManager.getBundle().getString("invoice.alert.header.noValidNumber"),
                        BundleManager.getBundle().getString("invoice.alert.title.noValidNumber"));
            }
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
     * A private helper class which represends a receipt for the table
     * The original receipt is saved to the origin variable and can be used later on
     */
    private class UIReceipt {

        private ReceiptDto origin;
        private StringProperty invoiceNumber;
        private StringProperty invoiceDate;
        private StringProperty invoiceStatus;
        private StringProperty paymentType;
        private StringProperty hasCancelation;
        private TransactionStateDto currentStatus;

        public UIReceipt(ReceiptDto receiptDto) {

            this.origin = receiptDto;
            this.invoiceNumber = new SimpleStringProperty(String.format(String.format("%06d", receiptDto.getId())));
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            this.invoiceDate = new SimpleStringProperty((formatter.format(receiptDto.getTransactionDate())).toString());
            TransactionStateDto status = receiptDto.getTransactionState();
            this.currentStatus = status;

            if(status.equals(TransactionStateDto.CANCELLED_POSITIONS)){
                this.hasCancelation = new SimpleStringProperty("✓");
                this.invoiceStatus = new SimpleStringProperty(TransactionStateDto.PAID.toString());
            } else if(status.equals(TransactionStateDto.CANCELED)){
                this.hasCancelation = new SimpleStringProperty("✓");
                this.invoiceStatus = new SimpleStringProperty(status.toString());
            } else {
                this.hasCancelation = new SimpleStringProperty("✗");
                this.invoiceStatus = new SimpleStringProperty(status.toString());
            }

            if(receiptDto.getMethodOfPaymentDto().getStripeDto() == null){
                this.paymentType = new SimpleStringProperty(BundleManager.getBundle().getString("invoice.table.cash"));
            } else {
                this.paymentType = new SimpleStringProperty(BundleManager.getBundle().getString("invoice.table.creditcard"));
            }
        }

        public ReceiptDto getOrigin() {
            return origin;
        }

        public void setOrigin(ReceiptDto origin) {
            this.origin = origin;
        }

        public String getInvoiceNumber() {
            return invoiceNumber.get();
        }

        public StringProperty invoiceNumberProperty() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber.set(invoiceNumber);
        }

        public String getInvoiceDate() {
            return invoiceDate.get();
        }

        public StringProperty invoiceDateProperty() {
            return invoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
            this.invoiceDate.set(invoiceDate);
        }

        public String getInvoiceStatus() {
            return invoiceStatus.get();
        }

        public String getPaymentType() {
            return paymentType.get();
        }

        public StringProperty paymentTypeProperty() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType.set(paymentType);
        }

        public StringProperty invoiceStatusProperty() {
            return invoiceStatus;
        }

        public void setInvoiceStatus(String invoiceStatus) {
            this.invoiceStatus.set(invoiceStatus);
        }

        public String getHasCancelation() {
            return hasCancelation.get();
        }

        public StringProperty hasCancelationProperty() {
            return hasCancelation;
        }

        public void setHasCancelation(String hasCancelation) {
            this.hasCancelation.set(hasCancelation);
        }

        public TransactionStateDto getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(TransactionStateDto currentStatus) {
            this.currentStatus = currentStatus;
        }
    }

}
