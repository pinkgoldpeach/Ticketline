package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data transfer object for Reservations
 *
 * @author Alexander Poschenreithner (1328924)
 */
@ApiModel(value = "ReservationPublishDto", description = "Data transfer object for publishing reservation. Employee and Customer objects must not be set, only their IDs")
public class ReservationPublishDto {

    @NotNull
    @ApiModelProperty(value = "Customer who made the reservation", required = true)
    private Integer customerId;

    @ApiModelProperty(value = "Tickets that are reserved")
    private List<TicketPublishDto> tickets;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<TicketPublishDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketPublishDto> tickets) {
        this.tickets = tickets;
    }
}
