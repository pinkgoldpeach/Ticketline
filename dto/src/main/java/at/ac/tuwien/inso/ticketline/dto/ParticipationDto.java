package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Carla on 08/05/2016.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object for a participation
 */
@ApiModel(value = "ParticipationDto", description = "Data transfer object for a participation")
public class ParticipationDto {


    @Size(min = 0, max = 1024)
    @ApiModelProperty(value = "Description of the participation")
    private String description;

    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Description of the participation")
    private String artistRole;

    @ApiModelProperty(value = "The performance in which the artist participates")
    private PerformanceDto performance;

    @ApiModelProperty(value = "The artist who participates")
    private ArtistDto artist;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistRole() {
        return artistRole;
    }

    public void setArtistRole(String artistRole) {
        this.artistRole = artistRole;
    }

    public PerformanceDto getPerformance() {
        return performance;
    }

    public void setPerformance(PerformanceDto performance) {
        this.performance = performance;
    }

    public ArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDto artist) {
        this.artist = artist;
    }
}
