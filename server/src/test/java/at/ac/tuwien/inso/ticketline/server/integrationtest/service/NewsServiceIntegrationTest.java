package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.NewsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class NewsServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private NewsService service;

    @Test
    public void testGetAll() {
        try {
            List<News> news = service.getAllNews();
            assertEquals(10, news.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetAll_CheckOrder() {
        try {
            List<News> news = service.getAllNews();
            assertEquals(10, news.size());
            Date date = news.get(0).getSubmittedOn();
            assertTrue(!date.before(news.get(1).getSubmittedOn()));
            date = news.get(1).getSubmittedOn();
            assertTrue(!date.before(news.get(2).getSubmittedOn()));
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

}
