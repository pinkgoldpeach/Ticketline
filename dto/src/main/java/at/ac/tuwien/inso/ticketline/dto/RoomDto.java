package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 */
@ApiModel(value = "RoomDto", description = "Data transfer object for a room")
public class RoomDto {

    @ApiModelProperty(value = "id of room", required = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(value = "seatChoice is true, if you have to choose seats, else choose an area")
    private Boolean seatChoice; // true if show has seat tickets, false if show has only areas

    /** bidirectional issue
    @ApiModelProperty(value = "Rows of the room")
    private List<RowDto> rows; // room has different rows
    @ApiModelProperty(value = "Areas of the room")
    private List<AreaDto> areas; // different areas (eg. barrier free, VIP, stance, free seating choice)
*/
    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Name of the room")
    private String name;

    @Size(min = 0, max = 1024)
    @ApiModelProperty(value = "Description of the room")
    private String description;

    /*
    @ApiModelProperty(value = "The shows that are shown in the room")
    private List<ShowDto> shows;
*/
    @NotNull
    @ApiModelProperty(value = "Location of the room", required = true)
    private LocationDto location;

    public Boolean getSeatChoice() {
        return seatChoice;
    }

    public void setSeatChoice(Boolean seatChoice) {
        this.seatChoice = seatChoice;
    }

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
/*
    public List<ShowDto> getShows() {
        return shows;
    }

    public void setShows(List<ShowDto> shows) {
        this.shows = shows;
    }
*/
    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }
}
