package at.ac.tuwien.inso.ticketline.server.security;

import at.ac.tuwien.inso.ticketline.dto.UserStatusDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * This class provides static helper methods for the authentication module
 */
public class AuthUtil {

    /**
     * Instantiates a new auth util.
     */
    private AuthUtil() {
    }

    /**
     * Creates a user status DTO for an authentication object.
     *
     * @param authentication the authentication
     * @return the user status DTO
     */
    public static UserStatusDto getUserStatusDto(Authentication authentication) {
        UserStatusDto result = new UserStatusDto();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof TicketlineUser)) {
            return result;
        }
        TicketlineUser user = (TicketlineUser) principal;
        result.setUsername(user.getUsername());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setAnonymous(false);
        if (user.getAuthorities().size() > 0) {
            for (GrantedAuthority a : user.getAuthorities()) {
                result.getRoles().add(a.getAuthority());
            }
        }
        return result;
    }
}
