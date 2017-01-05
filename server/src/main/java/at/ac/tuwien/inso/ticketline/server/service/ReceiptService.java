package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
public interface ReceiptService {

    /**
     * Returns a collection of all receipts
     *
     * @return a collection of all receipts
     * @throws ServiceException the service exception
     */
    List<Receipt> getAllReceipts() throws ServiceException;

    /**
     * Returns a collection of all receipts
     *
     * @param pageNumber the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all receipts.
     * @throws ServiceException the service exception
     */
    List<Receipt> getPageOfReceipts(int pageNumber, int entriesPerPage) throws ServiceException;

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

    /**
     * Persists a receipt
     *
     * @param receipt to be persisted
     * @return persisted object
     * @throws ServiceException on error
     */
    Receipt save(Receipt receipt) throws ServiceException;

    /**
     * Returns a list of receipts which belongs to a customer with a specific lastname
     *
     * @param lastname of the customer
     * @return list of found receipts
     * @throws ServiceException on error
     */
    List<Receipt> findReceiptByLastName(String lastname) throws ServiceException;
}
