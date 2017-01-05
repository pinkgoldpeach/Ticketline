package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Data transfer object for a news
 */
@ApiModel(value = "NewsDto", description = "Data transfer object for a news")
public class NewsDto {

	@ApiModelProperty(value = "Date when the news was submitted")
    private Date submittedOn;

    @NotNull
    @Size(min = 5, max = 255)
    @ApiModelProperty(value = "Title of the news", required = true)
    private String title;

    @NotNull
    @Size(min = 5)
    @ApiModelProperty(value = "News text", required = true)
    private String newsText;

    /**
     * Gets the submitted-on date.
     *
     * @return the submitted-on date
     */
    public Date getSubmittedOn() {
        return submittedOn;
    }

    /**
     * Sets the submitted-on date.
     *
     * @param submittedOn the new submitted-on date
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

	/**
	 * {@inheritDoc}
	 */
    @Override
    public String toString() {
        return "NewsDto [submittedOn=" + submittedOn + ", title=" + title
                + ", newsText=" + newsText + "]";
    }

}
