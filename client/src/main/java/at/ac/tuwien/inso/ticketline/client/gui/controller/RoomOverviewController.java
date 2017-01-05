package at.ac.tuwien.inso.ticketline.client.gui.controller;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AreaService;
import at.ac.tuwien.inso.ticketline.client.service.RowService;
import at.ac.tuwien.inso.ticketline.client.service.SeatService;
import at.ac.tuwien.inso.ticketline.client.util.*;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hannes on 15.05.2016.
 */
@Component
public class RoomOverviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomOverviewController.class);

    @FXML
    private Label labelReserved,labelStage,labelLocked,labelSold,labelFree,labelAreas;

    @FXML
    private ImageView imageViewLocked,imageViewSold,imageViewReserved,imageViewFree;


    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    //----------------------------------------

    @FXML
    private AnchorPane roomOverviewPane;

    @FXML
    private ImageView imageViewStance,imageViewVIP,imageViewBarrierFree,imageViewFreeSeat;

    @FXML
    private ScrollPane scrollPane;

    //---------------------------------------

    @FXML
    private TextArea textAreaSeatOverview,textAreaTicketsChosen,textAreaAreaTicketsOverview;

    @FXML
    private Button buttonSellTickets, buttonCancel;


    @FXML
    private TilePane tilePaneRowNumbers;

    @FXML
    private GridPane gridPaneOverview;


    //------------------------------------------

    @FXML
    private Button buttonStance,buttonFreeseatChoice, buttonVIP,buttonBarrierFree,buttonDeleteVIPTicket,buttonDeleteBarrierTicket,buttonDeleteFreeSeatTicket,buttonDeleteStanceTicket;


    @Autowired
    private  AreaService areaService;

    @Autowired
    private RowService rowService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private LoginController loginController;

    private  Boolean rowsExist = false;
    private List<AreaDto> areaList = null;
    private List<RowDto> rowList = null;
    private  List<ButtonWithID> IDButtonList;
    private List<List<SeatDto>> allSeats = null;
    private ShowDto show;
    private String availableAreas;

    private static NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Initialises all data and labels and eventhandler
     */
    public void initialize() {
        buttonCancel.setText(BundleManager.getBundle().getString("generic.cancel"));

        buttonBarrierFree.setVisible(false);
        buttonFreeseatChoice.setVisible(false);
        buttonStance.setVisible(false);
        buttonVIP.setVisible(false);
        buttonDeleteVIPTicket.setVisible(false);
        buttonDeleteBarrierTicket.setVisible(false);
        buttonDeleteFreeSeatTicket.setVisible(false);
        buttonDeleteStanceTicket.setVisible(false);
        textAreaAreaTicketsOverview.setVisible(false);
        labelAreas.setVisible(false);
        labelStage.setText(BundleManager.getBundle().getString("roomplan.stage"));

    }

    /**
     * Gets the current IDButtonList, where the buttons with IDs are stored
     * @return the list with the the ButtonWithIDs
     */
    public List<ButtonWithID> getIDButtonList() {
        return IDButtonList;
    }

    public void setShow(ShowDto show) {
        this.show = show;
        this.initOverview(show);

        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                handleCancel();
            }
        });
    }


    /**
     * Initializes the room plan and gets the necessary data from the server
     * @param show the show
     */
    public void initOverview(ShowDto show) {
        RoomDto room = new RoomDto();
        room.setId(show.getRoom().getId());
        textAreaTicketsChosen.setText(TicketHandler.getStringRepresentation());
        IDButtonList = new ArrayList<>();


        try {
            areaList = areaService.getAreasByRoom(room);
        } catch (ServiceException e) {
            LOGGER.error("areaService.getAreasByRoom failed: "+e.getMessage());
        }
        try {
            rowList = rowService.getRowsByRoom(room);
        } catch (ServiceException e) {
            LOGGER.error("rowService.getRowsByRoom failed: "+e.getMessage());
        }

        rowsExist = rowList.size() != 0;

        if (rowsExist) {
            initRows();
        } else {
            initAreas();
        }
    }

    /**
     * Initializes the room plan, if areas are present
     */
    private void initAreas() {

        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(),areaList);
        textAreaSeatOverview.setText(availableAreas);

        buttonSellTickets.setText(BundleManager.getBundle().getString("salesPage.field.sellAreaTickets"));
        scrollPane.setVisible(false);
        labelLocked.setVisible(false);
        labelReserved.setVisible(false);
        labelSold.setVisible(false);
        labelFree.setVisible(false);
        Image stanceimage;
        if (BundleManager.getBundle().getLocale() == Locale.GERMAN) {
            stanceimage = new Image("/gui/image/stance_area_de.png");
        } else {
            stanceimage = new Image("/gui/image/stance_area.png");
        }
        imageViewStance.setImage(stanceimage);
        imageViewStance.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                handleStancePressed(null);
            }
        });
        Image barrierimage;
        if (BundleManager.getBundle().getLocale() == Locale.GERMAN) {
            barrierimage = new Image("/gui/image/barrierfree_area_de.png");
        } else {
            barrierimage = new Image("/gui/image/barrierfree_area.png");
        }
        imageViewBarrierFree.setImage(barrierimage);
        imageViewBarrierFree.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                handleBarrierFreePressed(null);
            }
        });

        Image freeseatimage;
        if (BundleManager.getBundle().getLocale() == Locale.GERMAN) {
            freeseatimage = new Image("/gui/image/freeseat_area_de.png");
        } else {
            freeseatimage = new Image("/gui/image/freeseat_area.png");
        }

        imageViewFreeSeat.setImage(freeseatimage);
        imageViewFreeSeat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                handleFreeSeatPressed(null);
            }
        });
        Image vipimage;
        if (BundleManager.getBundle().getLocale() == Locale.GERMAN) {
            vipimage = new Image("/gui/image/vip_area_de.png");
        } else {
            vipimage = new Image("/gui/image/vip_area.png");
        }

        imageViewVIP.setImage(vipimage);
        imageViewVIP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                handleVIPPressed(null);
            }
        });

        imageViewStance.setVisible(false);
        imageViewBarrierFree.setVisible(false);
        imageViewFreeSeat.setVisible(false);
        imageViewVIP.setVisible(false);

        labelAreas.setVisible(true);
        labelAreas.setText(BundleManager.getBundle().getString("salesPage.label.area"));

        buttonDeleteVIPTicket.setText(BundleManager.getBundle().getString("salesPage.button.deleteVIP"));
        buttonDeleteBarrierTicket.setText(BundleManager.getBundle().getString("salesPage.button.deletebarrier"));
        buttonDeleteFreeSeatTicket.setText(BundleManager.getBundle().getString("salesPage.button.deletefreeseatchoice"));
        buttonDeleteStanceTicket.setText(BundleManager.getBundle().getString("salesPage.button.deletestance"));

        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.BARRIER_FREE) {
                imageViewBarrierFree.setVisible(true);
            }
            if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE) {
                imageViewFreeSeat.setVisible(true);
            }
            if (area.getAreaType() == AreaTypeDto.STANCE) {
                imageViewStance.setVisible(true);
            }
            if (area.getAreaType() == AreaTypeDto.VIP) {
                 imageViewVIP.setVisible(true);
            }
        }

        //Listeners
        imageViewBarrierFree.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                textAreaSeatOverview.setText(availableAreas);
            }
        });
        imageViewFreeSeat.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                textAreaSeatOverview.setText(availableAreas);
            }
        });
        imageViewVIP.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                textAreaSeatOverview.setText(availableAreas);
            }
        });
        imageViewStance.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                textAreaSeatOverview.setText(availableAreas);
            }
        });


        imageViewBarrierFree.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                double price = 0;
                for (AreaDto area : areaList) {
                    if (area.getAreaType() == AreaTypeDto.BARRIER_FREE)
                        price = area.getPrice();
                }

                textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + price + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            }
        });

        imageViewFreeSeat.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                double price = 0;
                for (AreaDto area : areaList) {
                    if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE)
                        price = area.getPrice();
                }

                 textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            }
        });


        imageViewStance.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                double price = 0;
                for (AreaDto area : areaList) {
                    if (area.getAreaType() == AreaTypeDto.STANCE)
                        price = area.getPrice();
                }
               textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.stance") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

            }
        });


        imageViewVIP.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                double price = 0;
                for (AreaDto area : areaList) {
                    if (area.getAreaType() == AreaTypeDto.VIP)
                        price = area.getPrice();
                }

               textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.vip") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

            }
        });


        buttonDeleteVIPTicket.setVisible(true);
        buttonDeleteBarrierTicket.setVisible(true);
        buttonDeleteFreeSeatTicket.setVisible(true);
        buttonDeleteStanceTicket.setVisible(true);
        textAreaAreaTicketsOverview.setVisible(true);
        textAreaTicketsChosen.setVisible(false);

        buttonDeleteVIPTicket.setDisable(true);
        buttonDeleteBarrierTicket.setDisable(true);
        buttonDeleteFreeSeatTicket.setDisable(true);
        buttonDeleteStanceTicket.setDisable(true);

        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
        if(TicketHandler.hasBarrierTicket()){
            buttonDeleteBarrierTicket.setDisable(false);
        }
        if(TicketHandler.hasStanceTicket()){
            buttonDeleteStanceTicket.setDisable(false);
        }
        if(TicketHandler.hasVIPTicket()){
            buttonDeleteVIPTicket.setDisable(false);
        }
        if(TicketHandler.hasSeatTicket()){
            buttonDeleteFreeSeatTicket.setDisable(false);
        }

        checkIfEnoughTicketsAvailableAndCorrectGUI();
    }


    /**
     * Initializes the room plan if seats are present
     */
    public void initRows() {
        buttonSellTickets.setText(BundleManager.getBundle().getString("salesPage.field.sellTickets"));
        scrollPane.setVisible(true);

        labelSold.setText(BundleManager.getBundle().getString("salesPage.sold"));
        labelReserved.setText(BundleManager.getBundle().getString("salesPage.reserved"));
        labelLocked.setText(BundleManager.getBundle().getString("salesPage.locked"));
        labelFree.setText(BundleManager.getBundle().getString("salesPage.free"));

        Image soldimage = new Image("/gui/image/sold.png");
        imageViewSold.setImage(soldimage);

        Image reservedimage = new Image("/gui/image/reserved.png");
        imageViewReserved.setImage(reservedimage);

        Image lockedimage = new Image("/gui/image/locked.png");
        imageViewLocked.setImage(lockedimage);

        Image freeImage = new Image("/gui/image/free.png");
        imageViewFree.setImage(freeImage);

        //GridPane
        tilePaneRowNumbers.setVgap(20);
        gridPaneOverview.setVgap(10);
        gridPaneOverview.setHgap(2);

        allSeats = new ArrayList<>();

        UsedSeatsDto lockedSeats = null;
        UsedSeatsDto soldSeats = null;
        UsedSeatsDto reservedSeats = null;
        String descriptionText = "";

        try {
            lockedSeats = seatService.getLockedSeats(show.getId());
        } catch (ServiceException e) {
            LOGGER.error("seatService.getLockedSeats failed: "+e.getMessage());
        }
        try {
            soldSeats = seatService.getSoldSeats(show.getId());
        } catch (ServiceException e) {
            LOGGER.error("seatService.getSoldSeats failed: "+e.getMessage());
        }
        try {
            reservedSeats = seatService.getReservedSeats(show.getId());
        } catch (ServiceException e) {
            LOGGER.error("seatService.getReservedSeats failed: "+e.getMessage());
        }

        for (int i = 0; i < rowList.size(); i++) {

            List<SeatDto> seatsFromThisRow = null;
            try {
                seatsFromThisRow = seatService.getSeatsByRow(rowList.get(i));
                allSeats.add(seatsFromThisRow);
            } catch (ServiceException e) {
                LOGGER.error("seatService.getSeatsByRow failed: "+e.getMessage());
            }

            int oldSequence = 0;
            int sequence = 0;
            int seatoffset = 0;
            for (int j = 0; j < seatsFromThisRow.size(); j++) {

                oldSequence = sequence;
                sequence = seatsFromThisRow.get(j).getOrder();

                if (sequence - oldSequence > 1) {
                    seatoffset++;
                    Label rowLabel = new Label();
                    rowLabel.setPadding(new Insets(0, 10, 0, 10));
                    rowLabel.setMinWidth(40);
                    rowLabel.setText(""+sequence);
                    rowLabel.setVisible(false);
                    gridPaneOverview.add(rowLabel, j + 1 + seatoffset, i);
                }

                Button seat = new Button();
                seat.setMinSize(40, 30);
                descriptionText = "available";
                boolean chosen = false;

                //Ausgrauen
                if (!(lockedSeats.getIds() == null)) {
                    for (Integer lockedseatid : lockedSeats.getIds()) {
                        if (lockedseatid.equals(seatsFromThisRow.get(j).getId())) {
                            seat.setStyle("-fx-base: #D8D8D8;");
                            seat.setDisable(true);
                            descriptionText = "locked";
                        }
                    }
                }

                if (!reservedSeats.getIds().isEmpty()) {
                    for (Integer reservedseatid : reservedSeats.getIds()) {
                        if (reservedseatid.equals(seatsFromThisRow.get(j).getId())) {
                            seat.setStyle("-fx-base: #F7FE2E;");
                            seat.setDisable(true);
                            descriptionText = "reserved";
                        }
                    }
                }

                if (!(soldSeats.getIds() == null)) {
                    for (Integer soldseatid : soldSeats.getIds()) {
                        if (soldseatid.equals(seatsFromThisRow.get(j).getId())) {
                            seat.setStyle("-fx-base: #F78181;");
                            seat.setDisable(true);
                            descriptionText = "sold";
                        }
                    }
                }

                seatsFromThisRow.get(j).getId();
                seat.setText("" + (j + 1));

                if (descriptionText.equals("available"))
                    seat.setStyle("-fx-base: #b6e7c9;");

                boolean wasClicked = TicketHandler.buttonWasClickedOn(i, j);
                if (wasClicked) {
                    seat.setDisable(false);
                    seat.setStyle("-fx-base: #b6e7c9;");
                }
                boolean hasButton = TicketHandler.buttonWasChosen(i, j);
                if (hasButton) {
                    seat.setDisable(false);
                    chosen = true;
                }
                if (chosen)
                    seat.setStyle(("-fx-base: #9133f5;"));

                 gridPaneOverview.add(seat, sequence, i);
                ButtonWithID button = new ButtonWithID(i, j, seat, seatsFromThisRow.get(j), descriptionText);
                button.setChosen(chosen);
                button.setRepeatedlyClickedOn(wasClicked);
                IDButtonList.add(button);


                //Listeners
                seat.setOnMouseEntered(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        int rowNr = -1;
                        int seatNr = -1;
                        for (ButtonWithID b : IDButtonList) {
                            if (b.getButton() == seat) {
                                rowNr = b.getRow();
                                seatNr = b.getSeat();
                            }
                        }
                        textAreaSeatOverview.setText(BundleManager.getBundle().getString("generic.price") + " " + formatter.format(rowList.get(rowNr).getPrice()) + " €\n" + BundleManager.getBundle().getString("roomPlan.field.position") + " " + (seatNr + 1) + "" + AlphabetMapper.getLetter(rowNr) + "\n");
                    }
                });
                seat.setOnMouseExited(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        textAreaSeatOverview.setText("");
                    }
                });
                seat.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        //textAreaSeatOverview.setText("CLICKED");
                        int rowNr = -1;
                        int seatNr = -1;
                        for (ButtonWithID b : IDButtonList) {
                            if (b.getButton() == seat) {
                                rowNr = b.getRow();
                                seatNr = b.getSeat();

                                if (!b.isChosen()) {

                                    UsedSeatsDto lockedSeats = null;
                                    UsedSeatsDto soldSeats = null;
                                    UsedSeatsDto reservedSeats = null;
                                    String descriptionText = "";

                                    try {
                                        lockedSeats = seatService.getLockedSeats(show.getId());
                                    } catch (ServiceException e) {
                                        LOGGER.error("seatService.getLockedSeats failed: "+e.getMessage());
                                    }

                                    if ((lockedSeats.getIds() == null) || ((lockedSeats.getIds() != null) && !lockedSeats.getIds().contains(new Integer(allSeats.get(rowNr).get(seatNr).getId())))) {

                                        //Ticket erstellen
                                        TicketDto ticket = new TicketDto();
                                        ticket.setPrice((rowList.get(rowNr).getPrice()));
                                        ticket.setShow(show);
                                        ticket.setDescription(show.getPerformance().getName());
                                        ticket.setValid(true);
                                        ticket.setSeat(b.getActualSeat());
                                        //ticket.setUuid();
                                        b.setTicket(ticket);
                                        TicketHandler.addTicket(new EnhancedTicket(ticket, rowNr, seatNr));

                                        b.getButton().setStyle("-fx-base: #9133f5;");
                                        b.setChosen(true);
                                        b.setRepeatedlyClickedOn(true);

                                        UsedSeatsDto lock = new UsedSeatsDto();
                                        lock.setShowId(show.getId());
                                        lock.setIds(new ArrayList<Integer>());
                                        lock.getIds().add(new Integer(allSeats.get(rowNr).get(seatNr).getId()));
                                         try {
                                            seatService.lockSeats(lock);
                                        } catch (ServiceException e) {
                                             LOGGER.error("seatService.lockSeats failed: "+e.getMessage());
                                        }

                                        textAreaTicketsChosen.setText(TicketHandler.getStringRepresentation());
                                    } else {
                                        if (b.isRepeatedlyClickedOn()) {
                                            //Ticket erstellen
                                            TicketDto ticket = new TicketDto();
                                            ticket.setPrice((rowList.get(rowNr).getPrice()));
                                            ticket.setShow(show);
                                            ticket.setDescription(show.getPerformance().getName());
                                            ticket.setValid(true);
                                            ticket.setSeat(b.getActualSeat());
                                            b.setTicket(ticket);
                                            TicketHandler.addTicket(new EnhancedTicket(ticket, rowNr, seatNr));

                                            b.getButton().setStyle("-fx-base: #9133f5;");
                                            b.setChosen(true);
                                            b.setRepeatedlyClickedOn(true);
                                            textAreaTicketsChosen.setText(TicketHandler.getStringRepresentation());
                                        } else {
                                            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("salesPage.SeatsCurrentlyLocked"), BundleManager.getBundle().getString("salesPage.locked"), BundleManager.getBundle().getString("salesPage.seatLocked"));
                                            b.setDescriptionText("locked");
                                            b.getButton().setStyle("-fx-base: #D8D8D8;");
                                            b.getButton().setDisable(true);
                                        }
                                    }

                                } else {
                                    TicketHandler.deleteTicket(new EnhancedTicket(b.getTicket(), rowNr, seatNr));
                                    b.getButton().setStyle("-fx-base: #b6e7c9;");
                                    b.setChosen(false);

                                    //Unlock Seat
                                    try {
                                        seatService.unlockSeat(show.getId(), allSeats.get(rowNr).get(seatNr).getId());
                                    } catch (ServiceException e) {
                                        LOGGER.error("seatService.unlockSeat failed: "+e.getMessage());
                                    }

                                    textAreaTicketsChosen.setText(TicketHandler.getStringRepresentation());
                                }
                            }
                        }
                    }
                });

                if (j == 0) {
                    Label rowLabel = new Label();
                    rowLabel.setPadding(new Insets(0, 10, 0, 10));
                    rowLabel.setMinWidth(40);
                    rowLabel.setText(AlphabetMapper.getLetter(i));
                    gridPaneOverview.add(rowLabel, 0, i);
                }
            }
        }
    }

    //-------------------------------------

    /**
     * Misleading name (old name). Saves current state of the roomplan and returns to the Sell Overview.
     */
    @FXML
    public void handleSellTickets() {

        if(!checkIfEnoughTicketsAvailableAndCorrectGUI())
            return;

        TicketHandler.setButtonWithIDList(IDButtonList);
        Stage stage = (Stage) buttonVIP.getScene().getWindow();
        stage.close();

    }

    /**
     * Checks, if the chosen area tickets are still available on the server and removes tickets from the client, if necessary
     * @return true if area tickets are still available
     */
    public boolean checkIfEnoughTicketsAvailableAndCorrectGUI(){
        if(!show.getRoom().getSeatChoice()) {

            int excess = TicketHandler.getAvailableVIPTickets(show.getId(), areaList) - TicketHandler.numberOfVIPTickets();
            boolean enoughTickets = true;

            double price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.VIP)
                    price = area.getPrice();
            }

            if (excess < 0) {
                for (int i = 0; i < -excess; i++) {
                    TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.VIP));
                }
                textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"), BundleManager.getBundle().getString("generic.error"), BundleManager.getBundle().getString("generic.error"));
                availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);

                //textAreaSeatOverview.setText(availableAreas + BundleManager.getBundle().getString("roomPlan.field.vip") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro"));

                textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.vip") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro") + "\n\n" + availableAreas);

                enoughTickets = false;
            }

            excess = TicketHandler.getAvailableBarrierfreeTickets(show.getId(), areaList) - TicketHandler.numberOfBarrierfreeTickets();

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.BARRIER_FREE)
                    price = area.getPrice();
            }

            if (excess < 0) {
                for (int i = 0; i < -excess; i++) {
                    TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.BARRIER_FREE));
                }
                textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"), BundleManager.getBundle().getString("generic.error"), BundleManager.getBundle().getString("generic.error"));
                availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);

                //textAreaSeatOverview.setText(availableAreas + BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro"));

                textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro") + "\n\n" + availableAreas);

                enoughTickets = false;
            }

            excess = TicketHandler.getAvailableFreeseatTickets(show.getId(), areaList) - TicketHandler.numberOfSFreeseatTickets();

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE)
                    price = area.getPrice();
            }

            if (excess < 0) {
                for (int i = 0; i < -excess; i++) {
                    TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.FREE_SEATING_CHOICE));
                }
                textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"), BundleManager.getBundle().getString("generic.error"), BundleManager.getBundle().getString("generic.error"));
                availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);

                //textAreaSeatOverview.setText(availableAreas + BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro"));

                textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro") + "\n\n" + availableAreas);

                enoughTickets = false;
            }

            excess = TicketHandler.getAvailableStanceTickets(show.getId(), areaList) - TicketHandler.numberOfStanceTickets();

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.STANCE)
                    price = area.getPrice();
            }

            if (excess < 0) {
                for (int i = 0; i < -excess; i++) {
                    TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.STANCE));
                }
                textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

                CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"), BundleManager.getBundle().getString("generic.error"), BundleManager.getBundle().getString("generic.error"));
                availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);

                textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.stance") + " " + formatter.format(price) + " " + BundleManager.getBundle().getString("roomplan.field.currencyEuro") + "\n\n" + availableAreas);

                enoughTickets = false;
            }

            return enoughTickets;
        }
        return true;
    }


    //-------------------------------------

    /**
     * Adds a VIP ticket. Throws Alert if not available.
     * @param actionEvent event
     */
    public void handleVIPPressed(ActionEvent actionEvent) {

        int excess = TicketHandler.getAvailableVIPTickets(show.getId(), areaList)-TicketHandler.numberOfVIPTickets();

        double price = 0;
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.VIP)
                price = area.getPrice();
        }

        if(excess < 0){
            for(int i = 0; i<-excess ; i++){
                TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.VIP));
            }
            textAreaSeatOverview.setText( BundleManager.getBundle().getString("roomPlan.field.VIP") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro"));
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"),BundleManager.getBundle().getString("generic.error"),BundleManager.getBundle().getString("generic.error"));
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);

            return;
        }

        if(excess == 0){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("roomPlan.field.noVIPMessage"),BundleManager.getBundle().getString("roomPlan.field.soldOutArea"),BundleManager.getBundle().getString("generic.error"));

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.VIP)
                    price = area.getPrice();
            }
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.vip") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            return;
        }

        //Ticket erstellen
        TicketDto ticket = new TicketDto();
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.VIP) {
                ticket.setPrice(area.getPrice());
                ticket.setArea(area);
            }
        }
        ticket.setShow(show);
        ticket.setDescription(show.getPerformance().getName());
        ticket.setValid(true);
        TicketHandler.addTicket(new EnhancedTicket(ticket, AreaTypeDto.VIP));

        buttonDeleteVIPTicket.setDisable(false);
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);


        textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.vip") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro") +"\n\n"+ availableAreas);
        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
    }

    /**
     * Adds a Barrierfree ticket. Throws Alert if not available.
     * @param actionEvent event
     */
    public void handleBarrierFreePressed(ActionEvent actionEvent) {

        int excess = TicketHandler.getAvailableBarrierfreeTickets(show.getId(), areaList)-TicketHandler.numberOfBarrierfreeTickets();

        double price = 0;
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.VIP)
                price = area.getPrice();
        }

        if(excess < 0){
            for(int i = 0; i<-excess ; i++){
                TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.BARRIER_FREE));
            }
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"),BundleManager.getBundle().getString("generic.error"),BundleManager.getBundle().getString("generic.error"));
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

            return;
        }

        if(excess == 0){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("roomPlan.noMore.barrier"),BundleManager.getBundle().getString("roomPlan.noMore.area"),BundleManager.getBundle().getString("roomPlan.noMore.area"));

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.BARRIER_FREE)
                    price = area.getPrice();
            }
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            return;
        }

        //Ticket erstellen
        TicketDto ticket = new TicketDto();

        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.BARRIER_FREE) {
                ticket.setPrice(area.getPrice());
                ticket.setArea(area);
            }
        }
        ticket.setShow(show);
        ticket.setDescription(show.getPerformance().getName());
        ticket.setValid(true);
        TicketHandler.addTicket(new EnhancedTicket(ticket, AreaTypeDto.BARRIER_FREE));

        buttonDeleteBarrierTicket.setDisable(false);
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);


        textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.barrierFree") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
    }

    /**
     * Adds a free-seating-choice ticket. Throws Alert if not available.
     * @param actionEvent event
     */
    public void handleFreeSeatPressed(ActionEvent actionEvent) {

        int excess = TicketHandler.getAvailableFreeseatTickets(show.getId(), areaList)-TicketHandler.numberOfSFreeseatTickets();

        double price = 0;
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE)
                price = area.getPrice();
        }

        if(excess < 0){
            for(int i = 0; i<-excess ; i++){
                TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.FREE_SEATING_CHOICE));
            }
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"),BundleManager.getBundle().getString("generic.error"),BundleManager.getBundle().getString("generic.error"));
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

            return;
        }

        if(TicketHandler.getAvailableFreeseatTickets(show.getId(), areaList)-TicketHandler.numberOfSFreeseatTickets() == 0){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("roomPlan.noMore.seatingchoice"),BundleManager.getBundle().getString("roomPlan.noMore.area"),BundleManager.getBundle().getString("roomPlan.noMore.area"));

             price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE)
                    price = area.getPrice();
            }

            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            return;
        }

        //Ticket erstellen
        TicketDto ticket = new TicketDto();
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.FREE_SEATING_CHOICE) {
                ticket.setPrice(area.getPrice());
                ticket.setArea(area);
            }
        }
        ticket.setShow(show);
        ticket.setDescription(show.getPerformance().getName());
        ticket.setValid(true);
        TicketHandler.addTicket(new EnhancedTicket(ticket, AreaTypeDto.FREE_SEATING_CHOICE));

        buttonDeleteFreeSeatTicket.setDisable(false);
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);


        textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.seatingChoice") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
    }

    /**
     * Adds a Stance ticket. Throws Alert if not available.
     * @param actionEvent event
     */
    public void handleStancePressed(ActionEvent actionEvent) {

        int excess = TicketHandler.getAvailableStanceTickets(show.getId(), areaList)-TicketHandler.numberOfStanceTickets();

        double price = 0;
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.STANCE)
                price = area.getPrice();
        }

        if(excess < 0){
            for(int i = 0; i<-excess ; i++){
                TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.STANCE));
            }
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());

            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate2"),BundleManager.getBundle().getString("generic.error"),BundleManager.getBundle().getString("generic.error"));
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.stance") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);

            return;
        }

        if(excess == 0){
            CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("roomPlan.noMore.stance"),BundleManager.getBundle().getString("roomPlan.noMore.area"),BundleManager.getBundle().getString("roomPlan.noMore.area"));

            price = 0;
            for (AreaDto area : areaList) {
                if (area.getAreaType() == AreaTypeDto.STANCE)
                    price = area.getPrice();
            }
            availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);
            textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.stance") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);
            textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());


            return;
        }

        //Ticket erstellen
        TicketDto ticket = new TicketDto();
        for (AreaDto area : areaList) {
            if (area.getAreaType() == AreaTypeDto.STANCE) {
                ticket.setPrice(area.getPrice());
                ticket.setArea(area);
            }
        }
        ticket.setShow(show);
        ticket.setDescription(show.getPerformance().getName());
        ticket.setValid(true);
        TicketHandler.addTicket(new EnhancedTicket(ticket, AreaTypeDto.STANCE));

        buttonDeleteStanceTicket.setDisable(false);

        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(), areaList);



        textAreaSeatOverview.setText(BundleManager.getBundle().getString("roomPlan.field.stance") + " " + formatter.format(price) + " "+ BundleManager.getBundle().getString("roomplan.field.currencyEuro")+"\n\n"+availableAreas);


        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
    }

    //--------------------------------------------

    /**
     * Deletes a barrierfree ticket
     * @param actionEvent event
     */
    public void handleDeleteBarrierTicket(ActionEvent actionEvent) {
        LOGGER.info("delete Barrier");
        TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.BARRIER_FREE));
        if (!TicketHandler.hasBarrierTicket())
            buttonDeleteBarrierTicket.setDisable(true);
        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(),areaList);
        textAreaSeatOverview.setText(availableAreas);
    }

    /**
     * Deletes a free-seating-choice ticket
     * @param actionEvent event
     */
    public void handleDeleteFreeSeatTicket(ActionEvent actionEvent) {
        LOGGER.info("delete FreeSeat");
        TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.FREE_SEATING_CHOICE));
        if (!TicketHandler.hasSeatTicket())
            buttonDeleteFreeSeatTicket.setDisable(true);
        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(),areaList);
        textAreaSeatOverview.setText(availableAreas);
    }

    /**
     * Deletes a Stance ticket
     * @param actionEvent event
     */
    public void handleDeleteStanceTicket(ActionEvent actionEvent) {
        LOGGER.info("delete stance");
        TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.STANCE));
        if (!TicketHandler.hasStanceTicket())
            buttonDeleteStanceTicket.setDisable(true);
        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(),areaList);
        textAreaSeatOverview.setText(availableAreas);
    }

    /**
     * Deletes a VIP ticket
     * @param actionEvent event
     */
    public void handleDeleteVIPTicket(ActionEvent actionEvent) {
        LOGGER.info("delete VIP");
        TicketHandler.deleteTicket(new EnhancedTicket(new TicketDto(), AreaTypeDto.VIP));
        if (!TicketHandler.hasVIPTicket())
            buttonDeleteVIPTicket.setDisable(true);
        textAreaAreaTicketsOverview.setText(TicketHandler.getStringRepresentation());
        availableAreas = TicketHandler.getStringAvailableAreaTickets(show.getId(),areaList);
        textAreaSeatOverview.setText(availableAreas);
    }

    /**
     * Method which will be called if the user clicks the close button
     * This will close the current window
     */
    public void handleCancel() {
        LOGGER.info("User clicked close window!");
        handleSellTickets();
    }
}
