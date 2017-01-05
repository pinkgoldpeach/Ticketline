package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.ReservationDto;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Hannes on 23.05.2016.
 */
public class UIReservation implements UIObject {
    private ReservationDto origin;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty showDescription;
    private IntegerProperty numberOfTickets;

    public ReservationDto getOrigin() {
        return origin;
    }

    public void setOrigin(ReservationDto origin) {
        this.origin = origin;
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

    public String getShowDescription() {
        return showDescription.get();
    }

    public StringProperty showDescriptionProperty() {
        return showDescription;
    }

    public void setShowDescription(String showDescription) {
        this.showDescription.set(showDescription);
    }

    public int getNumberOfTickets() {
        return numberOfTickets.get();
    }

    public IntegerProperty numberOfTicketsProperty() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets.set(numberOfTickets);
    }

    public UIReservation(ReservationDto reservationDto) {
        this.origin = reservationDto;
        this.firstname = new SimpleStringProperty(reservationDto.getCustomer().getFirstName());
        this.lastname = new SimpleStringProperty(reservationDto.getCustomer().getLastName());
        if (!reservationDto.getTickets().isEmpty()) {
            this.showDescription = new SimpleStringProperty(reservationDto.getTickets().get(0).getShow().getPerformance().getName());
        }
        int not = reservationDto.getTickets().size();
        this.numberOfTickets = new SimpleIntegerProperty(not);
    }


    @Override
    public Integer getID() {
        return origin.getId();
    }

}