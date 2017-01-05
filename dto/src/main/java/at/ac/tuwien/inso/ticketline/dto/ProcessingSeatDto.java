package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for a processingSeat
 *
 * @author Alexander Poschenreithner (1328924)
 */
@ApiModel(value = "ProcessingSeatDto", description = "Data transfer object for a news")
public class ProcessingSeatDto {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "Show ID", required = true)
    private int showId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "Seat ID", required = true)
    private int seatId;

    public ProcessingSeatDto() {
    }

    public ProcessingSeatDto(int showId, int seatId) {
        this.showId = showId;
        this.seatId = seatId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
