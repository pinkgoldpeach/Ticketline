package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Carla on 08/05/2016.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Data transfer object for an Area
 */
@ApiModel(value = "AreaDto", description = "Data transfer object for an area")

public class AreaDto {

    @ApiModelProperty(value = "ID of the area", required = true)
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Type of area", required = true)
    private AreaTypeDto areaType;

    @ApiModelProperty(value = "Amount of tickets available for the area")
    private long ticketAmount;

    @ApiModelProperty(value = "Price for that area")
    private double price;

    @ApiModelProperty(value = "The room the area belongs to")
    private RoomDto room;

    public long getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(long ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AreaTypeDto getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeDto areaType) {
        this.areaType = areaType;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
