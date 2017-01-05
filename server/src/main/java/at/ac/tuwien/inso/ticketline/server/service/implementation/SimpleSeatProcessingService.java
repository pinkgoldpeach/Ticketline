package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ProcessingSeatDao;
import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleSeatProcessingService implements SeatProcessingService {

    /**
     * Seconds to keep Reservations
     */
    final private static int SECONDS_DEPRECATED_SECONDS = 60;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSeatProcessingService.class);

    @Autowired
    ProcessingSeatDao processingSeatDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToProcessingSeats(int showId, List<Integer> seats, String username) throws ServiceException {
        LOGGER.info("addToProcessingSeats() called for user {} show {} and seats {}", username, showId, seats);

        try {
            for (Integer seatId : seats) {
                processingSeatDao.save(new ProcessingSeat(showId, seatId, username));
                LOGGER.debug("addToProcessingSeats() - Added: show {} / seat {}", showId, seatId);
            }
        } catch (Exception e) {
            LOGGER.error("addToProcessingSeats() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }
        LOGGER.debug("addToProcessingSeats() - Added all");
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToProcessingSeats(UsedSeatsDto usedSeatsDto, String username) throws ServiceException {
        return this.addToProcessingSeats(usedSeatsDto.getShowId(), usedSeatsDto.getIds(), username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProcessingSeats(int showId, List<Integer> seats) throws ServiceException {
        LOGGER.info("checkProcessingSeats() called for show {} and seats {}", showId, seats);
        try {

            //Free all deprecated processing seats
            LOGGER.debug("Freeing deprecated processing-seats (older than {} seconds)", SECONDS_DEPRECATED_SECONDS);
            removeDeprecatedProcessingSeat(SECONDS_DEPRECATED_SECONDS);


            for (Integer seatId : seats) {
                ProcessingSeat result = processingSeatDao.getProcessingSeat(new ProcessingSeat(showId, seatId));
                if (null != result) {
                    LOGGER.debug("checkProcessingSeats() - show {} / seat {} is already in processing mode", showId, seatId);
                    return false;
                }
                LOGGER.debug("checkProcessingSeats() - show {} / seat {} is free", showId, seatId);
            }
        } catch (Exception e) {
            LOGGER.error("checkProcessingSeats() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }

        return true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UsedSeatsDto getProcessingSeats(int showId) throws ServiceException {
        LOGGER.info("getProcessingSeats() called for show {}", showId);
        try {

            //Free all deprecated processing seats
            LOGGER.debug("Freeing deprecated processing-seats (older than {} seconds)", SECONDS_DEPRECATED_SECONDS);
            removeDeprecatedProcessingSeat(SECONDS_DEPRECATED_SECONDS);

            List<Integer> ids = new ArrayList<>();
            List<ProcessingSeat> seats = processingSeatDao.getProcessingSeats(showId);

            LOGGER.debug("Found: {}", seats);

            for (ProcessingSeat ps : seats) {
                ids.add(ps.getSeatId());
            }

            LOGGER.debug("getProcessingSeats() - show {} has processing seats: ", showId, ids);
            return new UsedSeatsDto(ids, showId);

        } catch (Exception e) {
            LOGGER.error("getProcessingSeats() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeProcessingSeat(ProcessingSeat processingSeat, String username) throws ServiceException {
        LOGGER.info("freeProcessingSeat() called for show {} with seat {}", processingSeat.getShowId(), processingSeat.getSeatId());
        try {
            processingSeatDao.freeProcessingSeat(processingSeat, username);
        } catch (Exception e) {
            LOGGER.error("freeProcessingSeat() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDeprecatedProcessingSeat(int seconds) throws ServiceException {
        LOGGER.info("removeDeprecatedProcessingSeat() called with {} seconds", seconds);
        try {
            processingSeatDao.removeDeprecatedProcessingSeat(seconds);
        } catch (Exception e) {
            LOGGER.error("removeDeprecatedProcessingSeat() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeProcessingSeatsForUser(String username) throws ServiceException {
        LOGGER.info("removeProcessingSeatsForUser() called for user {}", username);
        try {
            processingSeatDao.removeProcessingSeatsForUser(username);
        } catch (Exception e) {
            LOGGER.error("removeProcessingSeatsForUser() - Error: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }
}
