package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 * <p>
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.PerformanceService} interface
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimplePerformanceService extends AbstractPaginationService implements PerformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);

    @Autowired
    private PerformanceDao performanceDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getAllPerformances() throws ServiceException {
        try {
            return performanceDao.findAllOrderByNameAsc();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPageOfPerformances(int pageNumber, int entriesPerPage) throws ServiceException {

        try {
            Page<Performance> performances = performanceDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return performances.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPerformanceByName(Performance performance) throws ServiceException {
        LOGGER.info("getPerformanceByName() called");
        try {
            if (performance.getName() == null && performance.getName().trim().isEmpty()) {
                throw new ServiceException();
            }
            return performanceDao.findByNameLike(performance.getName());

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPerformanceByDuration(Performance performance) throws ServiceException {
        LOGGER.info("getPerformanceByDuration() called");
        try {
            int duration = performance.getDuration();
            int min = Math.max((duration - 30), 0);
            return performanceDao.findByDurationBetween(min, (duration + 30));

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPerformanceByDescription(Performance performance) throws ServiceException {
        LOGGER.info("getPerformanceByDescription() called");
        try {
            return performanceDao.findByDescription(performance.getDescription());

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Performance> getPerformanceByPerformanceType(Performance performance) throws ServiceException {
        LOGGER.info("getPerformanceByPerformanceType() called");
        try {
            return performanceDao.findByPerformanceType(performance.getPerformanceType());

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPerformanceByArtistName(Artist artist) throws ServiceException {
        LOGGER.info("getPerformanceByArtistName() called");
        try {
            return performanceDao.findPerformanceByArtistName(artist);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getPerformanceByArtist(Artist artist) throws ServiceException {
        LOGGER.info("getPerformanceByArtist() called");
        try {
            return performanceDao.findPerformanceByArtist(artist);

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
            return performanceDao.count();
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
            return calculatePages(performanceDao.count());
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
    public void setPerformanceDao(PerformanceDao dao) {
        this.performanceDao = dao;
    }

    // -------------------- For Testing purposes --------------------

}
