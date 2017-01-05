package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.server.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
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
 * @author Patrick Weiszkirchner
 */
@Service
public class SimpleReceiptService extends AbstractPaginationService implements ReceiptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReceiptService.class);

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private MethodOfPaymentDao methodOfPaymentDao;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Receipt> getAllReceipts() throws ServiceException {
        LOGGER.info("getAllReceipts() called");
        try {
            return receiptDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Receipt> getPageOfReceipts(int pageNumber, int entriesPerPage) throws ServiceException {
        LOGGER.info("getPageOfReceipts() called");
        try {
            Page<Receipt> receipts = receiptDao.findAll(new PageRequest(pageNumber, entriesPerPage));
            return receipts.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Receipt save(Receipt receipt) throws ServiceException {
        LOGGER.info("save() called");


        try {
            MethodOfPayment methodOfPayment = methodOfPaymentDao.save(receipt.getMethodOfPayment());

            receipt.setMethodOfPayment(methodOfPayment);
            receipt.setTransactionState(TransactionState.PAID);
            receipt = receiptDao.save(receipt);

            receipt.setMethodOfPayment(methodOfPayment);

            if (receipt.getReceiptEntries().get(0).getTicket().getReservation() != null) {
                Reservation reservation = receipt.getReceiptEntries().get(0).getTicket().getReservation();

                for (ReceiptEntry receiptEntry : receipt.getReceiptEntries()) {
                    Ticket ticket = receiptEntry.getTicket();
                    ticket.setReservation(null);
                    ticketDao.save(ticket);
                }

                reservation.setTickets(null);
                reservationDao.delete(reservation);
            }

            LOGGER.info("Receipt is paying with {}", receipt.getMethodOfPayment().getMethodOfPaymentType());

            //Stripe Payment
            if (MethodOfPaymentType.STRIPE.equals(methodOfPayment.getMethodOfPaymentType())) {

                LOGGER.info("Begin Payment with {}", methodOfPayment.getMethodOfPaymentType());

                StripePayment stripe = (StripePayment) methodOfPayment;

                int amount = 0;

                for (ReceiptEntry receiptEntry : receipt.getReceiptEntries()) {
                    amount += (receiptEntry.getUnitPrice() * 100.0) * receiptEntry.getAmount();
                }

                Charge charge = stripeService.charge(amount, receipt.getPerformanceName(), stripe);

                if (!charge.getPaid())
                    throw new Exception(charge.getFailureMessage());

                stripe.setCharge(charge.getId());
                stripeService.save(stripe);

            }

        } catch (Exception e) {
            LOGGER.error("save() error occurred: " + e);

            throw new ServiceException(e);
        }
        return receipt;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return receiptDao.count();
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
            return calculatePages(receiptDao.count());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Receipt> findReceiptByLastName(String lastname) throws ServiceException {
        LOGGER.info("findReceiptByLastName() called");
        try {
            return receiptDao.findReceiptByLastName(lastname);

        } catch (Exception e) {
            LOGGER.error("Failed to load receipts: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setReceiptDao(ReceiptDao dao) {
        this.receiptDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
