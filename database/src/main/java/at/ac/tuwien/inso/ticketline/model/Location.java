package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The location entity.
 */
@Entity
public class Location implements Serializable {

    private static final long serialVersionUID = 9038844425442187230L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(length = 50)
    private String owner;

    private Address address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;

    /**
     * Instantiates a new location.
     */
    public Location() {
    }

    /**
     * Instantiates a new location.
     *
     * @param name the name
     * @param description the description
     * @param owner the owner
     * @param address the address
     */
    public Location(String name, String description, String owner, Address address) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
    }

    /**
     * Instantiates a new location.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param owner the owner
     * @param address the address
     */
    public Location(Integer id, String name, String description, String owner, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
    }

    /**
     * Instantiates a new location.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param owner the owner
     * @param address the address
     * @param rooms the rooms
     */
    public Location(Integer id, String name, String description, String owner, Address address, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.address = address;
        this.rooms = rooms;
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
     * Gets the owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the rooms.
     *
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the rooms.
     *
     * @param rooms the new rooms
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString(){

        //public Location(Integer id, String name, String description, String owner, Address address) {

        String idString = "no id";
        if(this.getId() > 0)
            idString = String.valueOf(this.getId());

        return "Location: id: " + idString + ", name: " + this.name + ", description: " + this.description
                + ", owner: " + this.owner + ", address: " + this.address;
    }

}
