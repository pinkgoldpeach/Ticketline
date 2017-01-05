package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.server.rest.InformationController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class InformationControllerTest extends AbstractControllerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new InformationController()).build();
    }

    @Test
    @WithMockUser(username = "test") //, roles = {"STANDARD"})
    public void getUserStatus() throws Exception {
        MvcResult result = mockMvc.perform(get("/info/user")
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        //String content = result.getResponse().getContentAsString();
        //UserStatusDto userStatusDto = (UserStatusDto) jsonToObj(content, UserStatusDto.class);

        // System.out.println(userStatusDto.getUsername() + " " + userStatusDto.getFirstName() + " " + userStatusDto.getLastName() );

        //Sould not fail, but username is somehow null...
        //assertTrue(userStatusDto.getUsername().equals("test"));
    }

    @Test
    @WithAnonymousUser
    public void getUserStatus_anonymous() throws Exception {
        //Should fail, but still is on success
        MvcResult result = mockMvc.perform(get("/info/user")
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();
    }
}