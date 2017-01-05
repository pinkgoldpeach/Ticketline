package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleNewsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class NewsServiceTest {

    private SimpleNewsService service = null;

    private List<News> news = null;

    @Before
    public void setUp() {
        service = new SimpleNewsService();
        news = new ArrayList<>();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2013, Calendar.JUNE, 1);
        News n1 = new News(1, gc.getTime(), "first Title", "first newstext");
        news.add(n1);
        News n2 = new News(2, gc.getTime(), "Second Title", "Hudel Dudel");
        news.add(n2);
        gc.set(2013, Calendar.AUGUST, 4);
        News n3 = new News(3, gc.getTime(), "Bi Ba", "Fischers Fritz fischt frische Fische");
        news.add(n3);
        News n4 = new News(4, gc.getTime(), "Änderungsmitteilung", "Alles muss raus");
        news.add(n4);
        gc.set(2013, Calendar.SEPTEMBER, 15);
        News n5 = new News(5, gc.getTime(), "It's the last News", "Nix neues im Westen");
        news.add(n5);
    }

    @Test
    public void testGetAll() {
        NewsDao dao = Mockito.mock(NewsDao.class);
        Mockito.when(dao.findAllOrderBySubmittedOnAsc()).thenReturn(news);
        service.setNewsDao(dao);

        try {
            assertEquals(5, service.getAllNews().size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetAll_shouldThrowServiceException() {
        NewsDao dao = Mockito.mock(NewsDao.class);
        Mockito.when(dao.findAllOrderBySubmittedOnAsc()).thenThrow(new RuntimeException("no db"));
        service.setNewsDao(dao);

        try {
            service.getAllNews();
            fail("ServiceException not thrown");
        } catch (ServiceException e) {
            assertNotNull(e.getMessage());
        }
    }

}
