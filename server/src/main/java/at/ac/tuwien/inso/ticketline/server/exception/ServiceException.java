package at.ac.tuwien.inso.ticketline.server.exception;

/**
 * Exception for REST services
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 8375241096351843000L;

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

}
