package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@Entity
@Table(
        //uniqueConstraints = {@UniqueConstraint(columnNames = {"showId", "seatId"})},
        indexes = @Index(name = "combinedIdx", columnList = "showId,seatId", unique = true)
)
public class ProcessingSeat implements Serializable {

    private static final long serialVersionUID = -3630999924592433108L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int showId;

    @Column(nullable = false)
    private int seatId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date processingSince;

    @Column(nullable = false)
    private String lockedForUser;

    public ProcessingSeat() {
    }

    /**
     * This constructor will autoset the processingSince timestamp to now
     *
     * @param showId id of show
     * @param seatId id of seat
     */
    public ProcessingSeat(int showId, int seatId) {
        this.showId = showId;
        this.seatId = seatId;
        processingSince = new Date();
    }

    /**
     * This constructor will autoset the processingSince timestamp to now
     *
     * @param showId   id of show
     * @param seatId   id of seat
     * @param username name of user for locking
     */
    public ProcessingSeat(int showId, int seatId, String username) {
        this(showId, seatId);
        this.lockedForUser = username;
    }

    public ProcessingSeat(int showId, int seatId, Date processingSince) {
        this(showId, seatId);
        this.processingSince = processingSince;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public Date getProcessingSince() {
        return processingSince;
    }

    public void setProcessingSince(Date processingSince) {
        this.processingSince = processingSince;
    }

    public String getLockedForUser() {
        return lockedForUser;
    }

    public void setLockedForUser(String lockedForUser) {
        this.lockedForUser = lockedForUser;
    }

    @Override
    public String toString() {
        return "ProcessingSeat{" +
                "showId=" + showId +
                ", seatId=" + seatId +
                ", processingSince=" + processingSince +
                ", lockedForUser='" + lockedForUser + '\'' +
                '}';
    }
}