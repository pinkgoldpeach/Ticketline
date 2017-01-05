package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * The news entity.
 */
@Entity
public class News implements Serializable {

    private static final long serialVersionUID = 4871431571987100831L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedOn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 1024)
    private String newsText;

    /**
     * Instantiates a new news.
     */
    public News() {
    }

    /**
     * Instantiates a new news.
     *
     * @param id the id
     * @param submittedOn the submitted on
     * @param title the title
     * @param newsText the news text
     */
    public News(Integer id, Date submittedOn, String title, String newsText) {
        this.id = id;
        this.submittedOn = submittedOn;
        this.title = title;
        this.newsText = newsText;
    }

    /**
     * Instantiates a new news.
     *
     * @param submittedOn the submitted on
     * @param title the title
     * @param newsText the news text
     */
    public News(Date submittedOn, String title, String newsText) {
        this.submittedOn = submittedOn;
        this.title = title;
        this.newsText = newsText;
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
     * Gets the submitted on.
     *
     * @return the submitted on
     */
    public Date getSubmittedOn() {
        return submittedOn;
    }

    /**
     * Sets the submitted on.
     *
     * @param submittedOn the new submitted on
     */
    public void setSubmittedOn(Date submittedOn) {
        this.submittedOn = submittedOn;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the news text.
     *
     * @return the news text
     */
    public String getNewsText() {
        return newsText;
    }

    /**
     * Sets the news text.
     *
     * @param newsText the new news text
     */
    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

}
