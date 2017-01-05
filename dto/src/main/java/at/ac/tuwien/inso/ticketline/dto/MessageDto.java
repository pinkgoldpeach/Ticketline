package at.ac.tuwien.inso.ticketline.dto;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * A message for a REST service
 */
@ApiModel(value = "MessageDto", description = "Response message of a REST service")
public class MessageDto {

	@ApiModelProperty(value = "Type of the message", required = true)
    private MessageType type;

	@ApiModelProperty(value = "Message text")
    private String text;

	@ApiModelProperty(value = "List of field errors")
    private List<FieldError> fieldErrors = new ArrayList<>();

    /**
     * Gets the type.
     *
     * @return the type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Checks if the message has field errors
     *
     * @return true if it has errors, false otherwise
     */
    public boolean hasFieldErrors() {
        return ((this.fieldErrors != null) && (this.fieldErrors.size() > 0));
    }

    /**
     * Adds a field error.
     *
     * @param field the field
     * @param message the message
     */
    public void addFieldError(String field, String message) {
        FieldError error = new FieldError(field, message);
        fieldErrors.add(error);
    }

    /**
     * Gets the field errors.
     *
     * @return the field errors
     */
    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}
