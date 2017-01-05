package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;


/**
 * The show entity.
 */
@Entity
public class Show implements Serializable {

    private static final long serialVersionUID = 2398987661302928431L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean canceled;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPerformance;

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "show")
    private List<Ticket> tickets;
*/
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    /**
     * Instantiates a new show.
     */
    public Show() {
    }

    /**
     * Instantiates a new show.
     *
     * @param canceled the canceled
     * @param date the date
     * @param room the room
     * @param performance the performance
     */
    public Show(Boolean canceled, Date date, Room room, Performance performance) {
        this.canceled = canceled;
        this.dateOfPerformance = date;
        this.room = room;
        this.performance = performance;
    }

    /**
     * Instantiates a new show.
     *
     * @param id the id
     * @param canceled the canceled
     * @param date the date
     * @param room the room
     * @param performance the performance
     */
    public Show(Integer id, Boolean canceled, Date date, Room room, Performance performance) {
        this.id = id;
        this.canceled = canceled;
        this.dateOfPerformance = date;
        this.room = room;
        this.performance = performance;
    }

    /**
     * Instantiates a new show.
     *
     * @param id the id
     * @param canceled the canceled
     * @param date the date
     * @param room the room
     * @param performance the performance
     * @param tickets the tickets
     */
    /*
    public Show(Integer id, Boolean canceled, Date date, Room room, Performance performance, List<Ticket> tickets) {
        this.id = id;
        this.canceled = canceled;
        this.dateOfPerformance = date;
        this.room = room;
        this.performance = performance;
        this.tickets = tickets;
    }
     */

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the canceled.
     *
     * @return the canceled
     */
    public Boolean getCanceled() {
        return canceled;
    }

    /**
     * Sets the canceled.
     *
     * @param canceled the new canceled
     */
    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * Gets the date of performance.
     *
     * @return the date of performance
     */
    public Date getDateOfPerformance() {
        return dateOfPerformance;
    }

    /**
     * Sets the date of performance.
     *
     * @param dateOfPerformance the new date of performance
     */
    public void setDateOfPerformance(Date dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }

    /**
     * Gets the tickets.
     *
     * @return the tickets
     */
    /*
    public List<Ticket> getTickets() {
        return tickets;
    }*/

    /**
     * Sets the tickets.
     *
     * @param tickets the new tickets
     */
    /*
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }*/

    /**
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room.
     *
     * @param room the new room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the performance.
     *
     * @return the performance
     */
    public Performance getPerformance() {
        return performance;
    }

    /**
     * Sets the performance.
     *
     * @param performance the new performance
     */
    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

}
