package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 22/05/2016.
 *
 * @author Patrick Weiszkirchner
 * @author Carla Jancik
 */

@ApiModel(value = "UsedAreasDto", description = "State of the areas")

public class UsedAreasDto {

    @NotNull
    @ApiModelProperty(value = "Show ID", required = true)
    private Integer showID;


    @NotNull
    @ApiModelProperty(value = "Amount of tickets the user wants to buy per area", required = true)
    private HashMap<Integer, Integer> amountPerArea;


    @ApiModelProperty(value = "Reservation number")
    private String reservationNumber;


    public Integer getShowID() {
        return showID;
    }

    public void setShowID(Integer showID) {
        this.showID = showID;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public HashMap<Integer, Integer> getAmountPerArea() {
        return amountPerArea;
    }

    public void setAmountPerArea(HashMap<Integer, Integer> amountPerArea) {
        this.amountPerArea = amountPerArea;
    }
}
