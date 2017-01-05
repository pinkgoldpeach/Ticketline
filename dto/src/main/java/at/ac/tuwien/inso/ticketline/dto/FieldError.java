package at.ac.tuwien.inso.ticketline.dto;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * This class holds an error for a field
 */
@ApiModel(value = "FieldError", description = "Error message for a field")
public class FieldError {

    @NotNull
    @ApiModelProperty(value = "Field name", required = true)
    private String field;

    @NotNull
    @ApiModelProperty(value = "Error message", required = true)
    private String message;

    /**
     * Instantiates a new field error.
     */
    public FieldError() {
    }

    /**
     * Instantiates a new field error.
     *
     * @param field the field
     * @param message the message
     */
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    /**
     * Gets the field.
     *
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
