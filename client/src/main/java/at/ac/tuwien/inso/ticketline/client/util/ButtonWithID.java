package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.scene.control.Button;

/**
 * Created by Hannes on 21.05.2016.
 */
public class ButtonWithID{
    private int row;
    private int seat;
    private Button button;
    private boolean chosen;
    private TicketDto ticket;
    private SeatDto actualSeat;



    private boolean repeatedlyClickedOn;

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    String descriptionText;


    public ButtonWithID(int row, int seat, Button button, SeatDto actualSeat, String descriptionText){
        this.row = row;
        this.seat = seat;
        this.button = button;
        chosen = false;
        ticket = null;
        this.actualSeat = actualSeat;
        this.descriptionText = descriptionText;
        this.repeatedlyClickedOn = false;
    }

    public boolean isRepeatedlyClickedOn() {
        return repeatedlyClickedOn;
    }

    public void setRepeatedlyClickedOn(boolean repeatedlyClickedOn) {
        this.repeatedlyClickedOn = repeatedlyClickedOn;
    }

    public SeatDto getActualSeat() {
        return actualSeat;
    }

    public void setActualSeat(SeatDto actualSeat) {
        this.actualSeat = actualSeat;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
