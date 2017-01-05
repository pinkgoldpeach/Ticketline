package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 26/06/2016.
 */
public class ShowServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    ShowService showService;

    @Test
    public void testGetShowsByDateAndTime(){
        Date testDate = getUtilDate("2016-07-09 00:00:00.00");
        Show s = new Show();
        s.setDateOfPerformance(testDate);
        try {
            List<Show> shows = showService.getShowsByDateAndTime(s);
            assertTrue(shows.size()==1);
            assertTrue(shows.get(0).getId() == 1);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetShowsByDateFrom(){
        Date testDate = getUtilDate("2016-07-09 00:00:00.00");
        Show s = new Show();
        s.setDateOfPerformance(testDate);
        try {
            List<Show> shows = showService.getShowsByDateFrom(s);
            assertTrue(shows.size()==4);
            assertTrue(shows.get(0).getId() == 1);

            testDate = getUtilDate("2016-07-13 00:00:00.00");
            s = new Show();
            s.setDateOfPerformance(testDate);
            shows = showService.getShowsByDateFrom(s);
            assertTrue(shows.size()==1);
            assertTrue(shows.get(0).getId() == 5);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetShowsByDateRange(){
        Date min = getUtilDate("2016-07-09 00:00:00.00");
        Date max = getUtilDate("2016-07-12 00:00:00.00");
        try {
            List<Show> shows = showService.getShowsByDateRange(min, max);
            assertTrue(shows.size()==3);
            assertTrue(shows.get(0).getId() == 1);

            min = getUtilDate("2016-07-12 00:00:00.00");
            max = getUtilDate("2016-09-28 00:00:00.00");
            shows = showService.getShowsByDateRange(min, max);
            assertTrue(shows.size()==2);
            assertTrue(shows.get(0).getId() == 3);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetShowsByRowPrice(){
        Row row = new Row();
        row.setPrice(100.0);
        try {
            List<Show> shows = showService.getShowsByRowPrice(row);
            assertTrue(shows.size() == 2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetShowsByAreaPrice(){
        Area area = new Area();
        area.setPrice(4.0);
        try {
            List<Show> shows = showService.getShowsByAreaPrice(area);
            assertTrue(shows.size() == 1);
            area.setPrice(30.0);
            shows = showService.getShowsByAreaPrice(area);
            assertTrue(shows.size() == 2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetShowsByLocation(){
        Location location = new Location();
        location.setId(1);
        try {
            List<Show> shows = showService.getShowsByLocation(location);
            assertTrue(shows.size() == 4);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetShowsByPerformance(){
        Performance performance = new Performance();
        performance.setId(1);

        try {
            List<Show> shows = showService.getShowsByPerformance(performance);
            assertTrue(shows.size() == 2);
            performance.setId(3);
            shows = showService.getShowsByPerformance(performance);
            assertTrue(shows.size() == 1);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetShowsByRoom(){
        Room room = new Room();
        room.setId(1);
        try {
            List<Show> shows = showService.getShowsByRoom(room);
            assertTrue(shows.size() == 1);
            room.setId(4);
            shows = showService.getShowsByRoom(room);
            assertTrue(shows.size() == 2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


    public Date getUtilDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateUtil;
    }
}
