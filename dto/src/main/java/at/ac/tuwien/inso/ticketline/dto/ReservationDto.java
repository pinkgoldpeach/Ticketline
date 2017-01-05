package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@ApiModel(value = "ReservationDto", description = "Data transfer object for a reservation")
public class ReservationDto {

    @ApiModelProperty(value = "ID of the reservation")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Reservation number of the reservation", required = true)
    private String reservationNumber;

    @NotNull
    @ApiModelProperty(value = "Customer who made the reservation", required = true)
    private CustomerDto customer;

    @ApiModelProperty(value = "Employee who made the reservation")
    private EmployeeDto employee;

    @ApiModelProperty(value = "Tickets that are reserved")
    private List<TicketDto> tickets;

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
