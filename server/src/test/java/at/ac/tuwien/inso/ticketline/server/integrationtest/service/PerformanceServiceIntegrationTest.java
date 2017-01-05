package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 02/06/2016.
 */
public class PerformanceServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private PerformanceService service;


    @Test
    public void getAllPerformances(){
        try {
            List<Performance> performances = service.getAllPerformances();
            assertEquals(3, performances.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }

    }

    @Test
    public void getPageOfPerformances(){
        try {
            List<Performance> performances = service.getPageOfPerformances(0, 3);
            assertEquals(3, performances.size());
            performances = service.getPageOfPerformances(0, 2);
            assertEquals(2, performances.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByName(){
        try {
            Performance performance = new Performance();
            performance.setName("RHCP");
            List<Performance> performances = service.getPerformanceByName(performance);
            assertEquals(1, performances.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void getPerformanceByDuration(){

    }

    @Test
    public void getPerformanceByDescription(){

    }

    @Test
    public void getPerformanceByPerformanceType(){

    }

    @Test
    public void getPerformanceByArtistName(){

    }

    @Test
    public void getPerformanceByArtist(){

    }



}
