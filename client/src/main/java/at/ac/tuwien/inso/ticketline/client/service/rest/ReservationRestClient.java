package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.service.ReservationService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.ReservationDto;
import at.ac.tuwien.inso.ticketline.dto.ReservationPublishDto;
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
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hannes on 20.05.2016.
 */
@Component
public class ReservationRestClient implements ReservationService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestClient.class);

    public static final String GET_ALL_RESERVATIONS_URL = "/service/reservation/";
    public static final String SAVE_RESERVATION_URL = "/service/reservation/publish";
    public static final String GET_RESERVATION_BY_RESERVATIONNUMBER_URL = "/service/reservation/findByReservationNumber/";
    public static final String GET_RESERVATION_BY_LASTNAME_URL = "/service/reservation/findReservationByLastname/";
    public static final String CANCEL_TICKET_BY_POSITION_URL = "/service/reservation/cancelReservationPosition";
    public static final String CANCEL_RESERVATION_BY_RESERVATIONNUMBER_URL = "/service/reservation/cancelReservationByNumber/";
    public static final String GET_RESERVATION_BY_PERFORMANCEID_URL = "/service/reservation/findReservationByPerformanceID";
    public static final String GET_RESERVATION_BY_PERFORMANCENAME_URL = "/service/reservation/findReservationByPerformanceName";


    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<ReservationDto> getAllReservations() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_RESERVATIONS_URL);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving Tickets from {}", url);
        ArrayList<ReservationDto> reservations;
        try {
            ParameterizedTypeReference<ArrayList<ReservationDto>> ref = new ParameterizedTypeReference<ArrayList<ReservationDto>>() {
            };
            ResponseEntity<ArrayList<ReservationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            reservations = response.getBody();
        } catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.ticketservice") + e.getMessage(), e);
        }
        return reservations;
    }

    @Override
    public MessageDto saveReservation(ReservationPublishDto reservation) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(SAVE_RESERVATION_URL);
        LOGGER.info("Save reservation at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ReservationPublishDto> entity = new HttpEntity<>(reservation, headers);
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.save.reservation") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public ReservationDto findByReservationNumber(String number) throws ServiceException {
        number = number.replace(" ", "");
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_RESERVATION_BY_RESERVATIONNUMBER_URL + number);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving reservation from {}", url);
        ReservationDto reservation;
        try {
            ParameterizedTypeReference<ReservationDto> ref = new ParameterizedTypeReference<ReservationDto>() {
            };
            ResponseEntity<ReservationDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            reservation = response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        }catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.reservationservice") + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return reservation;
    }

    @Override
    public List<ReservationDto> findReservationByLastname(String lastname) throws ServiceException {
        lastname = lastname.replace(" ", "");
        RestTemplate restTemplate = this.restClient.getRestTemplate();

        String lastnameEncoded = new String(Base64.getEncoder().encode(lastname.getBytes()));

        String url = this.restClient.createServiceUrl(GET_RESERVATION_BY_LASTNAME_URL + lastnameEncoded);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving reservations by lastname {} (encoded: {}) from {}", lastname, lastnameEncoded, url);
        ArrayList<ReservationDto> reservations;
        try {
            ParameterizedTypeReference<ArrayList<ReservationDto>> ref = new ParameterizedTypeReference<ArrayList<ReservationDto>>() {
            };
            ResponseEntity<ArrayList<ReservationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            reservations = response.getBody();
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
            throw new ServiceException(BundleManager.getBundle().getString("exception.reservationservice") + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return reservations;
    }

    @Override
    public MessageDto cancelReservationPosition(CancelTicketDto cancelTicketDto) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CANCEL_TICKET_BY_POSITION_URL);
        LOGGER.info("Save ticket at {}", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CancelTicketDto> entity = new HttpEntity<>(cancelTicketDto, headers);
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
            throw new ServiceException(BundleManager.getBundle().getString("cancel reservation ticket by position failed") + e.getMessage(), e);
        }
        return msg;
    }

    public MessageDto cancelReservationByNumber(MessageDto reservationnumber) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CANCEL_RESERVATION_BY_RESERVATIONNUMBER_URL);
        LOGGER.info("Cancel Reservation {} at {}", reservationnumber.getText(), url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MessageDto> entity = new HttpEntity<>(reservationnumber, headers);
        MessageDto msg;
        try {
            msg = restTemplate.postForObject(url, entity, MessageDto.class);
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
            throw new ServiceException(BundleManager.getBundle().getString("cancel reservation ticket by position failed") + e.getMessage(), e);
        }
        return msg;
    }

    @Override
    public List<ReservationDto> findReservationByPerformanceID(Integer id) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_RESERVATION_BY_PERFORMANCEID_URL + "?performanceID=" + id);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving reservations by performanceID {} from {}", id, url);
        ArrayList<ReservationDto> reservations;
        try {
            ParameterizedTypeReference<ArrayList<ReservationDto>> ref = new ParameterizedTypeReference<ArrayList<ReservationDto>>() {
            };
            ResponseEntity<ArrayList<ReservationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            reservations = response.getBody();
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
        }catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }catch (RestClientException e) {
            throw new ServiceException(BundleManager.getBundle().getString("exception.reservationservice") + e.getMessage(), e);
        }
        return reservations;
    }

    @Override
    public List<ReservationDto> findReservationByPerformanceName(String name) throws ServiceException {
        String[] substrings = name.split("\\s+");
        LOGGER.info(substrings[0]);
        ArrayList<ReservationDto> reservations = new ArrayList<ReservationDto>();

        for (String s : substrings) {
            ArrayList<ReservationDto> tmpRes;
            RestTemplate restTemplate = this.restClient.getRestTemplate();
            String url = this.restClient.createServiceUrl(GET_RESERVATION_BY_PERFORMANCENAME_URL + "?performanceName=" + s);
            HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
            LOGGER.info("Retrieving reservations by performanceName {} from {}", s, url);

            try {
                ParameterizedTypeReference<ArrayList<ReservationDto>> ref = new ParameterizedTypeReference<ArrayList<ReservationDto>>() {
                };
                ResponseEntity<ArrayList<ReservationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
                tmpRes = response.getBody();
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
                throw new ServiceException(BundleManager.getBundle().getString("exception.reservationservice") + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
            }
            for (ReservationDto res : tmpRes) {
                if (!reservations.contains(res)) {
                    reservations.add(res);
                }
            }
        }
        return reservations;
    }
}
