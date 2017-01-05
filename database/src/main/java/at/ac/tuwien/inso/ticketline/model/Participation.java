package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The participation entity.
 */
@Entity
public class Participation implements Serializable {

    private static final long serialVersionUID = 3071965473717124011L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1024)
    private String description;

    @Column(length = 50)
    private String artistRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    /**
     * Instantiates a new participation.
     */
    public Participation() {
    }

    /**
     * Instantiates a new participation.
     *
     * @param artistRole the artist role
     * @param description the description
     * @param performance the performance
     * @param artist the artist
     */
    public Participation(String artistRole, String description, Performance performance, Artist artist) {
        this.artistRole = artistRole;
        this.description = description;
        this.performance = performance;
        this.artist = artist;

    }

    /**
     * Instantiates a new participation.
     *
     * @param id the id
     * @param artistRole the artist role
     * @param description the description
     * @param performance the performance
     * @param artist the artist
     */
    public Participation(Integer id, String artistRole, String description, Performance performance, Artist artist) {
        this.id = id;
        this.artistRole = artistRole;
        this.description = description;
        this.performance = performance;
        this.artist = artist;
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
     * Gets the artist role.
     *
     * @return the artist role
     */
    public String getArtistRole() {
        return artistRole;
    }

    /**
     * Sets the artist role.
     *
     * @param artistRole the new artist role
     */
    public void setArtistRole(String artistRole) {
        this.artistRole = artistRole;
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

    /**
     * Gets the artist.
     *
     * @return the artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the artist.
     *
     * @param artist the new artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
