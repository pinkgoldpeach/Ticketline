package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
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
public class SimpleLocationService extends AbstractPaginationService implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLocationService.class);


    @Autowired
    LocationDao locationDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getPageOfLocations(Integer pageNumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Location> locations = locationDao.findAllPageable(new PageRequest(pageNumber, entriesPerPage));
            return locations.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByName(Location location) throws ServiceException {
        LOGGER.info("getLocationsByName() called");
        try {
            return locationDao.findByName(location.getName());
        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByShow(Show show) throws ServiceException {
        LOGGER.info("getLocationsByShow() called");

        try {

            return locationDao.findByShow(show);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByStreet(Address address) throws ServiceException {
        LOGGER.info("getLocationByStreet() called");

        try {
            return locationDao.findByStreet(address);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByCountry(Address address) throws ServiceException {
        LOGGER.info("getLocationsByCountry() called");

        try {
            return locationDao.findByCountry(address);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByPostalCode(Address address) throws ServiceException {
        LOGGER.info("getLocationsByPostalCode() called");

        try {
            return locationDao.findByPostalCode(address);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getLocationsByCity(Address address) throws ServiceException {
        LOGGER.info("getLocationsByCity() called");

        try {
            return locationDao.findByCity(address);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /*
      @Override
    public List<Performance> getPerformanceByDuration(Performance performance) throws ServiceException {
        LOGGER.info("getPerformanceByDuration() called");
        try {
            int duration = performance.getDuration();
            int min = Math.max((duration-30), 0);
            return performanceDao.findByDurationBetween(min, (duration+30));

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return locationDao.count();
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
            return calculatePages(locationDao.count());
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
    public void setLocationDao(LocationDao dao) {
        this.locationDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
