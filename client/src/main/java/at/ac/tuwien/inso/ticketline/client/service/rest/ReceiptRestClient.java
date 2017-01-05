package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
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
 * Created by Hannes on 22.05.2016.
 */
@Component
public class ReceiptRestClient implements ReceiptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptRestClient.class);

    public static final String GET_ALL_RECEIPTS_URL = "/service/receipt/";
    public static final String SAVE_RECEIPT_URL = "/service/receipt/saveReceipt";
    private static final String GET_PAGE_COUNT = "/service/receipt/pagecount";
    private static final String GET_TICKET_INFORMATION = "/service/ticket/Id/";

    @Autowired
    private RestClient restClient;
    @Override
    public List<ReceiptDto> getAllReceipts() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_RECEIPTS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving Receipts from {}", url);
        ArrayList<ReceiptDto> receipts;
        try {
            ParameterizedTypeReference<ArrayList<ReceiptDto>> ref = new ParameterizedTypeReference<ArrayList<ReceiptDto>>() {
            };
            ResponseEntity<ArrayList<ReceiptDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            receipts = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.ticketservice") + e.getMessage(), e);
        }
        return receipts;
    }

    @Override
    public MessageDto saveReceipt(ReceiptDto receipt) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(SAVE_RECEIPT_URL);
        LOGGER.info("Save receipt at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ReceiptDto> entity = new HttpEntity<>(receipt, headers);
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.save.receipt") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public List<ReceiptDto> getAllReceipts(int page) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_RECEIPTS_URL + page);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving Receipts from {}", url);
        ArrayList<ReceiptDto> receipts;
        try {
            ParameterizedTypeReference<ArrayList<ReceiptDto>> ref = new ParameterizedTypeReference<ArrayList<ReceiptDto>>() {
            };
            ResponseEntity<ArrayList<ReceiptDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            receipts = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.ticketservice") + e.getMessage(), e);
        }
        return receipts;
    }

    @Override
    public Integer getPageCount() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_PAGE_COUNT);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving pagecount from {}", url);
        PageCountDto pageCount;
        try {
            ParameterizedTypeReference<PageCountDto> ref = new ParameterizedTypeReference<PageCountDto>() {
            };
            ResponseEntity<PageCountDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            pageCount = response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("Got an RestClientException when retrieving pageCount");
            throw new ServiceException(BundleManager.getBundle().getString("exception.receipt.getPageCount") + e.getMessage(), e);
        }
        return pageCount.getPageCount();
    }

    @Override
    public TicketDto getTicket(int id) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_TICKET_INFORMATION + id);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving ticket from {}", url);
        TicketDto ticketDto;
        try {
            ParameterizedTypeReference<TicketDto> ref = new ParameterizedTypeReference<TicketDto>() {
            };
            ResponseEntity<TicketDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            ticketDto = response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("Got an RestClientException when retrieving ticket");
            throw new ServiceException(BundleManager.getBundle().getString("exception.receipt.getTicket") + e.getMessage(), e);
        }
        return ticketDto;
    }
}
