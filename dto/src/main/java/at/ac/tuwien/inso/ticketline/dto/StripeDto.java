package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is used for payment with stripe to transmit the token
 * @author Patrick Weiszkirchner
 */
@ApiModel(value = "StripeDto", description = "Transmit the token to the server which is used for payment")
public class StripeDto {

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "token of the current payment", required = true)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
