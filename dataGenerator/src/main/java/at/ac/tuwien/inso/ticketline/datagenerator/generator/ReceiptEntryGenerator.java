package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.ReceiptDao;
import at.ac.tuwien.inso.ticketline.dao.ReceiptEntryDao;
import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Carla on 17/05/2016.
 */
@Component
public class ReceiptEntryGenerator implements DataGenerator{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptEntryGenerator.class);

    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private TicketDao ticketDao;


    @Override
    public void generate() {
        /*ReceiptEntry receiptEntry = new ReceiptEntry();
        receiptEntry.setAmount(20);
        receiptEntry.setPosition(1);
        receiptEntry.setUnitPrice(23.0);
        receiptEntry.setTicket(ticketDao.findOne(2));
        receiptEntry.setReceipt(receiptDao.findOne(1));

        receiptEntryDao.save(receiptEntry);*/
    }
}
