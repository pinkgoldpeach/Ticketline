package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Carla on 08/05/2016.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Data transfer object for a Row
 */
@ApiModel(value = "RowDto", description = "Data transfer object for a row")

public class RowDto {

    @ApiModelProperty(value = "ID of the row", required = true)
    private Integer id;


    @ApiModelProperty(value = "Name of the row")
    private String name;

    @ApiModelProperty(value = "Price of the row", required = true)
    private double price;

    @ApiModelProperty(value = "Description of the row")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
