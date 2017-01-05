package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.AuthService;
import at.ac.tuwien.inso.ticketline.dto.UserEventDto;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.CustomerService}
 */
@Component
public class AuthRestClient implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestClient.class);

    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";

    @Autowired
    private RestClient restClient;

    private UserStatusDto userStatus = new UserStatusDto();

	/**
	 * {@inheritDoc}
	 */
    public UserStatusDto getUserStatus() {
       return this.userStatus;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public UserEventDto login(String username, String password) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(LOGIN_URL);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("user", username);
        form.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        LOGGER.info("Login {} at {}", username, url);
        UserStatusDto tempUserStatus;
        try {
            tempUserStatus = restTemplate.postForObject(url, entity, UserStatusDto.class);
        } catch (RestClientException e) {
            throw new ServiceException("Login failed: " + e.getMessage(), e);
        }

        if(tempUserStatus.getEvent().equals(UserEventDto.AUTH_FAILURE) || tempUserStatus.getEvent().equals(UserEventDto.BLOCKED)){
            LOGGER.debug("Login {} not successful", username);
        } else {
            LOGGER.debug("Login {} successful", username);
        }

        this.userStatus = tempUserStatus;
        return this.userStatus.getEvent();
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void logout() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(LOGOUT_URL);
        LOGGER.info("Logout {} at {}", this.userStatus.getUsername(), url);
        UserStatusDto tempUserStatus;
        try {
            tempUserStatus = restTemplate.getForObject(url, UserStatusDto.class);
        } catch (RestClientException e) {
            throw new ServiceException("Logout failed: " + e.getMessage(), e);
        }
        if (tempUserStatus.getEvent() != UserEventDto.LOGOUT) {
            throw new ServiceException("Logout failed: Invalid event");
        } else {
            LOGGER.debug("Logout {} successful", this.userStatus.getUsername());
        }
        this.userStatus = new UserStatusDto();
    }

}
