package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ReceiptEntryDao;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleReceiptEntryService extends AbstractPaginationService implements ReceiptEntryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReceiptEntryService.class);

    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReceiptEntry> getAllReceiptEntries() throws ServiceException {
        LOGGER.info("getAllReceiptEntries() called");
        try {
            return receiptEntryDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReceiptEntry> getPageOfReceiptEntries(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfReceiptEntries() called");
        try {
            Page<ReceiptEntry> receiptEntries = receiptEntryDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return receiptEntries.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return receiptEntryDao.count();
        } catch (Exception e) {
            LOGGER.error("Requesting page count - " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getPageCount() throws ServiceException {
        LOGGER.info("getPageCount() was called");
        try {
            return calculatePages(receiptEntryDao.count());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setReceiptEntryDao(ReceiptEntryDao dao) {
        this.receiptEntryDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
