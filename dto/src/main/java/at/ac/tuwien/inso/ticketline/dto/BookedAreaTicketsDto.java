package at.ac.tuwien.inso.ticketline.dto;

/**
 * Created by Patrick on 06/06/16.
 */

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data transfer object for Area Tickets
 */
@ApiModel(value = "BookedAreaTicketsDto", description = "Data transfer object for Area Tickets")
public class BookedAreaTicketsDto {
    @NotNull
    @ApiModelProperty(value = "Tickets", required = true)
    private List<TicketDto> ticketDtos;

    public BookedAreaTicketsDto(List<TicketDto> ticketDtos){
        this.ticketDtos = ticketDtos;
    }

    public List<TicketDto> getTicketDtos() {
        return ticketDtos;
    }

    public void setTicketDtos(List<TicketDto> ticketDtos) {
        this.ticketDtos = ticketDtos;
    }

    public BookedAreaTicketsDto(){}
}
