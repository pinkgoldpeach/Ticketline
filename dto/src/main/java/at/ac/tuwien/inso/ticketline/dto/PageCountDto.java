package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for a PageCount
 *
 * @author Alexander Poschenreithner (1328924)
 */
@ApiModel(value = "PageCountDto", description = "Data transfer object for the page count")
public class PageCountDto {

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "Count of possible pages for pagination", required = true)
    private Integer pageCount;

    /**
     * Returns the count of possible pages
     *
     * @return page count
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * Set the count of possible pages
     *
     * @param pageCount page count
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NewsDto [pageCount=" + pageCount + "]";
    }

}
