package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.ReservationDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Hannes on 24.05.2016.
 */
public class UITicket implements UIObject {

    private TicketDto origin;
    private StringProperty performanceName;
    private StringProperty date;
    private DoubleProperty price;
    private StringProperty place;
    private StringProperty customerName;

    private static NumberFormat formatter = new DecimalFormat("#0.00");

    public UITicket(TicketDto ticketDto) {
        this.origin = ticketDto;
        this.performanceName = new SimpleStringProperty(ticketDto.getShow().getPerformance().getName());
        this.date = new SimpleStringProperty(DateFormat.getDateFormat(ticketDto.getShow().getDateOfPerformance()));
        this.price = new SimpleDoubleProperty(ticketDto.getPrice());
        if (ticketDto.getSeat()!=null) {
            place = new SimpleStringProperty(ticketDto.getSeat().getName().toUpperCase()+""+ticketDto.getSeat().getOrder());
        } else if (ticketDto.getArea()!=null) {
            place = new SimpleStringProperty(ticketDto.getArea().getAreaType().name());
        } else {
            place = new SimpleStringProperty("unknown");
        }
        ReservationDto res = ticketDto.getReservation();
        if (res!=null){
            if (res.getCustomer()!=null) {
                customerName = new SimpleStringProperty(res.getCustomer().getFirstName()+" "+res.getCustomer().getLastName());
            }
        }

    }

    public TicketDto getOrigin() {
        return origin;
    }

    public void setOrigin(TicketDto origin) {
        this.origin = origin;
    }

    public String getPerformanceName() {
        return performanceName.get();
    }

    public StringProperty performanceNameProperty() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName.set(performanceName);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public StringProperty getPlaceProperty() {return place;}

    public StringProperty getCustomerName() {return customerName;}

    @Override
    public Integer getID() {
        return origin.getId();
    }

    public void addCustomer(ReservationDto reservationDto) {
        if (reservationDto.getCustomer()!=null){
            customerName = new SimpleStringProperty(reservationDto.getCustomer().getFirstName()+" "+reservationDto.getCustomer().getLastName());
        }
    }

    public void addCustomer(String customername) {
            customerName = new SimpleStringProperty(customername);

    }
}
