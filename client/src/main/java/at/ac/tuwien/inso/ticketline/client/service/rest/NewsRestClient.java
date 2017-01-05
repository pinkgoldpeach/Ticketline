package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.NewsService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
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
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.NewsService}
 */
@Component
public class NewsRestClient implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRestClient.class);

    public static final String GET_ALL_NEWS_URL = "/service/news/";
    public static final String PUBLISH_NEWS_URL = "/service/news/publish";
    public static final String GET_SPECIFIC_NEWS = "/service/news/specific";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<NewsDto> getAllNews() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_NEWS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving news from {}", url);
        ArrayList<NewsDto> news;
        try {
            ParameterizedTypeReference<ArrayList<NewsDto>> ref = new ParameterizedTypeReference<ArrayList<NewsDto>>() {
            };
            ResponseEntity<ArrayList<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            news = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.getNews") + e.getMessage(), e);
        }
        return news;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<NewsDto> getSpecificNews() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SPECIFIC_NEWS);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving news from {}", url);
        ArrayList<NewsDto> news;
        try {
            ParameterizedTypeReference<ArrayList<NewsDto>> ref = new ParameterizedTypeReference<ArrayList<NewsDto>>() {
            };
            ResponseEntity<ArrayList<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            news = response.getBody();
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.getNews") + e.getMessage(), e);
        }
        return news;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageDto publishNews(NewsDto news) throws ServiceException {

        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(PUBLISH_NEWS_URL);
        LOGGER.info("Publish news at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<NewsDto> entity = new HttpEntity<>(news, headers);
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.publish") + e.getMessage(), e);
        }
        return msg;
    }

}
