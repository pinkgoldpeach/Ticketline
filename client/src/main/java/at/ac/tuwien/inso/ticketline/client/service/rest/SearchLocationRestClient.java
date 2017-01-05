package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.LocationService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.LocationDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;
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
 *  Implementation of {@link LocationService}
 */
@Component
public class SearchLocationRestClient implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchLocationRestClient.class);

    private static final String GET_ALL_LOCATION_URL = "/service/location/";
    private static final String GET_ALL_LOCATION_BY_NAME_URL = "/service/location/getByName";
    private static final String GET_ALL_LOCATION_BY_STREET_URL = "/service/location/getByStreet";
    private static final String GET_ALL_LOCATION_BY_CITY_URL = "/service/location/getByCity";
    private static final String GET_ALL_LOCATION_BY_LAND_URL = "/service/location/getByCountry";
    private static final String GET_ALL_LOCATION_BY_POSTAL_URL = "/service/location/getByPostalCode";
    private static final String GET_PAGE_COUNT = "/service/location/pagecount";

    @Autowired
    private RestClient restClient;

    private List<LocationDto> allLocations =null;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getSpecificPage(int page) throws ServiceException {
        return doRequest(GET_ALL_LOCATION_URL + "" + page, "location");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByName(String name) throws ServiceException {
        String[] tmpSubstrings = name.split("\\s+");
        List<LocationDto> result = new ArrayList<>();

        List<LocationDto> tmpList = doRequest(GET_ALL_LOCATION_BY_NAME_URL+"?name="+tmpSubstrings[0], "location by name "+tmpSubstrings[0]);

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (LocationDto aTmpList : tmpList) {
            String locName = aTmpList.getName();
            if (locName.toLowerCase().contains(name.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByStreet(String street) throws ServiceException {
        String[] tmpSubstrings = street.split("\\s+");
        List<LocationDto> result = new ArrayList<>();
        List<LocationDto> tmpList = doRequest(GET_ALL_LOCATION_BY_STREET_URL+"?streetName="+tmpSubstrings[0], "location by street " +tmpSubstrings[0]);

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (LocationDto aTmpList : tmpList) {
            String locStreet = aTmpList.getAddress().getStreet();
            if (locStreet.toLowerCase().contains(street.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByCity(String city) throws ServiceException{
        String[] tmpSubstrings = city.split("\\s+");
        List<LocationDto> result = new ArrayList<>();
        List<LocationDto> tmpList = doRequest(GET_ALL_LOCATION_BY_CITY_URL+"?city="+tmpSubstrings[0], "location by city "+tmpSubstrings[0]);

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (LocationDto aTmpList : tmpList) {
            String locCity = aTmpList.getAddress().getCity();
            if (locCity.toLowerCase().contains(city.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByLand(String land) throws ServiceException {
        String[] tmpSubstrings = land.split("\\s+");
        List<LocationDto> tmpList = doRequest(GET_ALL_LOCATION_BY_LAND_URL+"?country="+tmpSubstrings[0], "lcoation by land "+tmpSubstrings[0]);
        List<LocationDto> result = new ArrayList<>();

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (LocationDto aTmpList : tmpList) {
            String locLand = aTmpList.getAddress().getCountry();
            if (locLand.toLowerCase().contains(land.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByPostal(String postal) throws ServiceException {
        String[] tmpSubstrings = postal.split("\\s+");
        List<LocationDto> tmpList = doRequest(GET_ALL_LOCATION_BY_POSTAL_URL+"?postalCode="+tmpSubstrings[0], "location by postal "+tmpSubstrings[0]);
        List<LocationDto> result = new ArrayList<>();

        if(tmpSubstrings.length==1) {
            return tmpList;
        }

        for (LocationDto aTmpList : tmpList) {
            String locPostal = aTmpList.getAddress().getPostalCode();
            if (locPostal.toLowerCase().contains(postal.toLowerCase())) {
                result.add(aTmpList);
            }
        }

        return result;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<LocationDto> getLocationsByRoom(String roomName) throws ServiceException {
        if (allLocations==null) {
            setAll();
        }

        List<LocationDto> result= new ArrayList<>();

        for (LocationDto loc : allLocations) {
                List<RoomDto> roomList = loc.getRooms();
                for (RoomDto room : roomList) {
                    if (room.getName().contains(roomName)) {
                        result.add(loc);
                        break;
                    }
                }
        }


        return result;
    }

    private void setAll() throws ServiceException {
       allLocations = new ArrayList<>();

        for (int i = 1;i<getPageCount();i++) {
            List<LocationDto> tmpPage= getSpecificPage(i);
            for (LocationDto loc : tmpPage) {
                allLocations.add(loc);
            }
        }
    }

    private List<LocationDto> doRequest(String urlType, String type) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(urlType);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving "+type+" from {}", url);
        List<LocationDto> locations;
        try {
            ParameterizedTypeReference<ArrayList<LocationDto>> ref = new ParameterizedTypeReference<ArrayList<LocationDto>>() {};
            ResponseEntity<ArrayList<LocationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            locations = response.getBody();

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
            LOGGER.error("RestClientException when retrieving locations: " + e.getMessage());
            throw new ServiceException("Could not retrieve "+type+ " " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return locations;
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
