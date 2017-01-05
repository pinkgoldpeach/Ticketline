package at.ac.tuwien.inso.ticketline.dto;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserStatusDtoTest {

    @Test
    public void userStatusDtoIsAnonymous() {
        UserStatusDto usd = new UserStatusDto();
        assertTrue(usd.isAnonymous());
    }

}
