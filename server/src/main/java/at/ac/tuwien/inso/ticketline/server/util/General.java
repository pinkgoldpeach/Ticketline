package at.ac.tuwien.inso.ticketline.server.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class General {

    /**
     * Returns the logged in username
     *
     * @return username of logged in person
     */
    final public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
