package at.ac.tuwien.inso.ticketline.dto;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * This class holds information about the user status
 */
@ApiModel(value = "UserStatusDto", description = "Holds information about the user")
public class UserStatusDto {

	@ApiModelProperty(value = "Type of event")
    private UserEventDto event;

	@ApiModelProperty(value = "Username")
    private String username;

	@ApiModelProperty(value = "First name of the user")
    private String firstName;

	@ApiModelProperty(value = "Last name of the user")
    private String lastName;

	@ApiModelProperty(value = "Is a user logged in or anonymous")
    private boolean anonymous = true;

	@ApiModelProperty(value = "Roles of the user")
    private List<String> roles = new ArrayList<>();

    /**
     * Gets the event.
     *
     * @return the event
     */
    public UserEventDto getEvent() {
        return event;
    }

    /**
     * Sets the event.
     *
     * @param event the new event
     */
    public void setEvent(UserEventDto event) {
        this.event = event;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Checks if the user is anonymous.
     *
     * @return true, if the user is anonymous
     */
    public boolean isAnonymous() {
        return anonymous;
    }

    /**
     * Sets that the user is anonymous.
     *
     * @param anonymous user is anonymous
     */
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles the new roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
