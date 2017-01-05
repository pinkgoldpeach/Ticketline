package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AreaService;
import at.ac.tuwien.inso.ticketline.client.service.RowService;
import at.ac.tuwien.inso.ticketline.dto.AreaDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;
import at.ac.tuwien.inso.ticketline.dto.RowDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannes on 16.05.2016.
 */
@Component
public class AreaRestClient implements AreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaRestClient.class);

    public static final String GET_AREAS_BY_ROOM_URL = "/service/area/getByRoom?roomID=";
    public static final String GET_FREE_TICKETS_BY_SHOW_AND_AREA_URL = "/service/area/getNumberOfAvailableTickets";


    @Autowired
    private RestClient restClient;

    @Override
    public List<AreaDto> getAreasByRoom(RoomDto room) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String urlType;
        String url = this.restClient.createServiceUrl(GET_AREAS_BY_ROOM_URL+room.getId());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving rows of room "+room.getId()+" from {}", url);
        List<AreaDto> areas;
        try {
            ParameterizedTypeReference<ArrayList<AreaDto>> ref = new ParameterizedTypeReference<ArrayList<AreaDto>>() {};
            ResponseEntity<ArrayList<AreaDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            areas = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve rows of room "+room.getId()+" " + e.getMessage(), e);
        }
        return areas;
    }

    @Override
    public Integer getNumberOfAvailableTickets(Integer showID, Integer areaID) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String urlType;
        String url = this.restClient.createServiceUrl(GET_FREE_TICKETS_BY_SHOW_AND_AREA_URL+"?showID="+showID+"&areaID="+areaID);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving free ticketnumber of show "+showID+" and area "+areaID+" from {}", url);
        MessageDto answer;
        try {
            ParameterizedTypeReference<MessageDto> ref = new ParameterizedTypeReference<MessageDto>() {};
            ResponseEntity<MessageDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            answer = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve free ticketnumber of show "+showID+" and area "+areaID+": " + e.getMessage(), e);
        }
        return Integer.parseInt(answer.getText());
    }
}
