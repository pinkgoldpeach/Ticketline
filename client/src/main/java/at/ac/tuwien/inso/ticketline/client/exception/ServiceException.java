package at.ac.tuwien.inso.ticketline.client.exception;

/**
 * The ServiceException indicates an error when calling a REST service
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 3400531267454846055L;

    /**
     * Instantiates a new service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param throwable the throwable
     */
    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
