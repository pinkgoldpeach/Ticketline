package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.ReceiptDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.model.Receipt;

import java.util.List;

/**
 * Created by Hannes on 22.05.2016.
 */
public interface ReceiptService {

    List<ReceiptDto> getAllReceipts() throws ServiceException;

    MessageDto saveReceipt(ReceiptDto rc) throws ServiceException;

    /**
     * Requests a page of receipts from the server. This page will contain at most as much
     * receipts, as the server has as a given maximum.
     *
     * @param page - Number of page which will be requested at the server
     * @return - ArrayList of ReceiptDtos, which will be shown on this page
     * @throws ServiceException - in case page is a negative number or something other goes wrong when communicating
     */
    List<ReceiptDto> getAllReceipts(int page) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;

    /**
     * Returns the ticket for the given id
     *
     * @param id The id of the Ticket
     * @return The full ticket with this ID
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    TicketDto getTicket(int id) throws ServiceException;

}
