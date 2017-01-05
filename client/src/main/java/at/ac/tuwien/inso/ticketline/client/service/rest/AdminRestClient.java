package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.AdminService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.*;
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

/**
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.AdminService}
 */
@Component
public class AdminRestClient implements AdminService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRestClient.class);

    private static final String GET_ALL_EMPLOYEES_ON_PAGE_URL = "/service/admin/";
    private static final String UPDATE_EMPLOYEE_URL = "/service/admin/create";
    private static final String GET_PAGE_COUNT = "/service/admin/pagecount";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<EmployeeDto> getAllEmployeesOnPage(int page) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_EMPLOYEES_ON_PAGE_URL + page);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving all Employees from {}", url);
        ArrayList<EmployeeDto> employees;
        try {
            ParameterizedTypeReference<ArrayList<EmployeeDto>> ref = new ParameterizedTypeReference<ArrayList<EmployeeDto>>() {
            };
            ResponseEntity<ArrayList<EmployeeDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            employees = response.getBody();
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
            LOGGER.error("Got an RestClientException when retrieving all employees");
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.getAll") + e.getMessage(), e);
        }
        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageDto createOrUpdateEmployee(EmployeeDto employeeDto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(UPDATE_EMPLOYEE_URL);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(employeeDto, headers);
        LOGGER.info("Creating or updating {} at {}", employeeDto.getFirstName(), url);
        MessageDto messageDto;
        try {
            messageDto = restTemplate.postForObject(url, entity, MessageDto.class);
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
            LOGGER.error("Got an RestClientException when creating or updating user : " + e.getMessage());
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.createUpdate") + e.getMessage(), e);
        }

        LOGGER.info("Create or update with {} successful", employeeDto.getUsername());
        return messageDto;
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.getPageCount") + e.getMessage(), e);
        }
        return pageCount.getPageCount();
    }
}
