package at.ac.tuwien.inso.ticketline.server.integrationtest.rest;

import at.ac.tuwien.inso.ticketline.server.integrationtest.service.AbstractServiceIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.fail;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractServiceIntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    final protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    final protected ObjectMapper jacksonObjectMapper = new ObjectMapper();

    /**
     * Converts a Object to a Json string
     *
     * @param obj to be converted
     * @return json string of object
     */
    final protected String objToJson(Object obj) {
        String json = null;
        try {
            json = jacksonObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            fail("Failed on converting Obj 2 Json: " + e);
        }
        return json;
    }

    /**
     * Converts a Json string to an Object
     *
     * @param jsonString to be converted
     * @param cls        target class
     * @return converted Object of class cls
     */
    final protected Object jsonToObj(String jsonString, Class cls) {
        Object o = new Object();
        try {
            o = jacksonObjectMapper.readValue(jsonString, cls);
        } catch (IOException e) {
            fail("Failed on converting Json 2 Obj: " + e);
        }
        return o;
    }
}
