package at.ac.tuwien.inso.ticketline.server.service.implementation;


import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.dto.UsedAreasDto;
import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.StripeService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
@Service
public class SimpleTicketService extends AbstractPaginationService implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTicketService.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private SeatDao seatDao;


    @Autowired
    private AreaDao areaDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private StripeService stripeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getPageOfTickets(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfTickets() called");
        try {
            Page<Ticket> tickets = ticketDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return tickets.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getPageOfValidTickets(int pageNumber) throws ServiceException {
        LOGGER.info("getPageOfValidTickets() called");
        try {
            Page<Ticket> tickets = ticketDao.findAllPageableValid(new PageRequest(pageNumber, 20));
            return tickets.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket save(Ticket ticket) throws ServiceException {
        LOGGER.info("save() called");
        try {
            return ticketDao.save(ticket);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTickets() throws ServiceException {
        LOGGER.info("getAllTickets() called");
        try {
            return ticketDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllValidTickets() throws ServiceException {
        LOGGER.info("getAllValidTickets() called");
        try {
            return ticketDao.findAllValid();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Ticket save(Ticket ticket, Integer seatId, Integer areaID, Integer reservationId, Integer showId) throws ServiceException {
        LOGGER.info("save() called");
        try {
            if (seatId == null && areaID == null) {
                throw new ValidationException("Neither seat nor area is set");
            }

            if (showId == null)
                throw new Exception("show ID is not set");

            if (null != seatId) {
                ticket.setSeat(seatDao.findOne(seatId));
                LOGGER.debug("save() - loaded Seat for Id {}", seatId);

                if (getReservedSeats(showId).getIds().contains(seatId) || getSoldSeats(showId).getIds().contains(seatId))
                    throw new Exception("Seat is already in use");
            }
            if (null != areaID) {
                ticket.setArea(areaDao.findOne(areaID));
                LOGGER.debug("save() - loaded Area for Id {}", areaID);
            }
            if (null != reservationId) {
                ticket.setReservation(reservationDao.findOne(reservationId));
                LOGGER.debug("save() - loaded Reservation for Id {}", reservationId);
            }

            ticket.setShow(showDao.findOne(showId));
            LOGGER.debug("save() - loaded Show for Id {}", showId);

            ticket.setValid(true);
            ticket.setUuid(UUID.randomUUID());
            return ticketDao.save(ticket);
        } catch (Exception e) {
            LOGGER.error("Failed on saving Ticket: " + e.getMessage());
            throw new ServiceException(e);
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UsedSeatsDto getReservedSeats(int showId) throws ServiceException {
        LOGGER.info("getReservedSeats() called for show {}", showId);

        UsedSeatsDto usedSeatDtos = new UsedSeatsDto();
        try {
            usedSeatDtos.setIds(ticketDao.getReservedSeats(showId));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return usedSeatDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsedSeatsDto getSoldSeats(int showId) throws ServiceException {
        LOGGER.info("getSoldSeats() called for show {}", showId);

        UsedSeatsDto usedSeatDtos = new UsedSeatsDto();
        try {
            usedSeatDtos.setIds(ticketDao.getSoldSeats(showId));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return usedSeatDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void cancelSoldTicket(CancelTicketDto cancelTicketDto) throws ServiceException {
        LOGGER.info("cancelSoldTicket() called with reason \"{}\" for tickets:",
                cancelTicketDto.getCancellationReason(), cancelTicketDto.getIds()
        );

        int amount = 0;

        try {

            Receipt receipt = receiptDao.findOne(cancelTicketDto.getReceiptID());
            if (receipt == null)
                throw new Exception("Receipt ID is not correct");

            for (Integer integer : cancelTicketDto.getIds()) {
                Ticket ticket = ticketDao.findOne(integer);

                if (ticket == null)
                    throw new Exception("Ticket ID is not correct");

                boolean hasCanceledTicket = false;

                for (ReceiptEntry receiptEntry : receipt.getReceiptEntries()) {
                    if (receiptEntry.getTicket().getId() == ticket.getId()) {
                        amount += (receiptEntry.getUnitPrice() * 100) * receiptEntry.getAmount();

                        ticket.setValid(false);
                        ticket.setCancellationReason(cancelTicketDto.getCancellationReason());
                        ticketDao.save(ticket);
                        hasCanceledTicket = true;
                    }
                }

                //Set Invoice State
                if (hasCanceledTicket) {
                    receipt.setTransactionState(TransactionState.CANCELLED_POSITIONS);
                    receiptDao.save(receipt);
                }
            }

            // Stripe refund
            if (MethodOfPaymentType.STRIPE.equals(receipt.getMethodOfPayment().getMethodOfPaymentType())) {
                StripePayment stripePayment = (StripePayment) receipt.getMethodOfPayment();
                LOGGER.info("Refund " + amount);
                stripeService.refund(amount, cancelTicketDto.getCancellationReason(), stripePayment);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to cancel sold tickets: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket getTicketByID(Integer ticketId) throws ServiceException {
        LOGGER.info("getTicketByID() called");
        try {
            return ticketDao.findOne(ticketId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<Ticket> bookTicketAmount(UsedAreasDto usedAreasDto) throws ServiceException {
        LOGGER.info("bookTicketAmount() called");

        Show show = showDao.findOne(usedAreasDto.getShowID());

        try {
            for (Integer areaId : usedAreasDto.getAmountPerArea().keySet()) {
                Area area = new Area();
                area.setId(areaId);
                if (areaDao.getNumberOfAvailableTickets(show, area) < usedAreasDto.getAmountPerArea().get(areaId))
                    throw new Exception("Not enough Tickets available in this area");
            }

            Reservation reservation = new Reservation();
            if (usedAreasDto.getReservationNumber() != null) {
                reservation = reservationDao.findByReservationNumber(usedAreasDto.getReservationNumber());
            }

            List<Ticket> tickets = new ArrayList<>();

            for (Integer areaId : usedAreasDto.getAmountPerArea().keySet()) {

                Area area = areaDao.findOne(areaId);

                for (int i = 0; i < usedAreasDto.getAmountPerArea().get(areaId); i++) {
                    Ticket ticket = new Ticket();
                    ticket.setArea(area);
                    ticket.setUuid(UUID.randomUUID());
                    ticket.setValid(true);
                    ticket.setReservation(reservation);
                    ticket.setShow(show);
                    ticket.setPrice(area.getPrice());
                    tickets.add(ticketDao.save(ticket));
                }
            }
            return tickets;
        } catch (Exception e) {
            LOGGER.error("Failed on booking Ticket: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteTickets(CancelTicketDto cancelTicketDto) throws ServiceException {
        try {
            for (Integer id : cancelTicketDto.getIds()) {
                ticketDao.delete(id);
            }

        } catch (Exception e) {
            LOGGER.error("Failed on deleting Tickets: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return ticketDao.count();
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
            return calculatePages(ticketDao.count());
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
    public void setTicketDao(TicketDao dao) {
        this.ticketDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
