package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Carla on 08/05/2016.
 */
@ApiModel(value = "SeatDto", description = "Data transfer object for a seat")
public class SeatDto {

    @ApiModelProperty(value = "ID of the seat", required = true)
    private Integer id;

    @ApiModelProperty(value = "Name of the seat")
    private String name;

    @ApiModelProperty(value = "Description of the seat")
    private String description;

    @NotNull
    @ApiModelProperty(value = "Number of the seat", required = true)
    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
