package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * This class holds information about the user status
 */
@ApiModel(value = "EmployeeDto", description = "Holds information about the employee")
public class EmployeeDto extends PersonDto{

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Username of the employee", required = true)
    private String username;

    @Size(max = 1024)
    @ApiModelProperty(value = "PasswordHash of employee")
    private String passwordHash;

    @NotNull
    @ApiModelProperty(value = "If a user is blocked", required = true)
    private boolean blocked;

    @NotNull
    @ApiModelProperty(value = "Type of employee", required = true)
    private EmployeeRoles role;

    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Insurance number of employee", required = true)
    private String insuranceNumber;

    @NotNull
    @ApiModelProperty(value = "Date of starting being employed", required = true)
    private Date employedSince;


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public EmployeeRoles getRole() {
        return role;
    }

    public void setRole(EmployeeRoles role) {
        this.role = role;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Date getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(Date employedSince) {
        this.employedSince = employedSince;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
