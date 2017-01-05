package at.ac.tuwien.inso.ticketline.server.exception;

/**
 * Created by dev on 14/05/16.
 */
public class ValidationException extends Exception {
    private static final long serialVersionUID = 2807123734480328252L;

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
     * @param message the message
     * @param throwable the throwable
     */
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
