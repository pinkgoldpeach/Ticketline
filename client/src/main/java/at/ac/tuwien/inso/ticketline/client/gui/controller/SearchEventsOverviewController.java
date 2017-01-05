package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.*;
import at.ac.tuwien.inso.ticketline.client.util.*;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.util.*;

/**
 * Controller for find Performances window
 */
@Component
public class SearchEventsOverviewController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchEventsOverviewController.class);

    @FXML
    private TableView<UIPerformance> tablePerformanceView;
    @FXML
    private TableView<UIShow> tableFinalShowView;
    @FXML
    private TableView<UIArtist> tableArtistView;
    @FXML
    private TableView<UILocation> tableLocationView;
    @FXML
    private TableView<UIShowWithPrice> tableShowView;

    //SearchTables
    @FXML
    private TableColumn<UIPerformance, String> namePerformanceColumn, descriptionPerformanceColumn, typePerformanceColumn;
    @FXML
    private TableColumn<UIPerformance, Number> durationPerformanceColumn;
    @FXML
    private TableColumn<UIArtist, String> firstnameArtistColumn, lastnameArtistColumn, descriptionArtistColumn;
    @FXML
    private TableColumn<UILocation, String> nameLocationColumn,roomColumn, streetLocationColumn, cityLocationColumn, landLocationColumn, postalLocationColumn;
    @FXML
    private TableColumn<UIShowWithPrice, String> locationShowColumn, performanceShowColumn, priceShowColumn, dateShowColumn;

    //Resulting Shows table (Enabled when user clicked on a search result)
    @FXML
    private TableColumn<UIShow, String> dateColumn, locationColumn, performanceFinalColumn,roomFinalColumn;

    @FXML
    private Label labelMaxPage;
    @FXML
    private ChoiceBox<String> choiceBoxDetails;
    @FXML
    private ChoiceBox<PerformanceTypeDto> choicePerformanceType;

    final private ToggleGroup group = new ToggleGroup();
    @FXML
    private Rectangle rectShow, rectArtist, rectPerformance, rectLocation;

    @FXML
    TextField labelPage;

    @FXML
    private TextField fieldSearchInput;

    @FXML
    private Button btnGetTickets, btnPrevPage, btnNextPage;
    @FXML
    public DatePicker datePickerToDate, datePickerFromDate;
    @FXML
    public RadioButton radioPerformance, radioArtist, radioShow, radioLocation;


    @Autowired
    private SpringFxmlLoader springFxmlLoader;


    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private ShowService showService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ArtistService artistService;


    private Integer pageNumber; //current page number for pagnation

    private ArrayList<String> performanceDetails, locationDetails, showDetails, artistDetails;

    private Tooltip tooltipBtnGetTickets = new Tooltip();
    private Tooltip tooltipSearchInput = new Tooltip();

    /**
     * Init all labels, fields and setup validation support
     *
     * @param url url of fxml file
     * @param resBundle rseource bundle of controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resBundle) {

        //init tooltip
        tooltipBtnGetTickets.setText(BundleManager.getBundle().getString("tooltip.clickGetTickets"));
        btnGetTickets.setTooltip(tooltipBtnGetTickets);
        fieldSearchInput.setTooltip(tooltipSearchInput);

        //init paginations
        initArraysForChoiceBox();
        labelMaxPage.setText("1");
        btnGetTickets.setDisable(true);

        //init table selection listeners
        tablePerformanceView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFinalShowTable(newValue, "performance"));
        tableArtistView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFinalShowTable(newValue, "artist"));
        tableLocationView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFinalShowTable(newValue, "location"));
        tableShowView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> btnGetTickets.setDisable(false));
        tableFinalShowView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleFinalShowSelection());


        //set up pagination
        btnPrevPage.setDisable(true);
        pageNumber = 1;
        labelMaxPage.setText("1");

        initTextLabelsAndProperties();

        //init choicebox listener
        choiceBoxDetails.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            pageNumber = 1;
            handleChoiceBoxChange(newValue);
        });

        //init pagechange listener
        labelPage.textProperty().addListener((observable, oldValue, newValue) -> {
            btnGetTickets.setDisable(true);
            try {
                if (radioArtist.isSelected()) {
                    setSearchViewTable(Integer.parseInt(newValue), "artist");
                } else if (radioLocation.isSelected()) {
                    setSearchViewTable(Integer.parseInt(newValue), "location");
                } else if (radioPerformance.isSelected()) {
                    setSearchViewTable(Integer.parseInt(newValue), "performance");
                } else {
                    setSearchViewTable(Integer.parseInt(newValue), "show");
                }
            } catch (NumberFormatException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("inputValidation.notValid"), BundleManager.getBundle().getString("generic.error"));
            }
        });

        //changeListener for textfield
        fieldSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                updateSearchTable();
            } else {
                tableFinalShowView.setItems(null);
            }
        });

        initRadioButtons();

        //choice box listener
        choicePerformanceType.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            updateSearchTable();
        });


        initPerformanceSearch();

        datePickerFromDate.setPromptText("dd.MM.yyyy");
        datePickerToDate.setPromptText("dd.MM.yyyy");

        DatePickerHelper.setEnhancedDatePicker(datePickerFromDate);
        DatePickerHelper.setEnhancedDatePicker(datePickerToDate);


        //Set new comparator for table to sort dates right
        Comparator<String> newComparator = (t, t1) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date d1 = format.parse(t);
                Date d2 = format.parse(t1);

                return Long.compare(d1.getTime(), d2.getTime());
            } catch (ParseException p) {
                p.printStackTrace();
            }
            return -1;

        };

        dateColumn.setComparator(newComparator);
        dateShowColumn.setComparator(newComparator);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                tableFinalShowView.setItems(null);
                String searchType = new_toggle.getUserData().toString();
                tableFinalShowView.getSelectionModel().clearSelection();
                tableShowView.getSelectionModel().clearSelection();
                tableArtistView.getSelectionModel().clearSelection();
                tableLocationView.getSelectionModel().clearSelection();
                tablePerformanceView.getSelectionModel().clearSelection();
                btnGetTickets.setDisable(true);
                if (searchType.equals("performance")) {
                    initPerformanceSearch();
                } else if (searchType.equals("artist")) {
                    initArtistSearch();
                } else if (searchType.equals("location")) {
                    initLocationSearch();
                } else //show {
                    initShowSearch();
            }
        });

        //init placeholder
        tableFinalShowView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.show")));
        tableLocationView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.location")));
        tablePerformanceView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.performance")));
        tableShowView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.show")));
        tableArtistView.setPlaceholder(new Label(BundleManager.getBundle().getString("table.placeholder.artist")));
    }




    /**
     * Disables/Enables button if selection is made
     */
    private void handleFinalShowSelection() {
        if (tableFinalShowView.getItems()!=null && tableFinalShowView.getSelectionModel().getSelectedItem()!=null) {
            btnGetTickets.setDisable(false);
        } else {
            btnGetTickets.setDisable(true);
        }
    }

    /**
     * Cache Show List at the beginning
     */
    public void cacheData(){
        try {
            showService.loadData();
        } catch (ServiceException e) {
            LOGGER.error("failed to initialise Data");
        }
    }

    /**
     * Changes visibility of labels and buttons, depending on the current value of the choicebox
     *
     * @param value new value in choicebox
     */
    private void handleChoiceBoxChange(String value) {
        LOGGER.info("Selection changed");
        tableFinalShowView.getSelectionModel().clearSelection();
        tableShowView.getSelectionModel().clearSelection();
        tableArtistView.getSelectionModel().clearSelection();
        tableLocationView.getSelectionModel().clearSelection();
        tablePerformanceView.getSelectionModel().clearSelection();
        fieldSearchInput.setText("");
        btnGetTickets.setDisable(true);
        if (value != null) {
            if (radioArtist.isSelected()) {
                LOGGER.info("Radio search");
                if (value.equals(BundleManager.getBundle().getString("generic.all"))) {
                    fieldSearchInput.setVisible(false);
                    pageNumber = 1;
                    labelMaxPage.setText("/ " + String.valueOf(getTotalPages("artist")));

                    setSearchViewTable(pageNumber, "artist");
                    setPagesVisible(true);

                } else {
                    fieldSearchInput.setVisible(true);
                    setPagesVisible(false);
                    if (value.equals(BundleManager.getBundle().getString("generic.name"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.name"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.name"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    }


                }

            } else if (radioPerformance.isSelected()) {
                LOGGER.info("Performance search");
                if (value.equals(BundleManager.getBundle().getString("generic.type"))) {
                    fieldSearchInput.setVisible(false);
                    choicePerformanceType.setVisible(true);

                    setPagesVisible(false);

                } else if (value.equals(BundleManager.getBundle().getString("generic.all"))) {
                    choicePerformanceType.setVisible(false);
                    fieldSearchInput.setVisible(false);

                    labelMaxPage.setText("/ " + String.valueOf(getTotalPages("performance")));
                    setPagesVisible(true);
                    setSearchViewTable(1, "performance");
                } else {
                    fieldSearchInput.setVisible(true);
                    choicePerformanceType.setVisible(false);

                    if (value.equals(BundleManager.getBundle().getString("generic.name"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.performancename"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.name"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    } else { //duration
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.duration"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.duration"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    }
                    setPagesVisible(false);
                }
            } else if (radioLocation.isSelected()) { //locations
                LOGGER.info("locations search");
                if (value.equals(BundleManager.getBundle().getString("generic.all"))) {
                    fieldSearchInput.setVisible(false);


                    labelMaxPage.setText("/ " + String.valueOf(getTotalPages("location")));
                    setSearchViewTable(1, "location");
                    setPagesVisible(true);
                } else {
                    fieldSearchInput.setVisible(true);



                    //Set Tooltips
                    if (value.equals(BundleManager.getBundle().getString("generic.name"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.lcoationName"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.name"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    } else if (value.equals(BundleManager.getBundle().getString("generic.street"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.street"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.street"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    } else if (value.equals(BundleManager.getBundle().getString("generic.city"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.city"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.city"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    } else if (value.equals(BundleManager.getBundle().getString("generic.land"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.land"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.land"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    } else if (value.equals(BundleManager.getBundle().getString("generic.roomname"))) {
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.room"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.roomname"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    }else { //postal
                        tooltipSearchInput = new Tooltip();
                        tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.postal"));
                        fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.postal"));
                        fieldSearchInput.setTooltip(tooltipSearchInput);
                    }
                    setPagesVisible(false);
                }
            } else { //show is selected
                LOGGER.info("show search");
                datePickerToDate.setValue(null);
                datePickerFromDate.setValue(null);
                if (value.equals(BundleManager.getBundle().getString("generic.dateFrom"))) {
                    labelMaxPage.setText("/ " + String.valueOf(getTotalPages("show")));

                    datePickerFromDate.setVisible(true);
                    datePickerToDate.setVisible(false);

                    fieldSearchInput.setVisible(false);

                    setPagesVisible(false);


                } else if (value.equals(BundleManager.getBundle().getString("generic.all"))) {

                    datePickerFromDate.setVisible(false);
                    datePickerToDate.setVisible(false);

                    labelMaxPage.setText("/ " + String.valueOf(getTotalPages("show")));

                    pageNumber = 1;
                    setSearchViewTable(pageNumber, "show");
                    fieldSearchInput.setVisible(false);
                    setPagesVisible(true);


                } else if (value.equals(BundleManager.getBundle().getString("generic.dateFromTo"))) {

                    datePickerToDate.setVisible(true);
                    datePickerFromDate.setVisible(true);



                    fieldSearchInput.setVisible(false);
                    labelMaxPage.setText("1");
                    setPagesVisible(false);


                } else { //filter by price

                    datePickerFromDate.setVisible(false);
                    datePickerToDate.setVisible(false);
                    tooltipSearchInput = new Tooltip();
                    tooltipSearchInput.setText(BundleManager.getBundle().getString("tooltip.price"));
                    fieldSearchInput.setPromptText(BundleManager.getBundle().getString("generic.price"));
                    fieldSearchInput.setTooltip(tooltipSearchInput);
                    fieldSearchInput.setVisible(true);
                    setPagesVisible(false);
                }
            }
        }
    }


    /**
     * Fill data table with all UI Objects of given pagenumber and given searchtype
     *
     * @param page       current pagination page
     * @param searchType type of search service to be used. either "performance" "artist", "location" or "show"
     */
    private void setSearchViewTable(int page, String searchType) {
        LOGGER.info("update table. Get " + searchType + " page " + page);

        try {
            if (searchType.equals("performance")) {
                ObservableList<UIPerformance> UIlist = FXCollections.observableArrayList();
                for (PerformanceDto e : performanceService.getSpecificPage(page)) {
                    UIlist.add(new UIPerformance(e));
                }
                tablePerformanceView.setItems(UIlist);
            } else if (searchType.equals("artist")) {
                ObservableList<UIArtist> UIlist = FXCollections.observableArrayList();
                for (ArtistDto e : artistService.getSpecificPage(page)) {
                    UIlist.add(new UIArtist(e));
                }
                tableArtistView.setItems(UIlist);
            } else if (searchType.equals("location")) {
                ObservableList<UILocation> UIlist = FXCollections.observableArrayList();
                for (LocationDto e : locationService.getSpecificPage(page)) {
                    UIlist.add(new UILocation(e));
                }
                tableLocationView.setItems(UIlist);
            } else {
                tableShowView.setItems(showService.getCachedMap().get(page));

            }

        } catch (ServiceException e) {
            CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
        }

        //set prev button if page 1
        if (page == 1) {
            btnPrevPage.setDisable(true);
            labelPage.setText("1");
        }
        btnNextPage.setDisable(false);
    }


    /**
     * Update Tableview based on searchtype, depending on current filter settings, opens a alert message if input is not valid or
     * connection to database failed
     */
    private void updateSearchTable() {
        LOGGER.info("Updating Searchtables");

        if (isValid()) {

            try {
                if (radioPerformance.isSelected()) {
                    setPerformanceTable();

                } else if (radioArtist.isSelected()) {
                    setArtistTable();


                } else if (radioLocation.isSelected()) {
                    setLocationTable();

                } else {
                    setShowTable();
                }

                btnNextPage.setDisable(true);
                fieldSearchInput.requestFocus();

                btnPrevPage.setDisable(true);
            } catch (ValidationException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.notValidInput"), BundleManager.getBundle().getString("generic.error"));
            } catch (ServiceException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.failed"), BundleManager.getBundle().getString("generic.error"));
            } catch (NumberFormatException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("inputValidation.missing"), BundleManager.getBundle().getString("generic.error"));
            }


        }
    }


    /**
     * Update Final Show table, is called when user selects an object in the table view (performance, artist or location table)
     * Final Show Table will display ALL shows which will be found by object
     *
     * @param object     current selected object (either UIPerformance, UIArtist or UIShow)
     * @param searchType either "performance" "artist" or "location", to figure out what service should be called
     */
    private void updateFinalShowTable(UIObject object, String searchType) {
        LOGGER.info("update final show table");
        if (object != null) {
            btnGetTickets.setDisable(true);
            tableFinalShowView.setDisable(false);
            ObservableList<UIShow> UIlist = FXCollections.observableArrayList();
            LOGGER.debug(searchType + " ID:" + object.getID());
            List<ShowDto> showList = new ArrayList<>();
            try {

                if (searchType.equals("performance")) {
                    showList = showService.getShowByPerformance(object.getID());
                } else if (searchType.equals("artist")) {
                    showList = showService.getShowByArtist(object.getID());
                } else if (searchType.equals("location")) {
                    showList = showService.getShowByLocation(object.getID());
                } else {
                    LOGGER.error("Searchtype undefined");
                    tableShowView.setDisable(true);
                }


                for (ShowDto e : showList) {
                    if (e.getDateOfPerformance().after(new Date())) {
                        UIlist.add(new UIShow(e));
                    }
                }

            } catch (ServiceException e) {
                CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
            }
            tableFinalShowView.setItems(UIlist);

        } else {
            LOGGER.error("Selected " + searchType + " is null");
            tableFinalShowView.setDisable(true);
        }
    }

    /**
     * Is called if user triggers the "get Tickets" button. Should forward to another window to sell/reserve tickets.
     * Opens an alertbox if no show is selected in showview.
     */
    @FXML
    public void handleGetTickets() {
        LOGGER.info("handle get Tickets");
        ShowDto show;
        if (tableFinalShowView.getSelectionModel().getSelectedItem() == null) {
            if (tableShowView.getSelectionModel().getSelectedItem() == null) {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("generic.markShow"), "Show", "Show");
                return;
            } else {
                show = tableShowView.getSelectionModel().getSelectedItem().getShow();
            }
        } else {
            show = tableFinalShowView.getSelectionModel().getSelectedItem().getShow();
        }
        Stage sellReserveTickets = new Stage();
        SpringFxmlLoader.LoadWrapper wrapper;
        sellReserveTickets.initModality(Modality.APPLICATION_MODAL);
        wrapper = springFxmlLoader.loadAndWrap("/gui/fxml/sellReserveTickets.fxml");
        sellReserveTickets.setScene(new Scene((Parent) wrapper.getLoadedObject()));
        SellReserveTicketsController controller = (SellReserveTicketsController) wrapper.getController();
        controller.setShow(show);
        sellReserveTickets.setResizable(false);
        sellReserveTickets.setTitle(BundleManager.getBundle().getString("salesPage.title"));
        sellReserveTickets.showAndWait();
    }


    /**
     * Checks if input search input fields are correct, throws an error message if not.
     */
    private boolean isValid() {
        String errorMessage = "";

        String selection = choiceBoxDetails.getValue();
        LOGGER.debug(selection);
        String input = fieldSearchInput.getText();
        if (radioArtist.isSelected()) {
            if (input == null || input.equals("")) {
                errorMessage += BundleManager.getBundle().getString("validation.emptyFields") + "\n";

            }
        } else if (radioPerformance.isSelected()) {

            if (selection.equals(BundleManager.getBundle().getString("generic.type")) && (choicePerformanceType.getValue() == null || choicePerformanceType.getValue().equals(""))) {
                errorMessage += BundleManager.getBundle().getString("generic.type") + "\n";

            }
            if (!selection.equals(BundleManager.getBundle().getString("generic.type")) && !selection.equals(BundleManager.getBundle().getString("generic.all")) && (input == null || input.equals(""))) {
                errorMessage += BundleManager.getBundle().getString("validation.emptyFields") + "\n";

            }
        } else if (radioShow.isSelected()) {
            LOGGER.debug(BundleManager.getBundle().getString("generic.dateFrom") + " " + BundleManager.getBundle().getString("generic.dateFromTo"));
            if (selection.equals(BundleManager.getBundle().getString("generic.dateFrom")) && (datePickerFromDate.getValue() == null || datePickerFromDate.getValue().equals(""))) {
                errorMessage += BundleManager.getBundle().getString("generic.date") + "\n";
            }
            LOGGER.debug((selection.equals(BundleManager.getBundle().getString("generic.dateFromTo")) + ""));
            if (selection.equals(BundleManager.getBundle().getString("generic.dateFromTo")) && (datePickerFromDate.getValue() == null || datePickerFromDate.getValue().toString().equals("") || datePickerToDate.getValue() == null || datePickerToDate.getValue().toString().equals("") || datePickerToDate.getValue().isBefore(datePickerFromDate.getValue()) || datePickerToDate.getValue().isEqual(datePickerFromDate.getValue()))) {
                errorMessage += BundleManager.getBundle().getString("validation.dateFromTo") + "\n";
            }
            if (selection.equals(BundleManager.getBundle().getString("generic.price")) && (input == null || input.equals(""))) {
                errorMessage += BundleManager.getBundle().getString("validation.emptyFields") + "\n";
            }
        } else { //location radio is selected
            if (input == null || input.equals("")) {
                errorMessage += BundleManager.getBundle().getString("validation.emptyFields") + "\n";

            }
        }


        LOGGER.info(errorMessage);
        if (errorMessage.length() == 0) {
            return true;
        } else {
            CustomAlert.throwErrorWindow(errorMessage, BundleManager.getBundle().getString("inputValidation.missing"), BundleManager.getBundle().getString("inputValidation.notValid"));

            return false;
        }
    }

    //--------------------------------------------Helper classes, update tables------------------------------//

    /**
     * Update current artisttable according to searchfilter
     *
     * @throws ServiceException    if server is unavailable
     */
    private boolean setArtistTable() throws  ServiceException {
        ObservableList<UIArtist> UIlist = FXCollections.observableArrayList();
        if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.all"))) {
            setSearchViewTable(1, "artist");
        } else {
            if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.name"))) {
                for (ArtistDto e : artistService.getArtistsByFirstname(fieldSearchInput.getText())) {
                    UIlist.add(new UIArtist(e));
                }
                for (ArtistDto e : artistService.getArtistsByLastname(fieldSearchInput.getText())) {
                    UIlist.add(new UIArtist(e));
                }

            }  else {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("search.selectionMissing"), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
            }
            tableArtistView.setItems(UIlist);

        }
        return UIlist.isEmpty();
    }

    /**
     * Update current performancetable according to searchfilter
     *
     * @throws ServiceException    if server is unavailable
     */
    private boolean setPerformanceTable() throws ServiceException {
        //radio Performance is selected
        ObservableList<UIPerformance> UIlist = FXCollections.observableArrayList();
        if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.all"))) {
            setSearchViewTable(1, "performance");
        } else {
            if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.name"))) {
                for (PerformanceDto e : performanceService.getPerformancesByName(fieldSearchInput.getText())) {
                    UIlist.add(new UIPerformance(e));
                }

            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.type"))) {
                for (PerformanceDto e : performanceService.getPerformancesByType(choicePerformanceType.getValue())) {
                    UIlist.add(new UIPerformance(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.duration"))) {
                for (PerformanceDto e : performanceService.getPerformancesByDuration(Integer.parseInt(fieldSearchInput.getText().replaceAll("\\s+", "")))) {
                    UIlist.add(new UIPerformance(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.description"))) {
                for (PerformanceDto e : performanceService.getPerformancesByDescription(fieldSearchInput.getText())) {
                    UIlist.add(new UIPerformance(e));
                }
            } else {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("search.selectionMissing"), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
            }
            tablePerformanceView.setItems(UIlist);
        }

        return UIlist.isEmpty();
    }


    /**
     * Update current location table according to searchfilter
     *
     * @throws ServiceException    if server is unavailable
     */
    private boolean setLocationTable() throws ServiceException {
        ObservableList<UILocation> UIlist = FXCollections.observableArrayList();
        if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.all"))) {
            setSearchViewTable(1, "location");
        } else {
            if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.name"))) {
                for (LocationDto e : locationService.getLocationsByName(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }

            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.street"))) {
                for (LocationDto e : locationService.getLocationsByStreet(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.city"))) {
                for (LocationDto e : locationService.getLocationsByCity(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.land"))) {
                for (LocationDto e : locationService.getLocationsByLand(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.postal"))) {
                for (LocationDto e : locationService.getLocationsByPostal(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.roomname"))) {
                for (LocationDto e : locationService.getLocationsByRoom(fieldSearchInput.getText())) {
                    UIlist.add(new UILocation(e));
                }
            }else {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("search.selectionMissing"), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
            }
            tableLocationView.setItems(UIlist);

        }
        return UIlist.isEmpty();
    }

    /**
     * Update current show table according to searchfilter
     *
     * @throws ServiceException    if server is unavailable
     */
    private boolean setShowTable() throws ServiceException {
        ObservableList<UIShowWithPrice> UIlist = FXCollections.observableArrayList();
        if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.all"))) {
            btnPrevPage.setDisable(true);
        } else {
            if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.dateFrom"))  ) {
                LOGGER.info("Dateformat:" + datePickerFromDate.getValue().toString());
                LocalDate tmp = datePickerFromDate.getValue();
                if (tmp.isAfter(LocalDate.now())) {
                    tmp = LocalDate.now();
                }
                for (UIShowWithPrice s : showService.cachedServiceGetShowFromDate(tmp)) {
                    UIlist.add(s);
                }

            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.price"))) {
                Double currentPrice = Double.parseDouble(fieldSearchInput.getText().replaceAll("\\s+", ""));
                if (currentPrice>0){
                    for (UIShowWithPrice s : showService.cachedServiceGetShowByPrice(currentPrice)) {
                        UIlist.add(s);
                    }
                } else {
                    CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("search.invalidPrice"), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
                }
            } else if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.dateFromTo"))&&  datePickerToDate.getValue().isAfter(LocalDate.now().minusDays(1))) {
                LocalDate tmp = datePickerFromDate.getValue();
                if (tmp.isAfter(LocalDate.now())) {
                    tmp = LocalDate.now();
                }
                for (UIShowWithPrice s : showService.cachedServiceGetShowFromTo(tmp,datePickerToDate.getValue())) {
                    UIlist.add(s);

                }
            } else {
                CustomAlert.throwErrorWindow(BundleManager.getBundle().getString("search.wrongDate"), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
            }
            tableShowView.setItems(UIlist);

        }
        return UIlist.isEmpty();
    }

    //------------------------------------------------Pagination Methods-------------------------------------------//

    /**
     * Button for table pagination. Queries next set of data
     */
    @FXML
    private void handlePrevPage() {
        LOGGER.info("previous page clicked, current page: " + pageNumber);
        if (pageNumber > 1) {
            pageNumber--;

            if (btnNextPage.isDisabled()) {
                btnNextPage.setDisable(false);
            }

            if (radioPerformance.isSelected()) {
                setSearchViewTable(pageNumber, "performance");
            } else if (radioShow.isSelected()) {
                setSearchViewTable(pageNumber, "show");
            } else if (radioArtist.isSelected()) {
                setSearchViewTable(pageNumber, "artist");
            } else { //radiolocation is selected
                setSearchViewTable(pageNumber, "location");
            }
            labelPage.setText(pageNumber.toString());
            if (pageNumber == 1) {
                btnPrevPage.setDisable(true);
            }
        }
    }

    /**
     * Button for table pagination. Queries previous set of data
     */
    @FXML
    private void handleNextPage() {
        List<LocationDto> tempLocation;
        List<PerformanceDto> tempPerformance;
        List<ArtistDto> tempArtist;
        List<UIShowWithPrice> tempShow;

        if (btnPrevPage.isDisabled()) {
            btnPrevPage.setDisable(false);
        }

        pageNumber++;
        try {
            if (radioPerformance.isSelected()) {
                tempPerformance = performanceService.getSpecificPage(pageNumber);
                if (!tempPerformance.isEmpty()) {
                    ObservableList<UIPerformance> UIlist = FXCollections.observableArrayList();

                    for (PerformanceDto e : tempPerformance) {
                        UIlist.add(new UIPerformance(e));
                    }

                    tablePerformanceView.setItems(UIlist);
                    labelPage.setText(pageNumber.toString());
                    if (Objects.equals(pageNumber, getTotalPages("performance"))) {
                        btnNextPage.setDisable(true);
                    }
                }
            } else if (radioLocation.isSelected()) {
                tempLocation = locationService.getSpecificPage(pageNumber);
                if (!tempLocation.isEmpty()) {
                    ObservableList<UILocation> UIlist = FXCollections.observableArrayList();
                    for (LocationDto e : tempLocation) {
                        UIlist.add(new UILocation(e));
                    }
                    tableLocationView.setItems(UIlist);
                    labelPage.setText(pageNumber.toString());
                    if (Objects.equals(pageNumber, getTotalPages("location"))) {
                        btnNextPage.setDisable(true);
                    }
                }
            } else if (radioArtist.isSelected()) {
                tempArtist = artistService.getSpecificPage(pageNumber);
                if (!tempArtist.isEmpty()) {


                    ObservableList<UIArtist> UIlist = FXCollections.observableArrayList();

                    for (ArtistDto e : tempArtist) {
                        UIlist.add(new UIArtist(e));
                    }

                    tableArtistView.setItems(UIlist);
                    labelPage.setText(pageNumber.toString());
                    if (pageNumber == getTotalPages("artist")) {
                        btnNextPage.setDisable(true);
                    }
                }

            } else {
                tempShow = showService.getCachedMap().get(pageNumber);
                if (!tempShow.isEmpty()) {
                    ObservableList<UIShowWithPrice> UIlist = FXCollections.observableArrayList();

                    for (UIShowWithPrice e : tempShow) {
                        UIlist.add(e);
                    }

                    tableShowView.setItems(UIlist);
                    labelPage.setText(pageNumber.toString());
                    if (Objects.equals(pageNumber, getTotalPages("show"))) {
                        btnNextPage.setDisable(true);
                    }
                }
            }


        } catch (ServiceException e) {
            CustomAlert.throwErrorWindow(e.getMessage(), BundleManager.getBundle().getString("search.noResults"), BundleManager.getBundle().getString("generic.error"));
        }

    }


    /**
     * Requests the totalpages which exist, if something goes wrong
     * the window will be closed due an error
     *
     * @return - total Page number
     * @param searchType either "performance" "artist" or "location"
     */
    private Integer getTotalPages(String searchType) {
        try {
            if (searchType.equals("performance")) {
                return performanceService.getPageCount();
            } else if (searchType.equals("artist")) {
                return artistService.getPageCount();
            } else if (searchType.equals("location")) {
                return locationService.getPageCount();
            } else {
                return showService.getPageCount();
            }
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(BundleManager.getBundle().getString("admin.alert.title.serviceException"));
            alert.setHeaderText(BundleManager.getBundle().getString("admin.alert.header.serviceException"));
            alert.setContentText(BundleManager.getBundle().getString("admin.alert.content.serviceException"));
            return -1;
        }
    }


    //-----------------------------------------Initializing Methods-----------------------------------------//

    /**
     * Initialises all Labels, Buttons and columnstrings. Registers values to columns.
     */

    private void initTextLabelsAndProperties() {
        //Labels and Buttons
        btnGetTickets.setText(BundleManager.getBundle().getString("generic.getTickets"));

        //Performance Table
        namePerformanceColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        durationPerformanceColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        descriptionPerformanceColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        typePerformanceColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        namePerformanceColumn.setText(BundleManager.getBundle().getString("generic.name"));
        durationPerformanceColumn.setText(BundleManager.getBundle().getString("table.duration"));
        descriptionPerformanceColumn.setText(BundleManager.getBundle().getString("generic.description"));
        typePerformanceColumn.setText(BundleManager.getBundle().getString("generic.type"));

        //Final Show Table
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        performanceFinalColumn.setCellValueFactory(cellData -> cellData.getValue().performanceProperty());

        dateColumn.setText(BundleManager.getBundle().getString("generic.date"));
        locationColumn.setText(BundleManager.getBundle().getString("generic.location"));
        performanceFinalColumn.setText(BundleManager.getBundle().getString("generic.name"));

        //Show Table
        locationShowColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        dateShowColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        performanceShowColumn.setCellValueFactory(cellData -> cellData.getValue().performanceProperty());
        priceShowColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceRange());

        locationShowColumn.setText(BundleManager.getBundle().getString("generic.location"));
        dateShowColumn.setText(BundleManager.getBundle().getString("generic.date"));
        performanceShowColumn.setText(BundleManager.getBundle().getString("generic.performance"));
        priceShowColumn.setText(BundleManager.getBundle().getString("generic.price"));

        //Location Table
        nameLocationColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        streetLocationColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        cityLocationColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        landLocationColumn.setCellValueFactory(cellData -> cellData.getValue().landProperty());
        postalLocationColumn.setCellValueFactory(cellData -> cellData.getValue().postalProperty());
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().roomName());
        roomFinalColumn.setCellValueFactory(cellData -> cellData.getValue().roomName());

        roomFinalColumn.setText(BundleManager.getBundle().getString("generic.rooms"));
        roomColumn.setText(BundleManager.getBundle().getString("generic.rooms"));
        nameLocationColumn.setText(BundleManager.getBundle().getString("generic.name"));
        streetLocationColumn.setText(BundleManager.getBundle().getString("generic.street"));
        cityLocationColumn.setText(BundleManager.getBundle().getString("generic.city"));
        landLocationColumn.setText(BundleManager.getBundle().getString("generic.land"));
        postalLocationColumn.setText(BundleManager.getBundle().getString("generic.postal"));

        //Artist Table
        firstnameArtistColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastnameArtistColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        descriptionArtistColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        firstnameArtistColumn.setText(BundleManager.getBundle().getString("generic.firstname"));
        lastnameArtistColumn.setText(BundleManager.getBundle().getString("generic.lastname"));
        descriptionArtistColumn.setText(BundleManager.getBundle().getString("generic.description"));
    }


    /**
     * Initialises all radiobuttons, "Performance" is selected, sets text properties
     */
    private void initRadioButtons() {
        radioArtist.setSelected(false);
        radioLocation.setSelected(false);
        radioPerformance.setSelected(true);
        radioShow.setSelected(false);

        radioArtist.setText(BundleManager.getBundle().getString("generic.artist"));
        radioLocation.setText(BundleManager.getBundle().getString("generic.location"));
        radioPerformance.setText(BundleManager.getBundle().getString("generic.performance"));
        radioShow.setText(BundleManager.getBundle().getString("generic.show"));

        Tooltip radioArtistToolTip = new Tooltip();
        radioArtistToolTip.setText(BundleManager.getBundle().getString("tooltip.radioArtist"));
        radioArtist.setTooltip(radioArtistToolTip);
        Tooltip radioLocationToolTip = new Tooltip();
        radioLocationToolTip.setText(BundleManager.getBundle().getString("tooltip.radioLocation"));
        radioLocation.setTooltip(radioLocationToolTip);
        Tooltip radioPerformanceToolTip = new Tooltip();
        radioPerformanceToolTip.setText(BundleManager.getBundle().getString("tooltip.radioPerformance"));
        radioPerformance.setTooltip(radioPerformanceToolTip);
        Tooltip radioShowToolTip = new Tooltip();
        radioShowToolTip.setText(BundleManager.getBundle().getString("tooltip.radioShow"));
        radioShow.setTooltip(radioShowToolTip);

        radioArtist.setToggleGroup(group);
        radioLocation.setToggleGroup(group);
        radioPerformance.setToggleGroup(group);
        radioShow.setToggleGroup(group);

        radioArtist.setUserData("artist");
        radioLocation.setUserData("location");
        radioPerformance.setUserData("performance");
        radioShow.setUserData("show");


    }

    /**
     * initialses UI for PerformanceSearch
     */
    private void initPerformanceSearch() {
        tablePerformanceView.setVisible(true);
        tableArtistView.setVisible(false);
        tableLocationView.setVisible(false);
        tableShowView.setVisible(false);
        tableFinalShowView.setVisible(true);

        rectPerformance.setVisible(true);
        rectArtist.setVisible(false);
        rectShow.setVisible(false);
        rectLocation.setVisible(false);

        choiceBoxDetails.setItems(FXCollections.observableArrayList(performanceDetails));
        fieldSearchInput.setVisible(false);
        datePickerToDate.setVisible(false);
        datePickerFromDate.setVisible(false);

        choicePerformanceType.setVisible(true);
        choiceBoxDetails.setValue(BundleManager.getBundle().getString("generic.all"));
        setSearchViewTable(1, "performance");

        pageNumber = 1;

    }

    /**
     * initialses UI for ArtistSearch
     */
    private void initArtistSearch() {
        tablePerformanceView.setVisible(false);
        tableArtistView.setVisible(true);
        tableLocationView.setVisible(false);
        tableShowView.setVisible(false);
        tableFinalShowView.setVisible(true);

        rectPerformance.setVisible(false);
        rectArtist.setVisible(true);
        rectShow.setVisible(false);
        rectLocation.setVisible(false);

        choiceBoxDetails.setItems(FXCollections.observableArrayList(artistDetails));
        fieldSearchInput.setVisible(false);
        datePickerToDate.setVisible(false);
        datePickerFromDate.setVisible(false);

        choicePerformanceType.setVisible(false);
        choiceBoxDetails.setValue(BundleManager.getBundle().getString("generic.all"));
        setSearchViewTable(1, "artist");

    }

    /**
     * initialses UI for LocationSearch
     */
    private void initLocationSearch() {
        tablePerformanceView.setVisible(false);
        tableArtistView.setVisible(false);
        tableLocationView.setVisible(true);
        tableShowView.setVisible(false);
        tableFinalShowView.setVisible(true);

        rectPerformance.setVisible(false);
        rectArtist.setVisible(false);
        rectShow.setVisible(false);
        rectLocation.setVisible(true);

        choiceBoxDetails.setItems(FXCollections.observableArrayList(locationDetails));
        fieldSearchInput.setVisible(false);
        datePickerToDate.setVisible(false);
        datePickerFromDate.setVisible(false);

        choicePerformanceType.setVisible(false);
        choiceBoxDetails.setValue(BundleManager.getBundle().getString("generic.all"));
        setSearchViewTable(1, "location");

    }


    /**
     * initialses UI for ShowSearch
     */
    private void initShowSearch() {
        tablePerformanceView.setVisible(false);
        tableArtistView.setVisible(false);
        tableLocationView.setVisible(false);
        tableShowView.setVisible(true);
        tableFinalShowView.setVisible(false);

        rectPerformance.setVisible(false);
        rectArtist.setVisible(false);
        rectShow.setVisible(true);
        rectLocation.setVisible(false);

        choiceBoxDetails.setItems(FXCollections.observableArrayList(showDetails));
        fieldSearchInput.setVisible(false);
        datePickerToDate.setVisible(false);
        datePickerFromDate.setVisible(false);

        choicePerformanceType.setVisible(false);
        choiceBoxDetails.setValue(BundleManager.getBundle().getString("generic.all"));
        setSearchViewTable(1, "show");

    }


    /**
     * Initialises Choicelists for choicebox for every searchtype
     */
    private void initArraysForChoiceBox() {

        performanceDetails = new ArrayList<>();
        performanceDetails.add(BundleManager.getBundle().getString("generic.name"));
        performanceDetails.add(BundleManager.getBundle().getString("generic.type"));
        performanceDetails.add(BundleManager.getBundle().getString("generic.duration"));
        performanceDetails.add(BundleManager.getBundle().getString("generic.description"));
        performanceDetails.add(BundleManager.getBundle().getString("generic.all"));

        showDetails = new ArrayList<>();
        showDetails.add(BundleManager.getBundle().getString("generic.dateFrom"));
        showDetails.add(BundleManager.getBundle().getString("generic.price"));
        showDetails.add(BundleManager.getBundle().getString("generic.dateFromTo"));
        showDetails.add(BundleManager.getBundle().getString("generic.all"));


        locationDetails = new ArrayList<>();
        locationDetails.add(BundleManager.getBundle().getString("generic.name"));
        locationDetails.add(BundleManager.getBundle().getString("generic.street"));
        locationDetails.add(BundleManager.getBundle().getString("generic.city"));
        locationDetails.add(BundleManager.getBundle().getString("generic.land"));
        locationDetails.add(BundleManager.getBundle().getString("generic.postal"));
        locationDetails.add(BundleManager.getBundle().getString("generic.roomname"));
        locationDetails.add(BundleManager.getBundle().getString("generic.all"));

        artistDetails = new ArrayList<>();
        artistDetails.add(BundleManager.getBundle().getString("generic.name"));
        artistDetails.add(BundleManager.getBundle().getString("generic.all"));

        ArrayList<PerformanceTypeDto> performanceTypeList = new ArrayList<>();
        performanceTypeList.add(PerformanceTypeDto.FESTIVAL);
        performanceTypeList.add(PerformanceTypeDto.CONCERT);
        performanceTypeList.add(PerformanceTypeDto.MOVIE);
        performanceTypeList.add(PerformanceTypeDto.MUSICAL);
        performanceTypeList.add(PerformanceTypeDto.OPERA);
        performanceTypeList.add(PerformanceTypeDto.THEATER);

        choicePerformanceType.setItems(FXCollections.observableArrayList(performanceTypeList));
        choicePerformanceType.setValue(PerformanceTypeDto.THEATER);

    }

    /**
     * Triggered when user chooses a date in the datepickcer "from to date" when searching for shows
     */
    @FXML
    public void handleToDateClicked() {
        LOGGER.info("From: " + datePickerFromDate.getValue().toString());
        if (datePickerFromDate.getValue() != null && !datePickerFromDate.getValue().toString().equals("") && datePickerToDate.getValue() != null && !datePickerToDate.getValue().toString().equals("")) {
            updateSearchTable();
        }
    }

    /**
     * Triggered when user chooses a date in the datepicker "from date" when searching for shows
     */
    @FXML
    public void handleFromDateClicked() {
        LOGGER.info("from date clicked");
        if (choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.dateFrom"))) {
            updateSearchTable();
        } else if (datePickerToDate.getValue() != null && datePickerFromDate.getValue() != null && choiceBoxDetails.getValue().equals(BundleManager.getBundle().getString("generic.dateFromTo"))) {
            updateSearchTable();
        }
    }

    /**
     * Helper method to set pages visible or not.
     *
     * @param pagesVisible should the pages buttons be there or not
     */
    private void setPagesVisible(boolean pagesVisible) {
        if (pagesVisible) {
            btnNextPage.setVisible(true);
            btnPrevPage.setVisible(true);
            labelPage.setVisible(true);
            labelMaxPage.setVisible(true);
        } else {
            btnNextPage.setVisible(false);
            btnPrevPage.setVisible(false);
            labelPage.setVisible(false);
            labelMaxPage.setVisible(false);
        }
    }

}
