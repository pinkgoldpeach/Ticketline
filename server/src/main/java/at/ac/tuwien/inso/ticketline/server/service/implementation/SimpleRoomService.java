package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
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
public class SimpleRoomService extends AbstractPaginationService implements RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);

    @Autowired
    private RoomDao roomDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getPageOfRooms(int pageNumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Room> rooms = roomDao.findAllPageable(new PageRequest(pageNumber, entriesPerPage));
            return rooms.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getPriceRangeOfRoom(Room room) throws ServiceException {
        String priceRange = roomDao.findPriceRange(room);
        return priceRange;
/*
        try {
            String priceRange = roomDao.findPriceRange(room);
            return priceRange;
        } catch (Exception e) {
            throw new ServiceException(e);
        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return roomDao.count();
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
            return calculatePages(roomDao.count());
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
    public void setRoomDao(RoomDao dao) {
        this.roomDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
