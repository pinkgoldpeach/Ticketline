package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dev on 05/05/16.
 */
@Entity
public class Area implements Serializable {

    private static final long serialVersionUID = -3799343962419959019L;

    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AreaType areaType;

    private long ticketAmount;

    private double price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public long getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(long ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
