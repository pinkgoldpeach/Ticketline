package at.ac.tuwien.inso.ticketline.client.util;


import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.*;
import at.ac.tuwien.inso.ticketline.dto.*;
import com.stripe.model.Token;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import at.ac.tuwien.inso.ticketline.dto.AreaTypeDto;
import at.ac.tuwien.inso.ticketline.dto.ReceiptDto;
import at.ac.tuwien.inso.ticketline.dto.ReceiptEntryDto;


import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Hannes on 17.05.2016.
 */
@Component
public class TicketHandler {

    private static List<EnhancedTicket> tickets = new ArrayList<>();
    //private static ReservationPublishDto reservation;
    private static CustomerDto customer;
    private static EmployeeDto employee;

    private static List<ButtonWithID> buttonWithIDList;

    private static double total = 0;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TicketHandler.class);


    static TicketService ticketService;
    static SeatService seatService;
    static ReceiptService receiptService;
    static ReservationService reservationService;
    static AreaService areaService;

    @Autowired
    public void setAreaService(AreaService areaService) {
        TicketHandler.areaService = areaService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        TicketHandler.ticketService = ticketService;
    }

    @Autowired
    public void setSeatService(SeatService seatService) {
        TicketHandler.seatService = seatService;
    }

    @Autowired
    public void setReceiptService(ReceiptService receiptService) {
        TicketHandler.receiptService = receiptService;
    }

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        TicketHandler.reservationService = reservationService;
    }

    private static NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Resets the buttonWithIDList, so that buttons are not chosen etc. when the next instance of SellReserveController is opened
     */
    public static void removeButtonWithIDs() {
        buttonWithIDList = new ArrayList<>();
    }

    /**
     * Creates a receipt including all chosen tickets
     *
     * @param ticket to be added to database
     */
    public static void addTicket(EnhancedTicket ticket) {
        tickets.add(ticket);
        recalculateTotal();
    }

    /**
     * Needs to be used after Tickets are sold or reserved. Clears local Ticket storage.
     */
    public static void clearTicketList() {
        tickets.clear();
    }

    /**
     * Returns currently chosen Seats/Buttons (in a wrapper class)
     * @return list of buttons
     */
    public static List<ButtonWithID> getButtonWithIDList() {
        return buttonWithIDList;
    }

    /**
     * Sets List with chosen buttons. Used to persist chosen Seats/Buttons, in case roomPlan is re-opened.
     * @param buttonWithIDList the list to store for the next calling of room plan
     */
    public static void setButtonWithIDList(List<ButtonWithID> buttonWithIDList) {
        TicketHandler.buttonWithIDList = buttonWithIDList;
    }

    /**
     * Deletes a ticket. If it is a seat ticket, row and seat are taken to compare; if it's an area ticket, area name is taken.
     * @param ticket The ticket to be deleted, wrapped in an Enhanced Ticket.
     */
    public static void deleteTicket(EnhancedTicket ticket) {
        tickets.remove(ticket);
        recalculateTotal();
    }

    //----------------------------------------------------

    /**
     * Returns number of chosen VIP tickets
     * @return number of chosen VIP tickets
     */
    public static int numberOfVIPTickets() {
        int number = 0;
        for (EnhancedTicket t : tickets) {
            if (t.areaType.name().equals(AreaTypeDto.VIP.name()))
                number++;
        }
        return number;
    }

    /**
     * Returns number of chosen Stance tickets
     * @return number of chosen Stance tickets
     */
    public static int numberOfStanceTickets() {
        int number = 0;
        for (EnhancedTicket t : tickets) {
            if (t.areaType.name().equals(AreaTypeDto.STANCE.name()))
                number++;
        }
        return number;
    }

    /**
     * Returns number of chosen Free-seating choice tickets
     * @return number of chosen Free-seating choice tickets
     */
    public static int numberOfSFreeseatTickets() {
        int number = 0;
        for (EnhancedTicket t : tickets) {
            if (t.areaType.name().equals(AreaTypeDto.FREE_SEATING_CHOICE.name()))
                number++;
        }
        return number;
    }

    /**
     * Returns number of barrierfree tickets
     * @return number of barrierfree tickets
     */
    public static int numberOfBarrierfreeTickets() {
        int number = 0;
        for (EnhancedTicket t : tickets) {

            if (t.areaType.name().equals(AreaTypeDto.BARRIER_FREE.name()))
                number++;
        }
        return number;
    }


    //----------------------------------------------------

    /**
     * Returns true, if a stance ticket was chosen
     * @return true, if a stance ticket was chosen
     */
    public static boolean hasStanceTicket() {
        boolean has = false;
        for (EnhancedTicket t : tickets) {
            if (t.areaType != null) {
                if (t.areaType.name().equals(AreaTypeDto.STANCE.name()))
                    has = true;
            }
        }
        return has;
    }

    /**
     * Returns true, if a barrier-free ticket was chosen
     * @return true, if a barrier-free ticket was chosen
     */
    public static boolean hasBarrierTicket() {
        boolean has = false;
        for (EnhancedTicket t : tickets) {
            if (t.areaType != null) {
                if (t.areaType.name().equals(AreaTypeDto.BARRIER_FREE.name()))
                    has = true;
            }
        }
        return has;
    }

    /**
     * Returns true, if a free-seating-choice ticket was chosen
     * @return true, if a free-seating-choice ticket was chosen
     */
    public static boolean hasSeatTicket() {
        boolean has = false;
        for (EnhancedTicket t : tickets) {
            if (t.areaType != null) {
                if (t.areaType.name().equals(AreaTypeDto.FREE_SEATING_CHOICE.name()))
                    has = true;
            }
        }
        return has;
    }

    /**
     * Returns true if VIP ticket was chosen
     * @return true if VIP ticket was chosen
     */
    public static boolean hasVIPTicket() {
        boolean has = false;
        for (EnhancedTicket t : tickets) {
            if (t.areaType != null) {
                if (t.areaType.name().equals(AreaTypeDto.VIP.name()))
                    has = true;
            }
        }
        return has;
    }

    //----------------------------------------------------

    /**
     * Returns ID of Stance area, if one exists, otherwise returns 0
     * @param areaList the areas which to check
     * @return ID of area (or 0)
     */
    public static int getStanceAreaID(List<AreaDto> areaList) {
        boolean has = false;
        for (AreaDto a : areaList) {
            if (a.getAreaType().name().equals(AreaTypeDto.STANCE.name()))
                return a.getId();
        }
        return 0;
    }

    /**
     * Returns ID of Barrier-free area, if one exists, otherwise returns 0
     * @param areaList the areas which to check
     * @return ID of area (or 0)
     */
    public static int getBarrierfreeAreaID(List<AreaDto> areaList) {
        boolean has = false;
        for (AreaDto a : areaList) {
            if (a.getAreaType().name().equals(AreaTypeDto.BARRIER_FREE.name()))
                return a.getId();
        }
        return 0;
    }

    /**
     * Returns ID of Free-seating-choice area, if one exists, otherwise returns 0
     * @param areaList the areas which to check
     * @return ID of area (or 0)
     */
    public static int getSeatAreaID(List<AreaDto> areaList) {
        boolean has = false;
        for (AreaDto a : areaList) {
            if (a.getAreaType().name().equals(AreaTypeDto.FREE_SEATING_CHOICE.name()))
                return a.getId();
        }
        return 0;
    }

    /**
     * Returns ID of VIP area, if one exists, otherwise returns 0
     * @param areaList the areas which to check
     * @return ID of area (or 0)
     */
    public static int getVIPAreaID(List<AreaDto> areaList) {
        boolean has = false;
        for (AreaDto a : areaList) {
            if (a.getAreaType().name().equals(AreaTypeDto.VIP.name()))
                return a.getId();
        }
        return 0;
    }

    //----------------------------------------------------

    /**
     * Returns number of available free-seating-choice area tickets
     * @param showID at which show
     * @param areaList the areas, needed to get the ID of the specific area
     * @return available free seating choice tickets
     */
    public static int getAvailableFreeseatTickets(int showID, List<AreaDto> areaList) {
        if (getSeatAreaID(areaList) == 0)
            return 0;
        try {
            return areaService.getNumberOfAvailableTickets(showID, getSeatAreaID(areaList));
        } catch (ServiceException e) {
            LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
        }
        return 0;
    }

    /**
     * Returns number of available VIP area tickets
     * @param showID at which show
     * @param areaList the areas, needed to get the ID of the specific area
     * @return available VIP tickets
     */
    public static int getAvailableVIPTickets(int showID, List<AreaDto> areaList) {
        if (getVIPAreaID(areaList) == 0)
            return 0;
        try {
            int freetickets = areaService.getNumberOfAvailableTickets(showID, getVIPAreaID(areaList));
            return freetickets;
        } catch (ServiceException e) {
            LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
        }
        return 0;
    }

    /**
     * Returns number of available barrier-free area tickets
     * @param showID at which show
     * @param areaList the areas, needed to get the ID of the specific area
     * @return available barrierfree tickets
     */
    public static int getAvailableBarrierfreeTickets(int showID, List<AreaDto> areaList) {
        if (getBarrierfreeAreaID(areaList) == 0)
            return 0;
        try {
            return areaService.getNumberOfAvailableTickets(showID, getBarrierfreeAreaID(areaList));
        } catch (ServiceException e) {
            LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
        }
        return 0;
    }

    /**
     * Returns number of available stance area tickets
     * @param showID at which show
     * @param areaList the areas, needed to get the ID of the specific area
     * @return available stance tickets
     */
    public static int getAvailableStanceTickets(int showID, List<AreaDto> areaList) {
        if (getStanceAreaID(areaList) == 0)
            return 0;
        try {
            return areaService.getNumberOfAvailableTickets(showID, getStanceAreaID(areaList));
        } catch (ServiceException e) {
            LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
        }
        return 0;
    }

    //----------------------------------------------------


    /**
     * Returns a string-representation of all available area tickets
     * @param showID from which show
     * @param areaList areas, that the representation should be made of
     * @return string-representation of all available area tickets
     */
    public static String getStringAvailableAreaTickets(int showID, List<AreaDto> areaList) {
        String returnstring = BundleManager.getBundle().getString("roomPlan.field.available");
        if (getBarrierfreeAreaID(areaList) != 0) {
            returnstring += (getAvailableBarrierfreeTickets(showID, areaList) - numberOfBarrierfreeTickets()) + " x " + BundleManager.getBundle().getString("roomPlan.label.BARRIER_FREE");
        }
        if (getSeatAreaID(areaList) != 0) {
            returnstring += (getAvailableFreeseatTickets(showID, areaList) - numberOfSFreeseatTickets()) + " x " + BundleManager.getBundle().getString("roomPlan.label.FREE_SEATING_CHOICE");
        }
        if (getStanceAreaID(areaList) != 0) {
            returnstring += (getAvailableStanceTickets(showID, areaList) - numberOfStanceTickets()) + " x " + BundleManager.getBundle().getString("roomPlan.label.STANCE");
        }
        if (getVIPAreaID(areaList) != 0) {
            returnstring += (getAvailableVIPTickets(showID, areaList) - numberOfVIPTickets()) + " x " + BundleManager.getBundle().getString("roomPlan.label.VIP") + "\n";
        }

        return returnstring;
    }

    /**
     * Returns the total (price) of all currently chosen tickets
     * @return total
     */
    public static double getTotal() {
        return total;
    }

    /**
     * Returns a string-representation of all currently chosen tickets (price + seat/row or price + area) in a LISTED FORM (not 3x area)
     * @return string-representation of all currently chosen tickets (price + seat/row or price + area)
     */
    public static String getStringRepresentation() {
        String output = "";
        double sum = 0;
        for (EnhancedTicket ticket : tickets) {
            if (ticket.getTicket().getShow().getRoom().getSeatChoice()) {
                output += "1 x " + formatter.format(ticket.getTicket().getPrice()) + " € - " + BundleManager.getBundle().getString("roomPlan.field.row") + " " + AlphabetMapper.getLetter(ticket.getRow()) +
                        " / " + BundleManager.getBundle().getString("roomPlan.field.seat") + " " + (ticket.getSeat() + 1) + "\n";
                sum += ticket.getTicket().getPrice();
            } else {
                String areaType = ticket.getAreaType().name();
                String area = BundleManager.getBundle().getString("roomPlan.field." + areaType);
                output += "1 x " + formatter.format(ticket.getTicket().getPrice()) + " € - " + area + "\n";
                sum += ticket.getTicket().getPrice();
            }
        }

        output += "------------------------------\n";
        output += ("    " + formatter.format(sum) + " €");

        return output;
    }

    /**
     * Used internally to recalculate the total
     */
    private static void recalculateTotal() {
        double sum = 0;
        for (EnhancedTicket ticket : tickets) {
            sum += ticket.getTicket().getPrice();
        }
        total = sum;
    }

    /**
     * Unlocks all currently locked seats from a specific show
     * @param show the show, which to unlock all seats from
     */
    public static void unlockAllSeats(ShowDto show) {
        try {
            seatService.unlockSeats(show.getId());
        } catch (ServiceException e) {
            LOGGER.error("seatService.unlockSeats failed: "+e.getMessage());
        }
    }

    /**
     * Returns true, if any seats/areas are chosen
     * @return true, if any seats/areas are chosen, false otherwise
     */
    public static boolean hasTickets() {
        return !tickets.isEmpty();
    }

    /**
     * Returns the currently chosen Customer
     * @return the customer
     */
    public static CustomerDto getCustomer() {
        return customer;
    }

    /**
     * Sets the current Customer, who will be on the receipt and reservations
     * @param customer the customer
     */
    public static void setCustomer(CustomerDto customer) {
        TicketHandler.customer = customer;
    }

    /**
     * Gets a list of all tickets (sold and reserved)
     * @return list of all tickets
     */
    public static List<TicketDto> receiveAllTickets() {
        try {
            return ticketService.getAllTickets();
        } catch (ServiceException e) {
            LOGGER.error("ticketService.getAllTickets failed: "+e.getMessage());
        }
        return null;
    }

    /**
     * Gets a nicely readable and continuous string-representation of all chosen tickets (used for Alerts etc.)
     * @return string-representation
     */
    public static String getSeatsRepresentation() {
        String output = "";
        if (hasSeatTicket() || hasStanceTicket() || hasBarrierTicket() || hasVIPTicket()) {
            int vip = numberOfVIPTickets();
            int stance = numberOfStanceTickets();
            int barrier = numberOfBarrierfreeTickets();
            int freeseat = numberOfSFreeseatTickets();

            if (vip > 0)
                output += "" + vip + "x "+BundleManager.getBundle().getString("salesPage.button.vip")+", ";
            if (stance > 0)
                output += "" + stance + "x "+BundleManager.getBundle().getString("salesPage.button.stance")+", ";
            if (barrier > 0)
                output += "" + barrier + "x "+BundleManager.getBundle().getString("salesPage.button.barrierfree")+", ";
            if (freeseat > 0)
                output += "" + freeseat + "x "+BundleManager.getBundle().getString("salesPage.button.freeseatchoice")+", ";

            return output.substring(0, output.length() - 2);
        }
        boolean first = true;
        for (EnhancedTicket t : tickets) {
            if (first) {
                if (t.getAreaType() != null) {
                } else {
                    output += (t.getSeat() + 1) + "" + AlphabetMapper.getLetter(t.getRow());
                }
                first = false;
            } else {
                if (t.getAreaType() != null) {
                } else {
                    output += ", " + (t.getSeat() + 1) + "" + AlphabetMapper.getLetter(t.getRow());

                }
            }
        }
        return output;
    }

    /**
     * Returns, whether a seat was chosen the last time the roomPlan was shown
     * @param row row of the seat to check
     * @param seat seat-number of the seat to check
     * @return if button was chosen the last time in the roomplan
     */
    public static boolean buttonWasChosen(int row, int seat) {
        if (buttonWithIDList != null) {
            for (ButtonWithID b : buttonWithIDList) {
                if (b.getRow() == row && b.getSeat() == seat)
                    return b.isChosen();
            }
        }
        return false;
    }

    /**
     * Returns, whether a seat was clicked on once the last itme the
     * roomPlan was shown (even if the seat wasn't chosen at the time of closing the roomplan)
     * @param row the row
     * @param seat the seat
     * @return if button was clicked on once the last time in the roomplan
     */
    public static boolean buttonWasClickedOn(int row, int seat) {
        if (buttonWithIDList != null) {
            for (ButtonWithID b : buttonWithIDList) {
                if (b.getRow() == row && b.getSeat() == seat)
                    return b.isRepeatedlyClickedOn();
            }
        }
        return false;
    }

    /**
     * Sends a reservation with all the chosen tickets
     * @param hasSeatChoice whether tickets are areas or seats
     * @param showID the show
     * @param areaList the areas
     * @return true if successful
     * @throws ServiceException if saveReseravion failed, or bookTicketsForArea failed
     */
    public static boolean sendReservation(boolean hasSeatChoice, int showID, List<AreaDto> areaList) throws ServiceException{
        ReservationPublishDto reservation = new ReservationPublishDto();
        reservation.setTickets(new ArrayList<>());

        //if seats are present, create and add TicketPublishDtos to reservation
        if (hasSeatChoice) {
            for (EnhancedTicket t : tickets) {
                TicketPublishDto pubticket = new TicketPublishDto();
                pubticket.setDescription(t.getTicket().getDescription());
                pubticket.setPrice(t.getTicket().getPrice());
                pubticket.setShowId(t.getTicket().getShow().getId());
                if (t.getAreaType() == null) {
                    pubticket.setSeatId(t.getTicket().getSeat().getId());
                } else {
                    pubticket.setAreaId((t.getTicket().getArea().getId()));
                }
                reservation.getTickets().add(pubticket);
            }
        }

        reservation.setCustomerId(customer.getId());

        //Send reservation to server
        MessageDto answer = null;
        answer = reservationService.saveReservation(reservation);

        UsedAreasDto dto = new UsedAreasDto();

        //if areas are present, send the tickets after the reservation (needed for concurrency)
        if (!hasSeatChoice) {
            dto.setReservationNumber(answer.getText());
            HashMap<Integer, Integer> areatickets = new HashMap<>();

            if (hasStanceTicket()) {
                try {
                    if (areaService.getNumberOfAvailableTickets(showID, getStanceAreaID(areaList)).intValue() >= numberOfStanceTickets()) {
                        areatickets.put(getStanceAreaID(areaList), numberOfStanceTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.stance"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
                } catch (ServiceException e) {
                    LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
                }
            }
            if (hasSeatTicket()) {
                try {
                    if (areaService.getNumberOfAvailableTickets(showID, getSeatAreaID(areaList)).intValue() >= numberOfSFreeseatTickets()) {
                        areatickets.put(getSeatAreaID(areaList), numberOfSFreeseatTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.freeSeatChoice"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
                } catch (ServiceException e) {
                    LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
                }
            }
            if (hasVIPTicket()) {
                try {
                    if (areaService.getNumberOfAvailableTickets(showID, getVIPAreaID(areaList)).intValue() >= numberOfVIPTickets()) {
                        areatickets.put(getVIPAreaID(areaList), numberOfVIPTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.vip"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
                } catch (ServiceException e) {
                    LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
                }
            }
            if (hasBarrierTicket()) {
                try {
                    if (areaService.getNumberOfAvailableTickets(showID, getBarrierfreeAreaID(areaList)).intValue() >= numberOfBarrierfreeTickets()) {
                        areatickets.put(getBarrierfreeAreaID(areaList), numberOfBarrierfreeTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.barrierFree"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
                } catch (ServiceException e) {
                    LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
                }
            }

            dto.setAmountPerArea(areatickets);
            dto.setShowID(showID);

            ticketService.bookTicketsForArea(dto);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(BundleManager.getBundle().getString("findby.reservationno"));
        alert.setHeaderText(BundleManager.getBundle().getString("findby.reservationno"));
        alert.setContentText(BundleManager.getBundle().getString("findby.reservationno"));

        //Label label = new Label("ReservationNumber:");

        TextArea textArea = new TextArea(answer.getText());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        //expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);

        alert.showAndWait();

        return true;
    }

    /**
     * Resets the customer and employee. Used when SellReserve Page gets closed.
     */
    public static void resetCustomerAndEmployee() {
        customer = null;
        employee = null;
        total = 0;
    }

    /**
     * Sends the tickets and the according receipt and manages the payment process via Stripe
     * Also checks seats/areas are still available and throws Alert, if they aren't
     * @param token Stripe token
     * @param show the show
     * @param areaList the areas
     * @return true, if successful
     * @throws ServiceException gets thrown, if during the payment process with stripe, someone else bought the tickets
     */
    public static boolean sendTicketsAndReceipt(Token token, ShowDto show, List<AreaDto> areaList) throws ServiceException  {

        int showID = show.getId();

        List<ReceiptEntryDto> receiptEntries = new ArrayList<>();

        List<Integer> idlist = new ArrayList<>();

        if (show.getRoom().getSeatChoice()) {
            for (EnhancedTicket t : tickets) {
                TicketPublishDto pubticket = new TicketPublishDto();
                pubticket.setDescription(t.getTicket().getDescription());
                pubticket.setPrice(t.getTicket().getPrice());
                pubticket.setShowId(t.getTicket().getShow().getId());
                if (t.getAreaType() == null) {
                    pubticket.setSeatId(t.getTicket().getSeat().getId());
                } else {
                    pubticket.setAreaId((t.getTicket().getArea().getId()));
                }

                MessageDto success = null;

                try {
                    success = ticketService.saveTicket(pubticket);
                } catch (ServiceException e) {
                    CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("payment.alert.tooLate"),"Error","Error");
                    return false;
                }

                int ticketId = Integer.parseInt(success.getText());

                ReceiptEntryDto receiptentry = new ReceiptEntryDto();
                receiptentry.setTicketId(ticketId);
                receiptentry.setPosition(1);
                receiptentry.setAmount(1);
                receiptentry.setUnitPrice(pubticket.getPrice());
                receiptEntries.add(receiptentry);
                idlist.add(ticketId);
            }
        } else {

            UsedAreasDto dto = new UsedAreasDto();
            dto.setReservationNumber("");

            HashMap<Integer, Integer> areatickets = new HashMap<>();

            if (hasStanceTicket()) {
                    if (areaService.getNumberOfAvailableTickets(showID, getStanceAreaID(areaList)).intValue() >= numberOfStanceTickets()) {
                        areatickets.put(getStanceAreaID(areaList), numberOfStanceTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.stance"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
            }
            if (hasSeatTicket()) {
                    if (areaService.getNumberOfAvailableTickets(showID, getSeatAreaID(areaList)).intValue() >= numberOfSFreeseatTickets()) {
                        areatickets.put(getSeatAreaID(areaList), numberOfSFreeseatTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.freeSeatChoice"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
            }
            if (hasVIPTicket()) {
                    if (areaService.getNumberOfAvailableTickets(showID, getVIPAreaID(areaList)).intValue() >= numberOfVIPTickets()) {
                        areatickets.put(getVIPAreaID(areaList), numberOfVIPTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.vip"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
            }
            if (hasBarrierTicket()) {
                    if (areaService.getNumberOfAvailableTickets(showID, getBarrierfreeAreaID(areaList)).intValue() >= numberOfBarrierfreeTickets()) {
                        areatickets.put(getBarrierfreeAreaID(areaList), numberOfBarrierfreeTickets());
                    } else {
                        CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.barrierFree"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                        return false;
                    }
            }

            dto.setAmountPerArea(areatickets);
            dto.setShowID(showID);

            BookedAreaTicketsDto returnDto = null;
            returnDto = ticketService.bookTicketsForArea(dto);
            List<TicketDto> ticketlist = returnDto.getTicketDtos();

            int i = 0;
            for (EnhancedTicket t : tickets) {
                idlist.add(ticketlist.get(i).getId());

                ReceiptEntryDto receiptentry = new ReceiptEntryDto();
                receiptentry.setTicketId(ticketlist.get(i).getId());

                receiptentry.setPosition(1);
                receiptentry.setAmount(1);
                receiptentry.setUnitPrice(ticketlist.get(i).getPrice());
                receiptEntries.add(receiptentry);
                i++;
            }
        }

        ReceiptDto receipt = new ReceiptDto();
        receipt.setCustomerDto(customer);
        receipt.setTransactionDate(new Date());

        receipt.setReceiptEntryDtos(receiptEntries);
        receipt.setPerformanceName(tickets.get(0).getTicket().getShow().getPerformance().getName());

        if (token != null) {
            MethodOfPaymentDto methodOfPaymentDto = new MethodOfPaymentDto();
            StripeDto mop = new StripeDto();
            mop.setToken(token.getId());
            methodOfPaymentDto.setStripeDto(mop);
            receipt.setMethodOfPaymentDto(methodOfPaymentDto);
        } else {
            receipt.setMethodOfPaymentDto(new MethodOfPaymentDto());
        }

        if (receipt.getMethodOfPaymentDto().getStripeDto() == null) {
            receipt.setTransactionState(TransactionStateDto.PAID);
        } else {
            receipt.setTransactionState(TransactionStateDto.IN_PROCESS);
        }


        try {
            receiptService.saveReceipt(receipt);
        } catch (ServiceException e) {
            CancelTicketDto dto = new CancelTicketDto();
            dto.setIds(idlist);
            ticketService.deleteTickets(dto);
            throw new ServiceException(BundleManager.getBundle().getString("ticketHandler.serviceException.saveReceipt"));
        }
        return true;
    }

    /**
     * Checks if the currently chosen area tickets are still available on the server.
     * If they're not, the according number of tickets gets removed on the client side and the user gets notified.
     * @param showID the show
     * @param areaList the areas
     * @return returns true, if all area tickets are still available
     */
    public static boolean checkIfEnoughAreaTicketsAvailable(int showID, List<AreaDto> areaList) {
        if (hasStanceTicket()) {
            try {
                if (areaService.getNumberOfAvailableTickets(showID, getStanceAreaID(areaList)).intValue() >= numberOfStanceTickets()) {
                } else {
                    CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.stance"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                    return false;
                }
            } catch (ServiceException e) {
                LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
            }
        }
        if (hasSeatTicket()) {
            try {
                if (areaService.getNumberOfAvailableTickets(showID, getSeatAreaID(areaList)).intValue() >= numberOfSFreeseatTickets()) {
                } else {
                    CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.freeSeatChoice"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                    return false;
                }
            } catch (ServiceException e) {
                LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
            }
        }
        if (hasVIPTicket()) {
            try {
                if (areaService.getNumberOfAvailableTickets(showID, getVIPAreaID(areaList)).intValue() >= numberOfVIPTickets()) {
                } else {
                    CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.vip"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                    return false;
                }
            } catch (ServiceException e) {
                LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
            }
        }
        if (hasBarrierTicket()) {
            try {
                if (areaService.getNumberOfAvailableTickets(showID, getBarrierfreeAreaID(areaList)).intValue() >= numberOfBarrierfreeTickets()) {
                } else {
                    CustomAlert.throwWarningWindow(BundleManager.getBundle().getString("ticketHandler.area.barrierFree"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"), BundleManager.getBundle().getString("ticketHandler.area.overbooked"));
                    return false;
                }
            } catch (ServiceException e) {
                LOGGER.error("areaService.getNumberOfAvailableTickets failed: "+e.getMessage());
            }
        }

        return true;
    }
}
