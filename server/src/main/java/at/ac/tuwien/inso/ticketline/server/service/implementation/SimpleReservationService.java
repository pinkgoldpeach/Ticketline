package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.ReservationDao;
import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import at.ac.tuwien.inso.ticketline.server.util.ReservationNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleReservationService extends AbstractPaginationService implements ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReservationService.class);

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TicketDao ticketDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> getPageOfReservations(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfReservations() called");
        try {
            Page<Reservation> reservations = reservationDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return reservations.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reservation save(Reservation reservation, Integer employeeId, Integer customerId) throws ServiceException {
        LOGGER.info("save(Reservation reservation, Integer employeeId, Integer customerId) called");
        try {
            reservation.setEmployee(employeeDao.findOne(employeeId));
            LOGGER.debug("save() - loaded Employee for Id {}", employeeId);
            reservation.setCustomer(customerDao.findOne(customerId));
            LOGGER.debug("save() - loaded Customer for Id {}", customerId);

            String reservationNumber = ReservationNumberGenerator.generateString();
            LOGGER.debug("save() - Generated reservationNumber {}", reservationNumber);

            //Until we find a non existent Reservation number
            while (null != findByReservationNumber(reservationNumber)) {
                LOGGER.debug("save() - ReservationNumber {} already exists!", reservationNumber);
                reservationNumber = ReservationNumberGenerator.generateString();
            }
            LOGGER.debug("save() - Found unique ReservationNumber {} ", reservationNumber);
            reservation.setReservationNumber(reservationNumber);
            return reservationDao.save(reservation);

        } catch (Exception e) {
            LOGGER.error("Failed on saving Reservation: " + e.getMessage());
            throw new ServiceException(e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Reservation findByReservationNumber(String reservationNumber) throws ServiceException {
        LOGGER.info("findByReservationNumber() called with reservation number {}", reservationNumber);
        try {
            return reservationDao.findByReservationNumber(reservationNumber);
        } catch (Exception e) {
            LOGGER.error("Failed on finding by reservationNumber: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reservation save(Reservation reservation) throws ServiceException {
        LOGGER.info("save(Reservation reservation) called");
        try {
            return reservationDao.save(reservation);
        } catch (Exception e) {
            LOGGER.error("Failed on saving Reservation: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean cancelReservationByNumber(String reservationNumber) throws ServiceException {
        LOGGER.info("cancelCompleteReservation() called");
        try {
            Reservation reservation = reservationDao.findByReservationNumber(reservationNumber);
            if(reservation != null) {
                reservationDao.delete(reservation);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Failed to cancel reservation: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void cancelReservationPosition(CancelTicketDto cancelTicketDto) throws ServiceException {
        LOGGER.info("cancelSomeSeats() called");
        try {

            for (Integer integer : cancelTicketDto.getIds()) {
                Ticket ticket = ticketDao.findOne(integer);

                if(ticket == null)
                    throw new ServiceException("Ticket ID is not correct");
                if(ticket.getReservation() == null)
                    throw new ServiceException("This Ticket ID has no reservation ID");

                if(!ticket.getReservation().getReservationNumber().equals(cancelTicketDto.getReservationNumber()))
                    throw new ServiceException("Ticket doesn't belong to this reservation");

                ticket.getReservation().getTickets().remove(ticket);
                ticketDao.delete(ticket);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to cancel some seats of the reservation: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findReservationByLastName(String lastname) throws ServiceException {
        LOGGER.info("findReservationByLastName() called");
        try {
            return reservationDao.findReservationByLastName(lastname);

        } catch (Exception e) {
            LOGGER.error("Failed to load reservations: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findDeprecatedReservations() throws ServiceException {
        LOGGER.info("findDeprecatedReservations() called");
        try {
            return reservationDao.findAllDeprecated();
        } catch (Exception e) {
            LOGGER.error("Failed to load reservations: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Reservation reservation) throws ServiceException {

        LOGGER.info("deleteReservation() called");
        try {
            reservationDao.delete(reservation);
        } catch (Exception e) {
            LOGGER.error("Failed to delete reservation: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> getAll() throws ServiceException {
        LOGGER.info("findAll() called");
        try {
            return reservationDao.findAll();
        } catch (Exception e) {
            LOGGER.error("Failed to load reservations: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> getReservationsByPerformance(Performance performance) throws ServiceException {
        LOGGER.info("getReservationsByPerformance() called");
        try {
            return reservationDao.findByPerformance(performance);
        } catch (Exception e) {
            LOGGER.error("Failed to load reservations: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> getReservationsByPerformanceName(Performance performance) throws ServiceException {
        LOGGER.info("getReservationsByPerformanceName() called");
        try {
            return reservationDao.findByPerformanceName(performance);
        } catch (Exception e) {
            LOGGER.error("Failed to load reservations: " + e.getMessage());
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
            return reservationDao.count();
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
            return calculatePages(reservationDao.count());
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
    public void setReservationDao(ReservationDao dao) {
        this.reservationDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
