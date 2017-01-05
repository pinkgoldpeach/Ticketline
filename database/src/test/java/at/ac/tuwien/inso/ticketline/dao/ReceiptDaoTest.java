package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dev on 22/05/16.
 */
public class ReceiptDaoTest extends AbstractDaoTest {

    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    @Autowired
    private ReceiptDao receiptDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findAllReceiptEntries() {
        List<ReceiptEntry> receiptEntryList = receiptEntryDao.findAll();
        assertNotNull(receiptEntryList);
        assertEquals("Number all receipt entries found", 1, receiptEntryList.size());
    }

    @Test
    public void findAllReceiptEntriesPageOne() {
        List<ReceiptEntry> receiptEntryList = receiptEntryDao.findAllPageable(new PageRequest(0,20)).getContent();
        assertNotNull(receiptEntryList);
        assertEquals("Number all receipt entries found", 1, receiptEntryList.size());
    }

    @Test
    public void findAllReceipts() {
        List<Receipt> receipts = receiptDao.findAll();
        assertNotNull(receipts);
        assertEquals("Number all receipts found", 1, receipts.size());
    }

    @Test
    public void findAllReceiptsPageOne() {
        List<Receipt> receipts = receiptDao.findAllPageable(new PageRequest(0,20)).getContent();
        assertNotNull(receipts);
        assertEquals("Number all receipts found", 1, receipts.size());
    }

}
