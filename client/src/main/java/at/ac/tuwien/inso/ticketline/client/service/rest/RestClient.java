package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client for REST services
 */
@Component
public class RestClient {

    private static final String USER_AGENT = "Ticketline Client";

    private HttpComponentsClientHttpRequestFactory clientFactory;

    private List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersRestClient.class);

    @Value("#{'${server.protocol}://${server.host}:${server.port}${server.path}'}")
    private String baseUrl;

    /**
     * Initialize the client
     */
    @PostConstruct
    public void init() {
        HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();
        this.clientFactory = new HttpComponentsClientHttpRequestFactory(client);
        this.interceptors.add(new UserAgentInterceptor());
    }

    /**
     * Gets the REST template object which is used to call REST services
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate(this.clientFactory);
        template.setInterceptors(this.interceptors);
        return template;
    }

    /**
     * Gets the HTTP headers.
     *
     * @return the HTTP headers
     */
    public HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.getAcceptCharset().clear();
        return httpHeaders;
    }

    /**
     * Creates the service url.
     *
     * @param service the service
     * @return the string
     */
    public String createServiceUrl(String service) {
        return this.baseUrl + service;
    }

    /**
     * Map exception to message.
     *
     * @param exception the exception
     * @return the message dto
     */
    public MessageDto mapExceptionToMessage(HttpStatusCodeException exception) {
        MessageDto messageDto = new MessageDto();
        messageDto.setType(MessageType.ERROR);
        messageDto.setText(exception.getResponseBodyAsString());
        if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            messageDto.setText("Unauthorized access");
            return messageDto;
        }
        if (!MediaType.APPLICATION_JSON.equals(exception.getResponseHeaders().getContentType())) {
            return messageDto;
        }
        try {
            messageDto = this.mapper.readValue(exception.getResponseBodyAsString(), MessageDto.class);
        } catch (Exception e) {
            /* ignore - message text already set */
            LOGGER.info("message text already set" + exception.getMessage());
        }
        return messageDto;
    }

    /**
     * Interceptor which adds the user agent header to HTTP requests
     */
    private static class UserAgentInterceptor implements ClientHttpRequestInterceptor {

    	/**
    	 * {@inheritDoc}
    	 */
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add("User-Agent", USER_AGENT);
            return execution.execute(request, body);
        }
    }

}
