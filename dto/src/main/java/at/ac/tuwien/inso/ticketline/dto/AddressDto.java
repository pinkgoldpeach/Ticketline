package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is used for an address
 */
@ApiModel(value = "AddressDto", description = "Address Dto for a location or person")
public class AddressDto {

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Street of the Address", required = true)
    private String street;

    @NotNull
    @Size(max = 25)
    @ApiModelProperty(value = "Postalcode of the Address", required = true)
    private String postalCode;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "City of the Address", required = true)
    private String city;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Country of the Address", required = true)
    private String country;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
