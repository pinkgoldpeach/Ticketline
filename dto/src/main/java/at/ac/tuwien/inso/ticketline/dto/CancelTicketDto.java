package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Patrick on 21/05/16.
 *
 * @author Patrick Weiszkirchner
 */
@ApiModel(value = "CancelTicketDto", description = "Ticket ID transfer and cancellation reason for this IDs if these were alreasy sold")

public class CancelTicketDto {
    @NotNull
    @ApiModelProperty(value = "IDs of the tickets", required = true)
    private List<Integer> ids;

    @ApiModelProperty(value = "Reservation number") //if tickets of a reservation should be canceled then this attribute should be set
    private String reservationNumber;

    @ApiModelProperty(value = "Receipt id") // if sold tickets should be canceled then this attribute should be set
    private Integer receiptID;


    @ApiModelProperty(value = "Cancellation reason for the sold tickets") // only required for canceling sold tickets
    private String cancellationReason;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Integer getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(Integer receiptID) {
        this.receiptID = receiptID;
    }
}
