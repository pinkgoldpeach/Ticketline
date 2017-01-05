package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.CustomerService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.CustomerService}
 */
@Component
public class CustomersRestClient implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersRestClient.class);

    public static final String GET_ALL_CUSOMTERS_URL = "/service/customer/";
    public static final String CREATE_CUSOMTER_URL = "/service/customer/create";
    private static final String GET_PAGE_COUNT = "/service/customer/pagecount";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<CustomerDto> getSpecificPage(int page) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_CUSOMTERS_URL + page);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving customers from {}", url);
        ArrayList<CustomerDto> customers;
        try {
            ParameterizedTypeReference<ArrayList<CustomerDto>> ref = new ParameterizedTypeReference<ArrayList<CustomerDto>>() {
            };
            ResponseEntity<ArrayList<CustomerDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            customers = response.getBody();
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
            LOGGER.error("RestClientException when retrieving all customers: " + e.getMessage());
            throw new ServiceException(BundleManager.getBundle().getString("exception.customer.getAll") + e.getMessage(), e);
        }
        return customers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageDto saveCustomer(CustomerDto customer) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CREATE_CUSOMTER_URL);
        LOGGER.info("Create customer at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customer, headers);
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
            LOGGER.error("Got an RestClientException when creating or updating customer : " + e.getMessage());
            throw new ServiceException(BundleManager.getBundle().getString("exception.customer.createUpdate") + e.getMessage(), e);
        }

        LOGGER.info("Create or update with {} successful", customer.getFirstName());
        return msg;
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.customer.getPageCount") + e.getMessage(), e);
        }
        return pageCount.getPageCount();
    }
}
