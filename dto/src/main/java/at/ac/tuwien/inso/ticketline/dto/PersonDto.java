package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * This abstract class holds information about a person which can be a user or customer
 */
@ApiModel(value = "PersonDto", description = "Holds information about a person")
public abstract class PersonDto {

    @ApiModelProperty(value = "ID of the person")
    private Integer id;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "First name of the person", required = true)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Last name of the person", required = true)
    private String lastName;

    @ApiModelProperty(value = "Adress of the person")
    private AddressDto address;

    @NotNull
    @ApiModelProperty(value = "Gender of the person", required = true)
    private GenderDto gender;

    @NotNull
    @ApiModelProperty(value = "Date of birth of the person", required = true)
    private Date dateOfBirth;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "eMail of the person", required = true)
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public GenderDto getGender() {
        return gender;
    }

    public void setGender(GenderDto gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
