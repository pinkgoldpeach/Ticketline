package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * This class holds information about the user status
 */
@ApiModel(value = "CustomerDto", description = "Holds information about the customer")
public class CustomerDto extends PersonDto{

    @NotNull
    @ApiModelProperty(value = "Customer status of the customer", required = true)
    private CustomerStatusDto customerStatus;

    @ApiModelProperty(value = "All reservations of the customer")
    private List<ReservationDto> reservationDtos;


    public CustomerStatusDto getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatusDto customerStatus) {
        this.customerStatus = customerStatus;
    }


    public List<ReservationDto> getReservationDtos() {
        return reservationDtos;
    }

    public void setReservationDtos(List<ReservationDto> reservationDtos) {
        this.reservationDtos = reservationDtos;
    }
}
