package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;


import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.ProcessingSeatDto;
import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.server.rest.SeatController;
import at.ac.tuwien.inso.ticketline.server.service.SeatProcessingService;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class SeatControllerTest extends AbstractControllerTest {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatProcessingService seatProcessingService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws Exception {

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new SeatController(
                        seatService,
                        seatProcessingService
                )
        ).build();

    }

    private UsedSeatsDto createUsedSeats() {
        UsedSeatsDto usedSeatsDto = new UsedSeatsDto();
        usedSeatsDto.setShowId(1);

        ArrayList<Integer> seatList = new ArrayList<>();
        seatList.add(1);
        seatList.add(2);
        usedSeatsDto.setIds(seatList);
        return usedSeatsDto;
    }


    @Test
    @WithMockUser(username = "test")
    public void lockSeats() throws Exception {

        UsedSeatsDto usedSeatsDto = createUsedSeats();

        MvcResult result = mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDto messageDto = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(messageDto.getText().equals("true"));
    }

    @Test
    @WithMockUser(username = "test")
    //@WithMockUser(username="test",roles={"USER","ADMIN"})
    public void lockSeats_already_locked() throws Exception {

        UsedSeatsDto usedSeatsDto = createUsedSeats();

        MvcResult result = mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDto messageDto = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(messageDto.getText().equals("true"));

        result = mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        messageDto = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(messageDto.getText().equals("false"));
    }

    @Test
    @WithMockUser(username = "test")
    //@WithMockUser(username="test",roles={"USER","ADMIN"})
    public void lockSeats_partial_locked() throws Exception {

        UsedSeatsDto usedSeatsDto = createUsedSeats();

        MvcResult result = mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDto messageDto = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(messageDto.getText().equals("true"));

        //Unlock seat 2
        ProcessingSeatDto processingSeatDto = new ProcessingSeatDto(usedSeatsDto.getShowId(), 2);
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());


        //Try to lock seat 1 and 2
        result = mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        messageDto = (MessageDto) jsonToObj(content, MessageDto.class);
        assertTrue(messageDto.getText().equals("false"));
    }


    @Test
    @WithMockUser(username = "test")
    public void unlockSeats() throws Exception {
        UsedSeatsDto usedSeatsDto = createUsedSeats();

        //Lock Seats
        mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Get locked seats
        MvcResult result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        UsedSeatsDto usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 2);

        //Unlock seats for this user
        mockMvc.perform(
                get("/seat/unlockSeats")
        ).andExpect(status().isOk());


        //Get locked seats
        result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 0);
    }

    @Test
    @WithMockUser(username = "test")
    public void unlockSeat() throws Exception {
        UsedSeatsDto usedSeatsDto = createUsedSeats();
        usedSeatsDto.getIds().add(3);
        usedSeatsDto.getIds().add(4);
        usedSeatsDto.getIds().add(5);

        //Lock Seats
        mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Get locked seats
        MvcResult result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        UsedSeatsDto usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 5);

        //Unlock seat 3
        ProcessingSeatDto processingSeatDto = new ProcessingSeatDto(usedSeatsDto.getShowId(), 3);
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Get locked seats
        result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 4);

        //Unlock seat 1
        processingSeatDto = new ProcessingSeatDto(usedSeatsDto.getShowId(), 1);
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Unlock seat 2
        processingSeatDto = new ProcessingSeatDto(usedSeatsDto.getShowId(), 2);
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Get locked seats
        result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        content = result.getResponse().getContentAsString();
        usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 2);

    }

    @Test
    @WithMockUser(username = "test")
    public void unlockSeat_two_times() throws Exception {
        UsedSeatsDto usedSeatsDto = createUsedSeats();

        //Lock Seats
        mockMvc.perform(
                post("/seat/lockSeats")
                        .content(objToJson(usedSeatsDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Get locked seats
        MvcResult result = mockMvc.perform(
                get("/seat/getLockedSeats").
                        param("showId", "1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        UsedSeatsDto usedSeatsDtoResponse = (UsedSeatsDto) jsonToObj(content, UsedSeatsDto.class);
        assertTrue(usedSeatsDtoResponse.getIds().size() == 2);

        //Unlock seat 2
        ProcessingSeatDto processingSeatDto = new ProcessingSeatDto(usedSeatsDto.getShowId(), 2);
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        //Unlock seat 2 again
        mockMvc.perform(
                post("/seat/unlockSeat")
                        .content(objToJson(processingSeatDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

    }
}