package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.RowDao;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.RowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleRowService extends AbstractPaginationService implements RowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRowService.class);

    @Autowired
    private RowDao rowDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Row> getPageOfRows(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfRows() called");
        try {
            Page<Row> rows = rowDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return rows.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Row> getRowsByRoom(Room room) throws ServiceException {
        LOGGER.info("getRowsByRoom() called");
        try {
            return rowDao.findByRoom(room);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Row> getRowsByShow(Show show) throws ServiceException {
        LOGGER.info("getRowsByShow() called");
        try {
            return rowDao.findByShow(show);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
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
            return rowDao.count();
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
            return calculatePages(rowDao.count());
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
    public void setRowDao(RowDao dao) {
        this.rowDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
