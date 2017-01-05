package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Carla on 08/05/2016.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Data transfer object for a show
 */
@ApiModel(value = "TicketDto", description = "Data transfer object for a ticket")
public class TicketDto {

    @ApiModelProperty(value = "ID of the ticket", required = true)
    private Integer id;


    @ApiModelProperty(value = "Description of the ticket")
    private String description;

    @NotNull
    @ApiModelProperty(value = "Price of the ticket", required = true)
    private Double price;

    @NotNull
    @ApiModelProperty(value = "The show the ticket belongs to", required = true)
    private ShowDto show;


    @ApiModelProperty(value = "The seat belonging to the ticket")
    private SeatDto seat;

    @ApiModelProperty(value = "The area belonging to the ticket")
    private AreaDto area;

    @ApiModelProperty(value = "The reservation belonging to the ticket")
    private ReservationDto reservation;

    @ApiModelProperty(value = "The unique identifier of the ticket")
    private UUID uuid;

    @ApiModelProperty(value = "If the ticket is valid")
    private Boolean valid;

    @ApiModelProperty(value = "The cancelation reason of the ticket")
    private String cancellationReason;

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

    public ShowDto getShow() {
        return show;
    }

    public void setShow(ShowDto show) {
        this.show = show;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }

    public AreaDto getArea() {
        return area;
    }

    public void setArea(AreaDto area) {
        this.area = area;
    }

    public ReservationDto getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDto reservation) {
        this.reservation = reservation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
