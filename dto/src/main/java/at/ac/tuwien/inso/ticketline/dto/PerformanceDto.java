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
 * Data transfer object for performances
 */
@ApiModel(value = "PerformanceDto", description = "Data transfer object for performances")
public class PerformanceDto {


    @NotNull
    @ApiModelProperty(value = "The id of the performance", required = true)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(value = "Name of the performance", required = true)
    private String name;

    @ApiModelProperty(value = "Duration of the performance")
    private Integer duration;

    @Size(min = 0, max = 1024)
    @ApiModelProperty(value = "Description of the performance")
    private String description;

    @NotNull
    @ApiModelProperty(value = "Description of the performance type", required = true)
    private PerformanceTypeDto performanceType;


    @ApiModelProperty(value = "Shows with showdates of the performance")
    private List<ShowDto> shows;

    @ApiModelProperty(value = "Relation between performance and artist")
    private List<ParticipationDto> participations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ShowDto> getShows() {
        return shows;
    }

    public void setShows(List<ShowDto> shows) {
        this.shows = shows;
    }

    public List<ParticipationDto> getParticipations() {
        return participations;
    }

    public void setParticipations(List<ParticipationDto> participations) {
        this.participations = participations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PerformanceTypeDto getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(PerformanceTypeDto performanceType) {
        this.performanceType = performanceType;
    }
}
