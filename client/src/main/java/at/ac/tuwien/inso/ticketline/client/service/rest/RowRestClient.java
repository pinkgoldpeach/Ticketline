package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.RowService;
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
public class RowRestClient implements RowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RowRestClient.class);

    public static final String GET_ROWS_BY_ROOM_URL = "/service/row/getByRoom?roomID=";

    @Autowired
    private RestClient restClient;

    @Override
    public List<RowDto> getRowsByRoom(RoomDto room) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String urlType;
        String url = this.restClient.createServiceUrl(GET_ROWS_BY_ROOM_URL+room.getId());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving rows of room "+room.getId()+" from {}", url);
        List<RowDto> rows;
        try {
            ParameterizedTypeReference<ArrayList<RowDto>> ref = new ParameterizedTypeReference<ArrayList<RowDto>>() {};
            ResponseEntity<ArrayList<RowDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            rows = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve rows of room "+room.getId()+" " + e.getMessage(), e);
        }
        return rows;
    }
}
