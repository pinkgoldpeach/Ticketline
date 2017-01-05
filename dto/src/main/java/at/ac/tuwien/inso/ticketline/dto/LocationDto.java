package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 */

/**
 * This class holds information about the user status
 */
@ApiModel(value = "LocationDto", description = "Holds information about the location")
public class LocationDto {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(value = "id of location", required = true)
    private int id;

    @NotNull
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "Name of the location", required = true)
    private String name;

    @Size(min = 0, max = 1024)
    @ApiModelProperty(value = "Description of the location")
    private String description;

    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Owner of the location")
    private String owner;

    @ApiModelProperty(value = "Address of the location")
    private AddressDto address;

    @ApiModelProperty(value = "Rooms in the location")
    private List<RoomDto> rooms;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
