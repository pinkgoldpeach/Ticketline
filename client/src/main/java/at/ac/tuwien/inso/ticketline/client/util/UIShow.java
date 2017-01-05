package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Wrapper for UI
 */
public class UIShow {

    private ShowDto origin;
    private StringProperty date;
    private StringProperty location;
    private ObjectProperty<RoomDto> room;
    private StringProperty performance;
    private Date dateOrigin;
    private StringProperty roomName;


    public UIShow(ShowDto showDto) throws ServiceException {
        this.origin = showDto;
        dateOrigin = showDto.getDateOfPerformance();
        this.date = new SimpleStringProperty(DateFormat.getDateFormat(showDto.getDateOfPerformance()));
        String loc = showDto.getRoom().getLocation().getName();
        if (loc != null) {
            this.location = new SimpleStringProperty(loc);
        } else {
            this.location = new SimpleStringProperty("unknown");
        }
        if (showDto.getRoom() != null) {
            this.room = new SimpleObjectProperty<>(showDto.getRoom());
            roomName = new SimpleStringProperty(showDto.getRoom().getName());
        } else {
            this.room = null;
            roomName = new SimpleStringProperty("unknown");
        }
        this.performance = new SimpleStringProperty(showDto.getPerformance().getName());

    }

    public LocalDate getDateOrigin() {return dateOrigin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();}

    public ShowDto getShow() {
        return origin;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty locationProperty() {
        return location;
    }


    public String getRoom() {
        return room.getName();
    }


    public void setRoom(RoomDto room) {this.room = new SimpleObjectProperty<>(room);}

    public StringProperty performanceProperty() {
        return performance;
    }

    public StringProperty roomName() {
        return roomName;
    }




}
