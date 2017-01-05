package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;

import java.util.ArrayList;

/**
 * Interface for Customer Services
 */
public interface CustomerService {

    /**
     * Requests a page of customers from the server. This page will contain at most as much
     * customer, as the server has as a given maximum.
     *
     * @param page - Number of page which will be requested at the server
     * @return - ArrayList of CustomerDTOs, which will be shown on this page
     * @throws ServiceException - in case page is a negative number or something other goes wrong when communicating
     */
    ArrayList<CustomerDto> getSpecificPage(int page) throws ServiceException;

    /**
     * Creates a new customer on the server or updates an existing one. In both cases the customerDto is filled
     * with all necessary data with are requested in order to create a persistent customer. In case of update,
     * the customerDTO object has to be the same as requested from the server before.
     *
     * @param customerDto - a filled customerDto with the new data
     * @return - a MessageDTO with the status if the create or update was working
     * @throws ServiceException    - will be thrown in case something went wrong with communication or if the
     *                             HTTPStatusCode is not supported
     * @throws ValidationException - will be thrown if there was a wrong input e.g. to long values or not meeting
     *                             validation standards
     */
    MessageDto saveCustomer(CustomerDto customerDto) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;

}
