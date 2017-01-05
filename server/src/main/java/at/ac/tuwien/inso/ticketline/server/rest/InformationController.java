package at.ac.tuwien.inso.ticketline.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.security.AuthUtil;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controller for the information REST service
 */
@Api(value = "information", description = "The information REST service")
@RestController
@RequestMapping(value = "/info")
public class InformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InformationController.class);

    /**
     * Gets the current user status.
     *
     * @return the user status
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets information about the current user", response = UserStatusDto.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public UserStatusDto getUserStatus() throws ServiceException {
        LOGGER.info("getUserStatus called()");
        return AuthUtil.getUserStatusDto(SecurityContextHolder.getContext().getAuthentication());
    }

}
