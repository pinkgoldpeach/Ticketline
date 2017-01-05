package at.ac.tuwien.inso.ticketline.server.rest;


import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

/**
 * Abstract for Controllers for generalization
 *
 * @author Alexander Poschenreithner (1328924)
 */
abstract class AbstractPaginationController {

    /**
     * Number of records per Page
     */
    final public static Integer ENTRIES_PER_PAGE = 20;

    /**
     * REST Method to get page count
     *
     * @return number of pages
     * @throws ServiceException on error
     */
    abstract public PageCountDto getPageCount() throws ServiceException;
}
