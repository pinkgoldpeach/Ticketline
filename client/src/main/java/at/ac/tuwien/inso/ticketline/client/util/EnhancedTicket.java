package at.ac.tuwien.inso.ticketline.client.util;


import at.ac.tuwien.inso.ticketline.dto.AreaTypeDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;

/**
 * Created by Stefan on 17.05.2016.
 */
public class EnhancedTicket {

    private TicketDto ticket;
    AreaTypeDto areaType;
    private int row;
    private int seat;

    public EnhancedTicket(TicketDto ticket, int row, int seat){
        this.ticket = ticket;
        this.row = row;
        this.seat = seat;
        areaType = null;
    }

    public EnhancedTicket(TicketDto ticket, AreaTypeDto areaType){
        this.ticket = ticket;
        this.areaType = areaType;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public AreaTypeDto getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeDto areaType) {
        this.areaType = areaType;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnhancedTicket that = (EnhancedTicket) o;
        if(areaType == null){
            //return (ticket == that.ticket);
            return (this.getRow() == that.getRow() && this.getSeat() == that.getSeat());
        }

        else{
            return areaType.name().equals(that.areaType.name());
        }

    }

    @Override
    public int hashCode() {
        return 0;
    }
}
