package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.RoomService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.RoomService}
 */
@Component
public class RoomRestClient implements RoomService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomRestClient.class);

    public static final String GET_PRICE_RANGE = "/service/room/getPriceRangeOfRoom";

    @Autowired
    private RestClient restClient;

    @Override
    public String getRoomPriceRange(RoomDto roomDto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_PRICE_RANGE + "?roomID=" + roomDto.getId());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving priceRange from {}", url);
        String priceRange;
        try {
            ParameterizedTypeReference<MessageDto> ref = new ParameterizedTypeReference<MessageDto>() {
            };
            ResponseEntity<MessageDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            priceRange = response.getBody().getText();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("Got an RestClientException when retrieving priceRange");
            throw new ServiceException(BundleManager.getBundle().getString("exception.room.priceRange") + e.getMessage(), e);
        }
        return priceRange;
    }
}
