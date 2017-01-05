package at.ac.tuwien.inso.ticketline.server.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Entry Point will not redirect to any sort of Login - it will return the HTTP status code 401.
 */
@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private String errorMessage;

	/**
	 * Initializes the object
	 */
	@PostConstruct
	public void init() {
		ObjectMapper mapper = new ObjectMapper();
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.ERROR);
		msg.setText("Unauthorized access");
		
		try {
			errorMessage = mapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    	
        PrintWriter out = response.getWriter();
        out.println(errorMessage);
        out.close();
    }

}
