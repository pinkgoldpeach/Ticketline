package at.ac.tuwien.inso.ticketline.server.service;


/**
 * Abstract for Service for generalization
 *
 * @author Alexander Poschenreithner (1328924)
 */
public abstract class AbstractPaginationService {

    /**
     * Number of records per Page
     */
    final public static Integer ENTRIES_PER_PAGE = 20;


    /**
     * Calculates Pages according to number of records
     *
     * @param records count
     * @return number of pages
     */
    final protected Integer calculatePages(long records) {
        return (int) Math.ceil(((double) records) / ENTRIES_PER_PAGE);
    }

}
