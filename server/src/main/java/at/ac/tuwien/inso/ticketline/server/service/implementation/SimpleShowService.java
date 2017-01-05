package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 14/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleShowService extends AbstractPaginationService implements ShowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);

    @Autowired
    private ShowDao showDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getAllShows() throws ServiceException {
        try {
            return showDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getPageOfShows(Integer pageNumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Show> shows = showDao.findAllPageable(new PageRequest(pageNumber, entriesPerPage));
            return shows.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getPageOfNotCanceledShows(Integer pageNumber) throws ServiceException {
        try {
            Page<Show> shows = showDao.findAllNotCanceledPageable(new PageRequest(pageNumber, 20));
            return shows.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByDateAndTime(Show show) throws ServiceException {
        LOGGER.info("getShowsByDateAndTime() called");
        try {
            Date date = show.getDateOfPerformance();
            return showDao.findByDateAndTime(date);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByDateFrom(Show show) throws ServiceException {
        LOGGER.info("getShowsByDateFrom() called");
        try {
            Date date = show.getDateOfPerformance();
            return showDao.findByDateFrom(date);
        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByDateRange(Date min, Date max) throws ServiceException {
        LOGGER.info("getShowsByDateRange() called");
        try {
            return showDao.findByDateRange(min, max);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByRowPrice(Row row) throws ServiceException {
        LOGGER.info("getShowsByRowPrice() called");
        try {

            return showDao.findByRowPrice(row);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByAreaPrice(Area area) throws ServiceException {
        LOGGER.info("getShowsByAreaPrice() called");
        try {

            return showDao.findByAreaPrice(area);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByLocation(Location location) throws ServiceException {
        LOGGER.info("getShowsByLocation() called");
        try {

            return showDao.findByLocation(location);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByPerformance(Performance performance) throws ServiceException {
        LOGGER.info("getShowsByPerformance() called");
        try {

            return showDao.findByPerformance(performance);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getShowsByRoom(Room room) throws ServiceException {
        LOGGER.info("getShowsByRoom() called");
        try {

            return showDao.findByRoom(room);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Show, Long> getTopTenShows() throws ServiceException {
        LOGGER.info("getTopTenShows() called");
        try {

            return showDao.findTopTen();

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Show, Long> getTopTenShowsByPerformanceType(PerformanceType performanceType) throws ServiceException {
        LOGGER.info("getTopTenShowsByPerformanceType() called");
        try {
            return showDao.findTopTenByPerformanceType(performanceType);
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
            return showDao.count();
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
            return calculatePages(showDao.count());
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
    public void setShowDao(ShowDao dao) {
        this.showDao = dao;
    }

    // -------------------- For Testing purposes --------------------

}
