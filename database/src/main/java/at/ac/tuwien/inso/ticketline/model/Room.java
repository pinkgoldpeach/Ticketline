package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The room entity.
 */
@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = -668105317259567139L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean seatChoice; // true if show has seat tickets, false if show has only areas

    /* bidirectional issues
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Row> rows; // room has different rows
     */
/* bidirectional issues
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    //@JoinColumn(name = "area_id")
    private List<Area> areas; // different areas (eg. barrier free, VIP, stance, free seating choice)
*/

    @Column(length = 50)
    private String name;

    @Column(length = 1024)
    private String description;

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Show> shows;
    */

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;


    /**
     * Instantiates a new room.
     */
    public Room() {
    }

    /**
     *      * Instantiates a new room.
     *
     * @param name the name
     * @param description the description
     * @param seatChoice whether you have a seatchoice or areas
     * @param location the location
     */
    public Room(String name, String description, boolean seatChoice,Location location) {
        this.name = name;
        this.description = description;
        this.seatChoice = seatChoice;
        this.location = location;
    }

    /**
     * instantiates a new room
     * @param id the id of the room, is generated in the db
     * @param name the name of the room
     * @param description the description of the room
     * @param seatChoice whether you have a seatchoice or areas
     * @param location the location the room is in
     */
    public Room(Integer id, String name, String description, boolean seatChoice,Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seatChoice = seatChoice;
        this.location = location;
    }


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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the shows.
     *
     * @return the shows
     */
    /*
    public List<Show> getShows() {
        return shows;
    }

    /**
     * Sets the shows.
     *
     * @param shows the new shows
     */
    /*
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /*
     * Gets the rows
     *
     * @return
     */
    /*
    public List<Row> getRows() {
        return rows;
    }

    /**
     * Sets the new rows
     *
     * @param rows
     */
    /*
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    /**
     * Get the Areas
     *
     * @return
     */
    /*
    public List<Area> getAreas() {
        return areas;
    }

    /**
     * Set the Areas
     *
     * @param areas
     */
    /*
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
*/
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getSeatChoice() {
        return seatChoice;
    }

    public void setSeatChoice(Boolean seatChoice) {
        this.seatChoice = seatChoice;
    }
}
