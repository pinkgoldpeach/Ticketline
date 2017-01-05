package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The performance entity.
 */
@Entity
public class Performance implements Serializable {

    private static final long serialVersionUID = -3795343062479959019L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    private Integer duration;

    @Column(length = 1024)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private PerformanceType performanceType;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Show> shows;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL)
    private List<Participation> participations;

    /**
     * Instantiates a new performance.
     */
    public Performance() {
    }

    /**
     * Instantiates a new performance.
     *
     * @param name the name
     * @param description the description
     * @param duration the duration
     * @param type the type
     */
    public Performance(String name, String description, Integer duration, PerformanceType type) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.performanceType = type;
    }

    /**
     * Instantiates a new performance.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param duration the duration
     * @param type the type
     */
    public Performance(Integer id, String name, String description, Integer duration, PerformanceType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.performanceType = type;
    }

    /**
     * Instantiates a new performance.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param duration the duration
     * @param type the type
     * @param shows the shows
     * @param participations the participations
     */
    public Performance(Integer id, String name, String description, Integer duration, PerformanceType type,
                       List<Show> shows, List<Participation> participations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.performanceType = type;
        this.shows = shows;
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
     * Gets the duration.
     *
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the duration.
     *
     * @param duration the new duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
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
     * Gets the performance type.
     *
     * @return the performance type
     */
    public PerformanceType getPerformanceType() {
        return performanceType;
    }

    /**
     * Sets the performance type.
     *
     * @param performanceType the new performance type
     */
    public void setPerformanceType(PerformanceType performanceType) {
        this.performanceType = performanceType;
    }


    /**
     * Gets the shows.
     *
     * @return the shows
     */
    public List<Show> getShows() {
        return shows;
    }

    /**
     * Sets the shows.
     *
     * @param shows the new shows
     */
    public void setShows(List<Show> shows) {
        this.shows = shows;
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
