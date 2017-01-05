package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.TicketService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
@Component
public class TicketRestClient implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketRestClient.class);

    public static final String GET_ALL_TICKETS_URL = "/service/ticket/";
    public static final String SAVE_TICKET_URL = "/service/ticket/publish";
    public static final String CANCEL_SOLD_TICKET_URL = "/service/ticket/cancelSoldTicket";
    public static final String BOOK_TICKETS_FOR_AREA_URL = "/service/ticket/bookTicketsForArea";
    public static final String DELETE_TICKETS_BY_ID_URL = "/service/ticket/deleteTickets";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<TicketDto> getAllTickets() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TICKETS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving Tickets from {}", url);
        ArrayList<TicketDto> tickets;
        try {
            ParameterizedTypeReference<ArrayList<TicketDto>> ref = new ParameterizedTypeReference<ArrayList<TicketDto>>() {
            };
            ResponseEntity<ArrayList<TicketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            tickets = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.ticketservice") + e.getMessage(), e);
        }
        return tickets;
    }

    @Override
    public MessageDto saveTicket(TicketPublishDto ticket) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(SAVE_TICKET_URL);
        LOGGER.info("Saving tickets at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<TicketPublishDto> entity = new HttpEntity<>(ticket, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if(e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)){
                throw new ValidationException();
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.save.ticket") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public MessageDto cancelSoldTicket(CancelTicketDto dto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CANCEL_SOLD_TICKET_URL);
        LOGGER.info("Cancel soldticket at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CancelTicketDto> entity = new HttpEntity<>(dto, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException();
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("cancel sold ticket failed") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public BookedAreaTicketsDto bookTicketsForArea(UsedAreasDto usedAreasDto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(BOOK_TICKETS_FOR_AREA_URL);
        LOGGER.info("Failed to book tickets from {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<UsedAreasDto> entity = new HttpEntity<>(usedAreasDto, headers);
        BookedAreaTicketsDto tickets;
        try {
            tickets = restTemplate.postForObject(url, entity, BookedAreaTicketsDto.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if(e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)){
                throw new ValidationException();
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("Failed to book tickets for area") + e.getMessage(), e);
        }
        return tickets;

    }

    @Override
    public MessageDto deleteTickets(CancelTicketDto cancelTicketDto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(DELETE_TICKETS_BY_ID_URL);
        LOGGER.info("Cancel soldticket at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CancelTicketDto> entity = new HttpEntity<>(cancelTicketDto, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException();
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("deleteTickets failed ") + e.getMessage(), e);
        }
        return msg;
    }

}
