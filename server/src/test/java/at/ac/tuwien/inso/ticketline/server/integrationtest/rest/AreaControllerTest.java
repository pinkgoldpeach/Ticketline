package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.server.rest.AreaController;
import at.ac.tuwien.inso.ticketline.server.service.AreaService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class AreaControllerTest extends AbstractControllerTest {

    @Autowired
    private AreaService areaService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        /*RestAssured.baseURI = "http://localhost:8080";
        RestAssured.keystore(keystoreFile, keystorePass);
        RestAssured.port = port;
        Mockito.reset(mockedExternalServiceAuthenticator, mockedServiceGateway);*/

        //RestAssured.baseURI = "http://localhost:8080";

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AreaController(areaService)).build();


        //   RestAssured.baseURI = "http://localhost";
        //   RestAssured.port = 8080;
    }

    @Test
    public void getByRoom() throws Exception {
        mockMvc.perform(get("/area/getByRoom")
                ///.content(this.json(new Bookmark()))
                .param("roomID", "1")
                .contentType(contentType))
                .andExpect(status().isOk());

        //---------

//        String JSON = MediaType.APPLICATION_FORM_URLENCODED_VALUE.toString();

//        RequestSpecification basicAuth = RestAssured.given().auth().preemptive().basic("marvin", "42");
//        Response response = basicAuth.accept(JSON).post("service/login");

      /*  given().
                queryParam("user", "marvin").
                queryParam("password", "42").
                expect().
                statusCode(200).
                when().
                post("/login");*/


        //     System.out.println(response);

    }

    @Test
    public void getByRoom_not_found() throws Exception {
        mockMvc.perform(get("/area/getByRoom")
                .param("roomID", "99999")
                .contentType(contentType)
        ).andExpect(status().isOk());
    }

    @Test
    public void getByRoom_wrong_param_type() throws Exception {
        mockMvc.perform(get("/area/getByRoom")
                .param("roomID", "___FAIL___")
                .contentType(contentType)
        ).andExpect(status().is(400));
    }

    @Test
    public void getAreasPage() throws Exception {
        mockMvc.perform(get("/area/1")
                .contentType(contentType)
        ).andExpect(status().isOk());
    }

    @Test
    public void getAreasPage_no_param() throws Exception {
        mockMvc.perform(get("/area/")
                .contentType(contentType)
        ).andExpect(status().is(404));
    }

    @Test
    public void getPageCount() throws Exception {
        mockMvc.perform(get("/area/pagecount")
                .contentType(contentType)
        ).andExpect(status().is(200));
    }

    @Test
    public void getByShow_missing_id() throws Exception {
        mockMvc.perform(get("/area/getByShow")
                .contentType(contentType)
        ).andExpect(status().is(400));
    }

    @Test
    public void getByShow() throws Exception {
        mockMvc.perform(get("/area/getByShow")
                .param("showID", "1")
                .contentType(contentType)
        ).andExpect(status().is(200));
    }

    @Test
    public void getNumberOfAvailableTickets() throws Exception {
        mockMvc.perform(get("/area/getNumberOfAvailableTickets")
                .param("showID", "1")
                .param("areaID", "1")
                .contentType(contentType)
        ).andExpect(status().is(200));
    }

    @Test
    public void getNumberOfAvailableTickets_missing_areaId() throws Exception {
        mockMvc.perform(get("/area/getNumberOfAvailableTickets")
                .param("show", "1")
                .contentType(contentType)
        ).andExpect(status().is(400));
    }

    @Test
    public void getNumberOfAvailableTickets_missing_showId() throws Exception {
        mockMvc.perform(get("/area/getNumberOfAvailableTickets")
                .param("areaID", "1")
                .contentType(contentType)
        ).andExpect(status().is(400));
    }




}