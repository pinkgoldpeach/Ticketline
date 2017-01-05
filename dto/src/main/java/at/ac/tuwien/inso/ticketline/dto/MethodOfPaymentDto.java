package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * This class holds information about the payment methods of a customer
 */
@ApiModel(value = "MethodOfPaymentDto", description = "Holds information about a payment method")
public class MethodOfPaymentDto {

    @ApiModelProperty(value = "ID of the payment method")
    private int id;

    @ApiModelProperty(value = "stripe dto if it's paid with stripe")
    private StripeDto stripeDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StripeDto getStripeDto() {
        return stripeDto;
    }

    public void setStripeDto(StripeDto stripeDto) {
        this.stripeDto = stripeDto;
    }
}
