package at.ac.tuwien.inso.ticketline.server.security;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dto.UserEventDto;
import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import at.ac.tuwien.inso.ticketline.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of a Spring Security authentication handler.
 * Used by Spring Security
 */
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Autowired
    private EmployeeDao userDao;

    private ObjectMapper mapper;

    /**
     * Instantiates a new authentication handler.
     */
    public AuthenticationHandler() {
        this.mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserStatusDto userStatusDto = AuthUtil.getUserStatusDto(authentication);

        //Reset failed login counter for user after successful login
        List<Employee> users = userDao.findByUsername(userStatusDto.getUsername());
        Employee user;
        //if (1 == users.size()) {
            user = users.get(0);

            // if user has tried password too often then he cannot login correctly anymore
            if(user.getLoginFailcount() > 5){
                LOGGER.info("User has typed in correct password but is blocked and cannot do anything");
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                userStatusDto.setEvent(UserEventDto.BLOCKED);
                this.printUserStatusDto(userStatusDto,response);
                return;
            }

            if (user.getLoginFailcount() > 0) {
                LOGGER.debug("Resetting login fail counter for User {}", user.getUsername());
                user.setLoginFailcount(0);
                userDao.save(user);
            }

        if("ROLE_ADMINISTRATOR".equals(user.getPermission().name()))
            userStatusDto.getRoles().add("ADMIN");
        else
            userStatusDto.getRoles().add("STANDARD");


        userStatusDto.setEvent(UserEventDto.AUTH_SUCCESS);
        this.printUserStatusDto(userStatusDto, response);
    }



	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        //Increase failed login count for user
        String username = request.getParameter("user").trim();
        List<Employee> users = userDao.findByUsername(username);
        Employee user;
        if (1 == users.size()) {
            LOGGER.debug("Increasing login fail counter for User {}", username);
            user = users.get(0);

            // if user has tried password too often then he cannot login correctly anymore
            if(user.getLoginFailcount() > 5){
                LOGGER.info("User typed again false password but is now blocked");
                UserStatusDto userStatusDto = new UserStatusDto();
                userStatusDto.setEvent(UserEventDto.BLOCKED);
                this.printUserStatusDto(userStatusDto,response);
                return;
            }

            user.setLoginFailcount(user.getLoginFailcount() + 1);
            userDao.save(user);
        }

        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setEvent(UserEventDto.AUTH_FAILURE);
        this.printUserStatusDto(userStatusDto, response);
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setEvent(UserEventDto.LOGOUT);
        this.printUserStatusDto(userStatusDto, response);
    }

    /**
     * Prints the user status dto on the HTTP response stream.
     *
     * @param usd the user status DTO
     * @param response the HTTP response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    private void printUserStatusDto(UserStatusDto usd, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        String result;
        try {
            result = this.mapper.writeValueAsString(usd);
        } catch (JsonProcessingException e) {
            throw new ServletException(e);
        }
        response.getOutputStream().print(result);
    }

}
