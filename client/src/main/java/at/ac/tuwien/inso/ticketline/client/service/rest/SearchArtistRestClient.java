package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.ArtistService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.ArtistDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
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
 *  Implementation of {@link ArtistService}
 */
@Component
public class SearchArtistRestClient implements ArtistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchArtistRestClient.class);

    private static final String GET_ALL_ARTISTS_URL = "/service/artist/";
    private static final String GET_ALL_ARTISTS_BY_FIRSTNAME_URL = "/service/artist/getByFirstname";
    private static final String GET_ALL_ARTISTS_BY_LASTNAME_URL = "/service/artist/getByLastname";
    private static final String GET_PAGE_COUNT = "/service/artist/pagecount";


    @Autowired
    private RestClient restClient;


    @Override
    public List<ArtistDto> getArtists() throws ServiceException {
        return doRequest(GET_ALL_ARTISTS_URL, "artists");
    }
    @Override
    public List<ArtistDto> getSpecificPage(int page) throws ServiceException {
        return doRequest(GET_ALL_ARTISTS_URL + "" + page, "artists");
    }

    @Override
    public List<ArtistDto> getArtistsByFirstname(String firstName) throws ServiceException {
        String[] tmpSubstrings = firstName.split("\\s+");
        List<ArtistDto> result = new ArrayList<>();

        List<ArtistDto> tmpList = doRequest(GET_ALL_ARTISTS_BY_FIRSTNAME_URL + "?firstname=" + tmpSubstrings[0], "artists by firstname " + tmpSubstrings[0]);

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (ArtistDto aTmpList : tmpList) {
            String artistFirst = aTmpList.getFirstname();
            if (artistFirst.toLowerCase().contains(firstName.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    @Override
    public List<ArtistDto> getArtistsByLastname(String lastname) throws ServiceException {
        String[] tmpSubstrings = lastname.split("\\s+");
        List<ArtistDto> result = new ArrayList<>();

        List<ArtistDto> tmpList = doRequest(GET_ALL_ARTISTS_BY_LASTNAME_URL + "?lastname=" + tmpSubstrings[0], "artists bay lastname " +  tmpSubstrings[0]);

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (ArtistDto aTmpList : tmpList) {
            String artistalast = aTmpList.getLastname();
            if (artistalast.toLowerCase().contains(lastname.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }


    private List<ArtistDto> doRequest(String urlType, String type) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(urlType);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving "+type+" from {}", url);
        List<ArtistDto> artists;
        try {
            ParameterizedTypeReference<ArrayList<ArtistDto>> ref = new ParameterizedTypeReference<ArrayList<ArtistDto>>() {};
            ResponseEntity<ArrayList<ArtistDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            artists = response.getBody();

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
        return artists;
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
