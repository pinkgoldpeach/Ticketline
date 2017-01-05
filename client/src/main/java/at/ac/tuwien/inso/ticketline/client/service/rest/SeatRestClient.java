package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.SeatService;
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
import java.util.List;

/**
 * Created by Hannes on 17.05.2016.
 */
@Component
public class SeatRestClient implements SeatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatRestClient.class);

    private static final String GET_SEATS_BY_ROOM_URL = "/service/seat/getByRoom?roomID=";
    private static final String GET_SEATS_BY_ROW_URL = "/service/seat/getByRow?rowID=";
    private static final String GET_LOCKED_SEATS_BY_SHOW_URL = "/service/seat/getLockedSeats?showId=";
    private static final String LOCK_SEATS_BY_SHOW_URL = "/service/seat/lockSeats";
    private static final String UNLOCK_SEATS_BY_SHOW_URL = "/service/seat/unlockSeats";
    private static final String UNLOCK_SEAT_BY_SHOW_URL = "/service/seat/unlockSeat";
    private static final String GET_SOLD_SEATS_BY_SHOW_URL = "/service/seat/getSoldSeats/";
    private static final String GET_RESERVED_SEATS_BY_SHOW_URL = "/service/seat/getReservedSeats/";



    @Autowired
    private RestClient restClient;

    @Override
    public List<SeatDto> getSeatsByRow(RowDto row) throws ServiceException {

        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SEATS_BY_ROW_URL+row.getId());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving seats of row "+row.getId()+" from {}", url);
        List<SeatDto> seats;
        try {
            ParameterizedTypeReference<ArrayList<SeatDto>> ref = new ParameterizedTypeReference<ArrayList<SeatDto>>() {};
            ResponseEntity<ArrayList<SeatDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            seats = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve seats of row "+row.getId()+" " + e.getMessage(), e);
        }
        return seats;
    }

    @Override
    public List<SeatDto> getSeatsByRoom(RoomDto room) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SEATS_BY_ROOM_URL+room.getId());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving rows of room "+room.getId()+" from {}", url);
        List<SeatDto> seats;
        try {
            ParameterizedTypeReference<ArrayList<SeatDto>> ref = new ParameterizedTypeReference<ArrayList<SeatDto>>() {};
            ResponseEntity<ArrayList<SeatDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            seats = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve seats of room "+room.getId()+" " + e.getMessage(), e);
        }
        return seats;
    }


    //-----------------------------------------------------


    @Override
    public UsedSeatsDto getLockedSeats(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_LOCKED_SEATS_BY_SHOW_URL+showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving locked seats of showId "+showId+" from {}", url);
        UsedSeatsDto lockedSeats;
        try {
            ParameterizedTypeReference<UsedSeatsDto> ref = new ParameterizedTypeReference<UsedSeatsDto>() {};
            ResponseEntity<UsedSeatsDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            lockedSeats = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve locked seats of show "+showId+". " + e.getMessage(), e);
        }
        return lockedSeats;
    }

    @Override
    public MessageDto lockSeats(UsedSeatsDto toLockSeats) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(LOCK_SEATS_BY_SHOW_URL);
        LOGGER.info("Lock seats at showId "+toLockSeats.getShowId()+" at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<UsedSeatsDto> entity = new HttpEntity<>(toLockSeats, headers);
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
            throw new ServiceException(BundleManager.getBundle().getString("lock Seats failed") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public MessageDto unlockSeats(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(UNLOCK_SEATS_BY_SHOW_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Unlocking seats of showId "+showId+" from {}", url);
        MessageDto msg;
        try {
            ParameterizedTypeReference<MessageDto> ref = new ParameterizedTypeReference<MessageDto>() {};
            ResponseEntity<MessageDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            msg = response.getBody();

        } catch (RestClientException e) {
             throw new ServiceException("Could not unlock locked seats of show "+showId+". " + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public MessageDto unlockSeat(Integer showId, Integer seatId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(UNLOCK_SEAT_BY_SHOW_URL);
        LOGGER.info("Unlock Seat {} for Show {} from {}", seatId, showId, url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ProcessingSeatDto> entity = new HttpEntity<>(new ProcessingSeatDto(showId, seatId), headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException();
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.unlock.seat") + e.getMessage(), e);
        }
        return msg;
    }


    @Override
    public UsedSeatsDto getSoldSeats(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_SOLD_SEATS_BY_SHOW_URL+showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving sold seats of showId "+showId+" from {}", url);
        UsedSeatsDto soldSeats;
        try {
            ParameterizedTypeReference<UsedSeatsDto> ref = new ParameterizedTypeReference<UsedSeatsDto>() {};
            ResponseEntity<UsedSeatsDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            soldSeats = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve sold seats of show "+showId+". " + e.getMessage(), e);
        }
        return soldSeats;
    }

    @Override
    public UsedSeatsDto getReservedSeats(Integer showId) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_RESERVED_SEATS_BY_SHOW_URL+showId);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving reserved seats of showId "+showId+" from {}", url);
        UsedSeatsDto reservedSeats;
        try {
            ParameterizedTypeReference<UsedSeatsDto> ref = new ParameterizedTypeReference<UsedSeatsDto>() {};
            ResponseEntity<UsedSeatsDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            reservedSeats = response.getBody();

        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve reserved seats of show "+showId+". " + e.getMessage(), e);
        }
        return reservedSeats;
    }
}
