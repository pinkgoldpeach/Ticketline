package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.AreaDao;
import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.AreaService;
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
 * @author Carla
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleAreaService extends AbstractPaginationService implements AreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAreaService.class);

    @Autowired
    private AreaDao areaDao;


    @Override
    public List<Area> getPageOfAreas(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfAreas() called");
        try {
            Page<Area> areas = areaDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return areas.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Area> getAreasByRoom(Room room) throws ServiceException {
        LOGGER.info("getAreasByRoom() called");
        try {
            return areaDao.findByRoom(room);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Area> getAreasByShow(Show show) throws ServiceException {
        LOGGER.info("getAreasByShow() called");
        try {
            return areaDao.findByShow(show);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Long getNumberOfAvailableTickets(Show show, Area area) throws ServiceException {
        LOGGER.info("getNumberOfAvailableTickets() called for show {}", show.getId(), area.getId());

        try {
            return areaDao.getNumberOfAvailableTickets(show, area);
        }catch (Exception e){
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
            return areaDao.count();
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
            return calculatePages(areaDao.count());
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
    public void setAreaDao(AreaDao dao) {
        this.areaDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
