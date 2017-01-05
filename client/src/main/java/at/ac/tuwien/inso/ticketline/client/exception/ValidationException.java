package at.ac.tuwien.inso.ticketline.client.exception;

import at.ac.tuwien.inso.ticketline.dto.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * The ValidationException indicates validation errors when calling a REST service.
 * 
 * Use the {@link #getFieldErrors()} method to retrieve the errors.
 */
public class ValidationException extends ServiceException {

    private static final long serialVersionUID = 280721989480328252L;

    private List<FieldError> fieldErrors = new ArrayList<>();

    /**
     * Instantiates a new validation exception.
     */
    public ValidationException() {
        super();
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param message the message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param throwable the throwable
     */
    public ValidationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param fieldErrors the field errors
     */
    public ValidationException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param message the message
     * @param fieldErrors the field errors
     */
    public ValidationException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    /**
     * Gets the field errors.
     *
     * @return the field errors
     */
    public List<FieldError> getFieldErrors() {
        return this.fieldErrors;
    }

}
