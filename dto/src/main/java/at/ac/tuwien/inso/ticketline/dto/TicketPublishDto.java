package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Data transfer object for a show
 * @author Alexander Poschenreithner (1328924)
 */
@ApiModel(value = "TicketPublishDto", description = "Data transfer object for a ticket to be saved. No sub objects needed, just IDs to simplify data transfer.")
public class TicketPublishDto {


    @ApiModelProperty(value = "Description of the ticket")
    private String description;

    @NotNull
    @ApiModelProperty(value = "Price of the ticket", required = true)
    private Double price;

    @NotNull
    @ApiModelProperty(value = "The showId the ticket belongs to", required = true)
    private Integer showId;

    @ApiModelProperty(value = "The seatId belonging to the ticket")
    private Integer seatId;

    @ApiModelProperty(value = "The areaID belonging to the ticket")
    private Integer areaId;

    @ApiModelProperty(value = "The reservationId belonging to the ticket")
    private Integer reservationId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }


}
