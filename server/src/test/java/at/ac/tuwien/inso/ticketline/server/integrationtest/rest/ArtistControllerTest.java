package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.server.rest.ArtistController;
import at.ac.tuwien.inso.ticketline.server.service.ArtistService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ArtistControllerTest extends AbstractControllerTest {

    @Autowired
    private ArtistService artistService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ArtistController(artistService)).build();
    }

    @Test
    public void getArtistsPage() throws Exception {
        mockMvc.perform(get("/artist/1")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getArtistsPage_invalid() throws Exception {
        exception.expect(NestedServletException.class);
        mockMvc.perform(get("/artist/-1")
                .contentType(contentType));
    }

    @Test
    public void getByName() throws Exception {
        mockMvc.perform(get("/artist/getByName")
                .param("firstname", "test")
                .param("lastname", "test")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getByName_fail_no_params() throws Exception {
        mockMvc.perform(get("/artist/getByName")
                .contentType(contentType))
                .andExpect(status().is(400));
    }

    @Test
    public void getByName_fail_no_firstname() throws Exception {
        mockMvc.perform(get("/artist/getByName")
                .param("lastname", "test")
                .contentType(contentType))
                .andExpect(status().is(400));
    }

    @Test
    public void getByName_fail_no_lastname() throws Exception {
        mockMvc.perform(get("/artist/getByName")
                .param("firstname", "test")
                .contentType(contentType))
                .andExpect(status().is(400));
    }

    @Test
    public void getByFirstname() throws Exception {
        mockMvc.perform(get("/artist/getByFirstname")
                .param("firstname", "test")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getByFirstname_fail_no_param() throws Exception {
        mockMvc.perform(get("/artist/getByFirstname")
                .contentType(contentType))
                .andExpect(status().is(400));
    }


    @Test
    public void getByLastname() throws Exception {
        mockMvc.perform(get("/artist/getByLastname")
                .param("lastname", "test")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getByLastname_fail_no_param() throws Exception {
        mockMvc.perform(get("/artist/getByLastname")
                .contentType(contentType))
                .andExpect(status().is(400));
    }

    @Test
    public void getPageCount() throws Exception {
        mockMvc.perform(get("/artist/pagecount")
                .contentType(contentType))
                .andExpect(status().is(200));
    }

}