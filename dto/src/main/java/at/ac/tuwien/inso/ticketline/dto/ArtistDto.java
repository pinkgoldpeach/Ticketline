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
 * Data transfer object for a participation
 */
@ApiModel(value = "ArtistDto", description = "Data transfer object for an artist")

public class ArtistDto {



    @ApiModelProperty(value = "id of artist", required = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "First name of the artist", required = true)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "Last name of the artist", required = true)
    private String lastname;

    @Size(min = 0, max = 1024)
    @ApiModelProperty(value = "Description of the artist")
    private String description;

    @ApiModelProperty(value = "Participations of the artist")
    private List<ParticipationDto> participations;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ParticipationDto> getParticipations() {
        return participations;
    }

    public void setParticipations(List<ParticipationDto> participations) {
        this.participations = participations;
    }
}
