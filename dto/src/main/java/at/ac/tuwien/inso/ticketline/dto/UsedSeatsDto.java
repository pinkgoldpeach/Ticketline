package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Patrick on 20/05/16.
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner
 */
@ApiModel(value = "UsedSeatsDto", description = "State of the seat")
public class UsedSeatsDto {
    @NotNull
    @ApiModelProperty(value = "IDs of the seats", required = true)
    private List<Integer> ids;

    @NotNull
    @ApiModelProperty(value = "Show ID", required = true)
    private Integer showId;

    public UsedSeatsDto() {
    }

    public UsedSeatsDto(List<Integer> ids) {
        this.ids = ids;
    }

    public UsedSeatsDto(List<Integer> ids, Integer showId) {
        this(ids);
        this.showId = showId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public void addId(int id) {
        this.ids.add(id);
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }
}
