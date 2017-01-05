package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.server.rest.CustomerController;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
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
public class CustomerControllerTest extends AbstractControllerTest {

    @Autowired
    private CustomerService customerService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    private CustomerDto generateValidCustomer() {

        //New Address
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Vienna");
        addressDto.setCountry("Austria");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreet("Fakestreet 123");

        CustomerDto customer = new CustomerDto();
        customer.setDateOfBirth(new Date(489110400));
        customer.setAddress(addressDto);
        customer.setGender(GenderDto.MALE);
        customer.setEmail("test@ticketline.io");
        customer.setFirstName("Tester");
        customer.setLastName("Fake");
        customer.setCustomerStatus(CustomerStatusDto.VALID);
        return customer;
    }

    @Test
    public void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/customer/")
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        ArrayList<CustomerDto> customerDtos = (ArrayList<CustomerDto>) jsonToObj(content, List.class);

        assertTrue(customerDtos.size() >= 10);
    }

    @Test
    @Rollback
    @WithMockUser(username = "test", roles = {"STANDARD"})
    public void createCustomer() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/customer/create")
                        .content(objToJson(generateValidCustomer()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDto msg = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(Integer.parseInt(msg.getText()) > 10);
    }

    @Test
    @Rollback
    @WithMockUser(username = "test", roles = {"STANDARD"})
    public void createCustomer_fail_on_birthdate() throws Exception {
        CustomerDto customerDto = generateValidCustomer();
        customerDto.setDateOfBirth(new Date());

        boolean exceptionWasThrown = false;
        try {
            mockMvc.perform(
                    post("/customer/create")
                            .content(objToJson(customerDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk());
        } catch (NestedServletException e) {
            assertTrue(e.getMessage().contains("The person is too young"));
            exceptionWasThrown = true;
        }

        if (!exceptionWasThrown)
            fail("Validation of customer age failed!");
    }

    @Test
    public void getEmployeePage() throws Exception {
        //First page
        MvcResult result = mockMvc.perform(
                get("/customer/1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        ArrayList<CustomerDto> customerDtos = (ArrayList<CustomerDto>) jsonToObj(content, List.class);
        assertEquals(10, customerDtos.size());
    }

    @Test
    public void getEmployeePage_Empty() throws Exception {
        //5th page
        MvcResult result = mockMvc.perform(
                get("/customer/5")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        ArrayList<CustomerDto> customerDtos = (ArrayList<CustomerDto>) jsonToObj(content, List.class);
        assertEquals(0, customerDtos.size());
    }

    @Test
    public void getPageCount() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/customer/pagecount")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        PageCountDto pageCountDto = (PageCountDto) jsonToObj(content, PageCountDto.class);
        assertEquals(1, (int) pageCountDto.getPageCount());
    }

}