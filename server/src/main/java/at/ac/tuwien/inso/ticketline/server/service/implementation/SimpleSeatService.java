package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.SeatDao;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Carla on 16/05/2016.
 */
@Service
public class SimpleSeatService implements SeatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSeatService.class);

    @Autowired
    private SeatDao seatDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Seat> getSeatsByRow(Row row) throws ServiceException {
        LOGGER.info("getSeatsByRow() called");
        try {
            return seatDao.findByRow(row);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Seat> getSeatsByRoom(Room room) throws ServiceException {
        LOGGER.info("getSeatsByRoom() called");
        try {
            return seatDao.findByRoom(room);

        } catch (Exception e) {
            LOGGER.error("Service Exception " + e.getMessage());
            throw new ServiceException(e);
        }
    }
}
