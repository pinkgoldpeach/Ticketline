package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.server.rest.AdminController;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Alexander Poschenreithner (1328924)
 */
//@ContextConfiguration(locations = "classpath:spring/test-spring-security.xml")
public class AdminControllerTest extends AbstractControllerTest {

    @Autowired
    private AdminService adminService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(adminService)).build();
    }

    private EmployeeDto generateValidPerson() {

        //New Address
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Vienna");
        addressDto.setCountry("Austria");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreet("Fakestreet 123");

        EmployeeDto empNew = new EmployeeDto();
        empNew.setRole(EmployeeRoles.STANDARD);
        empNew.setEmployedSince(new Date());
        empNew.setUsername("TestUserREST");
        empNew.setInsuranceNumber("abc123");
        empNew.setFirstName("FirstName");
        empNew.setLastName("LastName");
        empNew.setEmail("test@ticketline.abc");
        empNew.setGender(GenderDto.FEMALE);
        empNew.setDateOfBirth(new Date(489110400));
        empNew.setAddress(addressDto);
        empNew.setPasswordHash("asdfasdfasdfasdf");

        return empNew;
    }

    @Test
    public void getAll() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/admin/")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        ArrayList<EmployeeDto> employeeDtos = (ArrayList<EmployeeDto>) jsonToObj(content, List.class);
        assertEquals(10, employeeDtos.size());
    }

    @Test
    @Rollback
    public void createEmployee() throws Exception {

        MvcResult result = mockMvc.perform(
                post("/admin/create")
                        .content(objToJson(generateValidPerson()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDto msg = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(Integer.parseInt(msg.getText()) > 10);


        result = mockMvc.perform(
                get("/admin/")
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        ArrayList<EmployeeDto> employeeDtos = (ArrayList<EmployeeDto>) jsonToObj(content, List.class);
        assertTrue(employeeDtos.size() > 10);
    }

    @Test
    @Rollback
    public void createEmployee_fail_birthdate_validation() throws Exception {

        EmployeeDto empNew = generateValidPerson();
        empNew.setDateOfBirth(new Date());

        boolean exceptionWasThrown = false;

        try {
            mockMvc.perform(
                    post("/admin/create")
                            .content(objToJson(empNew))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk());
        } catch (NestedServletException e) {
            assertTrue(e.getMessage().contains("The person is too young"));
            exceptionWasThrown = true;
        }

        if (!exceptionWasThrown)
            fail("Validation of employee age failed!");

    }

    @Test
    public void getEmployeePage() throws Exception {

        //First page
        MvcResult result = mockMvc.perform(
                get("/admin/1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        ArrayList<EmployeeDto> employeeDtos = (ArrayList<EmployeeDto>) jsonToObj(content, List.class);
        assertEquals(10, employeeDtos.size());

        //Second page
        result = mockMvc.perform(
                get("/admin/2")
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        employeeDtos = (ArrayList<EmployeeDto>) jsonToObj(content, List.class);
        assertEquals(0, employeeDtos.size());
    }

    @Test
    public void getPageCount() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/admin/pagecount")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        PageCountDto pageCountDto = (PageCountDto) jsonToObj(content, PageCountDto.class);
        assertEquals(1, (int) pageCountDto.getPageCount());
    }

    //Test if regular user can access Admin controls

    @Test
    @WithMockUser(username = "test", roles = {"STANDARD"})
    //@WithAnonymousUser
    public void createEmployee_not_admin() throws Exception {
        //This one should fail! -> no security context seems to be loaded!
        mockMvc.perform(
                post("/admin/create")
                        .content(objToJson(generateValidPerson()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("\n\n\n-----------");
        //System.out.println(authentication.getAuthorities() + " - " + authentication.getCredentials() + " - " + authentication.isAuthenticated() + " - " + authentication.getPrincipal());
    }

}