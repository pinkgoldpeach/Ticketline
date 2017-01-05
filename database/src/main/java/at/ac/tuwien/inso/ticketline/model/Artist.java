package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The artist entity.
 */
@Entity
public class Artist implements Serializable {

    private static final long serialVersionUID = -670177989763664076L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(length = 1024)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private List<Participation> participations;

    /**
     * Instantiates a new artist.
     */
    public Artist() {
    }

    /**
     * Instantiates a new artist.
     *
     * @param firstname the firstname
     * @param lastname the lastname
     * @param description the description
     */
    public Artist(String firstname, String lastname, String description) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
    }

    /**
     * Instantiates a new artist.
     *
     * @param id the id
     * @param firstname the firstname
     * @param lastname the lastname
     * @param description the description
     */
    public Artist(Integer id, String firstname, String lastname, String description) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
    }

    /**
     * Instantiates a new artist.
     *
     * @param id the id
     * @param firstname the firstname
     * @param lastname the lastname
     * @param description the description
     * @param participations the participations
     */
    public Artist(Integer id, String firstname, String lastname, String description, List<Participation> participations) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.participations = participations;
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
     * Gets the firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the new firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the new lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
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
     * Gets the participations.
     *
     * @return the participations
     */
    public List<Participation> getParticipations() {
        return participations;
    }

    /**
     * Sets the participations.
     *
     * @param participations the new participations
     */
    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}
