package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *  Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.PerformanceService}
 */
@Component
public class SearchPerformanceRestClient implements PerformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchPerformanceRestClient.class);

    private static final String GET_ALL_PERFORMANCE_URL = "/service/performance/";
    private static final String GET_ALL_PERFORMANCE_BY_NAME_URL = "/service/performance/getPerformanceByName";
    private static final String GET_ALL_PERFORMANCE_BY_DURATION_URL = "/service/performance/getPerformanceByDuration";
    private static final String GET_ALL_PERFORMANCE_BY_DESCRIPTION_URL = "/service/performance/getPerformanceByDescription";
    private static final String GET_ALL_PERFORMANCE_BY_TYPE_URL = "/service/performance/getByPerformanceType";
    private static final String GET_ALL_PERFORMANCE_BY_ARTIST_URL = "/service/performance/getByArtist";
    private static final String GET_PAGE_COUNT = "/service/performance/pagecount";

    @Autowired
    private RestClient restClient;


    @Override
    public List<PerformanceDto> getPerformances() throws ServiceException {
        return doRequest(GET_ALL_PERFORMANCE_URL, "performances");
    }

    @Override
    public List<PerformanceDto> getSpecificPage(int page) throws ServiceException {
        return doRequest(GET_ALL_PERFORMANCE_URL + "" + page, "performances");
    }

    @Override
    public List<PerformanceDto> getPerformancesByName(String name) throws ServiceException {
        String[] tmpSubstrings = name.split("\\s+");
        List<PerformanceDto> tmpList = doRequest(GET_ALL_PERFORMANCE_BY_NAME_URL+"?name="+tmpSubstrings[0], "performance by name");
        List<PerformanceDto> result = new ArrayList<>();

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (int i = 1; tmpSubstrings.length>i;i++) {
            for (PerformanceDto aTmpList : tmpList) {
                String perfName = aTmpList.getName();
                if (perfName.toLowerCase().contains(tmpSubstrings[i].toLowerCase())) {
                    result.add(aTmpList);
                }
            }
        }

        return result;

    }

    @Override
    public List<PerformanceDto> getPerformancesByArtistID(Integer ArtistID) throws ServiceException {
        return doRequest(GET_ALL_PERFORMANCE_BY_ARTIST_URL + "?artistID=" + ArtistID, "performance by artistID");
    }

    @Override
    public List<PerformanceDto> getPerformancesByDuration(Integer duration) throws ServiceException{
        return doRequest(GET_ALL_PERFORMANCE_BY_DURATION_URL+"?duration="+duration.toString(), "performance by duration");
    }

    @Override
    public List<PerformanceDto> getPerformancesByType(PerformanceTypeDto type) throws ServiceException {
        return doRequest(GET_ALL_PERFORMANCE_BY_TYPE_URL+"?performanceTypeDto="+type.name(), "performance by type");
    }

    @Override
    public List<PerformanceDto> getPerformancesByDescription(String description) throws ServiceException {
        String[] tmpSubstrings = description.split("\\s+");
        List<PerformanceDto> tmpList = doRequest(GET_ALL_PERFORMANCE_BY_DESCRIPTION_URL+"?description="+tmpSubstrings[0], "performance by description");
        List<PerformanceDto> result = new ArrayList<>();

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (PerformanceDto aTmpList : tmpList) {
            String perfDesc = aTmpList.getDescription();
            if (perfDesc.toLowerCase().contains(description.toLowerCase())) {
                result.add(aTmpList);
            }
        }


        return result;
    }

    private List<PerformanceDto> doRequest(String urlType, String type) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(urlType);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving "+type+" from {}", url);
        List<PerformanceDto> performances;
        try {
            ParameterizedTypeReference<ArrayList<PerformanceDto>> ref = new ParameterizedTypeReference<ArrayList<PerformanceDto>>() {};
            ResponseEntity<ArrayList<PerformanceDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            performances = response.getBody();

        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("RestClientException when retrieving artists: " + e.getMessage());
            throw new ServiceException("Could not retrieve "+type+ " " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return performances;
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
