package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Carla on 08/05/2016.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Data transfer object for a show
 */
@ApiModel(value = "ShowDto", description = "Data transfer object for a show")

public class ShowDto {

    @ApiModelProperty(value = "id of show", required = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(value = "True if the show was canceled")
    private Boolean canceled;

    @NotNull
    @ApiModelProperty(value = "Date of the show", required = true)
    private Date dateOfPerformance;

    /**
    @ApiModelProperty(value = "Sold tickets of the show")
    private List<TicketDto> tickets;
     */

    @NotNull
    @ApiModelProperty(value = "Room where the show takes place")
    private RoomDto room;

    @NotNull
    @ApiModelProperty(value = "Performance belonging to the show")
    private PerformanceDto performance;

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Date getDateOfPerformance() {
        return dateOfPerformance;
    }

    public void setDateOfPerformance(Date dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public PerformanceDto getPerformance() {
        return performance;
    }

    public void setPerformance(PerformanceDto performance) {
        this.performance = performance;
    }
}
