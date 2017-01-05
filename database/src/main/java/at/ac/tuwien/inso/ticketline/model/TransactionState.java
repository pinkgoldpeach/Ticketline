package at.ac.tuwien.inso.ticketline.model;

/**
 * Enumeration of transaction states.
 */
public enum TransactionState {
    ORDERED,
    CANCELED,
    IN_PROCESS,
    PAID,
    CANCELLED_POSITIONS
}
