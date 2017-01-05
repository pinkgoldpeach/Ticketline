package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Carla on 23/05/2016.
 */
@ApiModel(value = "ShowTopTenDto", description = "Data transfer object for a show of the top ten shows")
public class ShowTopTenDto {

    @NotNull
    @ApiModelProperty(value = "the showDto", required = true)
    ShowDto showDto;

    @NotNull
    @ApiModelProperty(value = "number of times a ticket was sold for the show", required = true)
    Long timesSold;

    public ShowDto getShowDto() {
        return showDto;
    }

    public void setShowDto(ShowDto showDto) {
        this.showDto = showDto;
    }

    public Long getTimesSold() {
        return timesSold;
    }

    public void setTimesSold(Long timesSold) {
        this.timesSold = timesSold;
    }
}
