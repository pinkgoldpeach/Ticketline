package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface ReceiptEntryService {

    /**
     * Returns a collection of all receipt entries
     *
     * @return a collection of all receipt entries
     * @throws ServiceException the service exception
     */
    List<ReceiptEntry> getAllReceiptEntries() throws ServiceException;

    /**
     * Returns a collection of all receipt entries, pagination possible
     *
     * @param pageNumber     the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all receipt entries
     * @throws ServiceException the service exception
     */
    List<ReceiptEntry> getPageOfReceiptEntries(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * Returns the number of records
     *
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     *
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;
}
