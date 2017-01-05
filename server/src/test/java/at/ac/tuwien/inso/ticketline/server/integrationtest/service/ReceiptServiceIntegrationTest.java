package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.dao.MethodOfPaymentDao;
import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.model.Cash;
import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.StripePayment;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Carla on 26/06/2016.
 */
public class ReceiptServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    ReceiptService receiptService;

    @Autowired
    AdminService adminService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TicketService ticketService;

    @Autowired
    ReservationService reservationService;

    @Test
    public void testGetAllReceipts(){
        try {
            List<Receipt> receipts = receiptService.getAllReceipts();
            assertTrue(receipts.size() ==2);
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testFindReceiptByLastName(){
        try {
            List<Receipt> receipts = receiptService.findReceiptByLastName("Mustermann0");
            assertTrue(receipts.size() ==1);
        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void saveReceipt(){

        try {
            Receipt receipt = new Receipt();
            receipt.setMethodOfPayment(new Cash());
            receipt.setEmployee(adminService.getAllEmployees().get(0));
            receipt.setCustomer(customerService.getAllCustomers().get(0));
            receipt.setPerformanceName("Testperformance");
            receipt.setTransactionDate(new Date());

            ReceiptEntry receiptEntry = new ReceiptEntry();
            receiptEntry.setTicket(ticketService.getAllTickets().get(0));
            receiptEntry.setPosition(1);
            receiptEntry.setAmount(1);
            receiptEntry.setUnitPrice(ticketService.getAllTickets().get(0).getPrice());
            receiptEntry.setReceipt(receipt);

            List<ReceiptEntry> receiptEntries = new ArrayList<>();
            receiptEntries.add(receiptEntry);

            receipt.setReceiptEntries(receiptEntries);

            assertNotNull(receiptService.save(receipt));
            assertEquals("Number of receipts in database", 3, receiptService.count());

        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }

    }

    @Test
    public void getReceiptEntries(){
        try {
            List<Receipt> receipts = receiptService.getAllReceipts();

            Receipt receipt1 = receipts.get(0);
            Receipt receipt2 = receipts.get(1);

            List<ReceiptEntry> receiptEntries1 = receipt1.getReceiptEntries();
            List<ReceiptEntry> receiptEntries2 = receipt2.getReceiptEntries();

            assertEquals("Number of receipt entries in receipt 1", 3, receiptEntries1.size());
            assertEquals("Number of receipt entries in receipt 2", 5, receiptEntries2.size());


        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void cancelTicketOfAReceipt(){
        try {
            List<Receipt> receipts = receiptService.getAllReceipts();

            Receipt receipt = receipts.get(1);

            assertEquals("Number of valid tickets", 13, ticketService.getAllValidTickets().size());

            CancelTicketDto cancelTicketDto = new CancelTicketDto();
            cancelTicketDto.setReceiptID(receipt.getId());
            cancelTicketDto.setCancellationReason("Testreason");

            List<Integer> ids = new ArrayList<>();
            ids.add(10);
            ids.add(11);
            ids.add(12);
            cancelTicketDto.setIds(ids);

            ticketService.cancelSoldTicket(cancelTicketDto);

            assertEquals("Number of valid tickets after three deletions", 10, ticketService.getAllValidTickets().size());


        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }
    }

    @Test
    public void saveReceiptWithFalseStripeToken(){
        try {
            Receipt receipt = new Receipt();
            StripePayment stripePayment = new StripePayment();
            stripePayment.setToken("false_Testtoken");
            receipt.setMethodOfPayment(stripePayment);
            receipt.setEmployee(adminService.getAllEmployees().get(0));
            receipt.setCustomer(customerService.getAllCustomers().get(0));
            receipt.setPerformanceName("Testperformance");
            receipt.setTransactionDate(new Date());

            ReceiptEntry receiptEntry = new ReceiptEntry();
            receiptEntry.setTicket(ticketService.getAllTickets().get(0));
            receiptEntry.setPosition(1);
            receiptEntry.setAmount(1);
            receiptEntry.setUnitPrice(ticketService.getAllTickets().get(0).getPrice());
            receiptEntry.setReceipt(receipt);

            List<ReceiptEntry> receiptEntries = new ArrayList<>();
            receiptEntries.add(receiptEntry);

            receipt.setReceiptEntries(receiptEntries);
            receiptService.save(receipt);

            fail("ServiceException not thrown");

        } catch (ServiceException e) {
        }

    }

    @Test
    public void saveReceiptAndDeleteReservation(){

        try {
            Receipt receipt = new Receipt();
            receipt.setMethodOfPayment(new Cash());
            receipt.setEmployee(adminService.getAllEmployees().get(0));
            receipt.setCustomer(customerService.getAllCustomers().get(0));
            receipt.setPerformanceName("Testperformance");
            receipt.setTransactionDate(new Date());

            ReceiptEntry receiptEntry = new ReceiptEntry();
            receiptEntry.setTicket(ticketService.getTicketByID(13));
            receiptEntry.setPosition(1);
            receiptEntry.setAmount(1);
            receiptEntry.setUnitPrice(ticketService.getAllTickets().get(0).getPrice());
            receiptEntry.setReceipt(receipt);

            List<ReceiptEntry> receiptEntries = new ArrayList<>();
            receiptEntries.add(receiptEntry);

            receipt.setReceiptEntries(receiptEntries);

            assertEquals("Number of reservations in database", 1, reservationService.count());


            assertNotNull(receiptService.save(receipt));
            assertEquals("Number of receipts in database", 3, receiptService.count());

            assertEquals("Number of reservations in database", 0, reservationService.count());



        } catch (ServiceException e) {
            fail("ServiceException thrown: " + e.getMessage());
        }

    }


}
