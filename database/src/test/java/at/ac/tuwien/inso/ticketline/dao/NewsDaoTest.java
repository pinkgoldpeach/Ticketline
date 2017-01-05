package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.News;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class NewsDaoTest extends AbstractDaoTest {

    @Autowired
    private NewsDao ndao;

    @Test
    public void testFindAll() {
        List<News> news = ndao.findAll();
        assertEquals("Check DB initial data - is ten first", 10, news.size());
    }

    @Test
    public void testFindAllOrderBySubmittedOnAsc_CheckOrder() {
        List<News> news = ndao.findAllOrderBySubmittedOnAsc();
        assertEquals(10, news.size());
        Date date = news.get(0).getSubmittedOn();
        assertTrue(!date.before(news.get(1).getSubmittedOn()));
        date = news.get(1).getSubmittedOn();
        assertTrue(!date.before(news.get(2).getSubmittedOn()));
        date = news.get(2).getSubmittedOn();
    }

    @Test
    public void testFindOneById() {
        News n = ndao.findOne(6);
        assertEquals(6, n.getId().longValue());
        assertEquals("News 6", n.getTitle());
    }

    @Test
    public void testFindOneById_NegativId() {
        assertNull(ndao.findOne(-1));
    }

    @Test
    public void testFindOneById_InvalidId() {
        assertNull(ndao.findOne(0));
    }

    @Test
    public void testAddNews() {
        assertEquals("Check DB initial data - is ten first", 10, ndao.count());
        News n = new News();
        n.setTitle("NewsDao test");
        n.setNewsText("test text,test text,test text,test text,test text,test text");
        n.setSubmittedOn(new GregorianCalendar().getTime());
        News saved = ndao.save(n);
        assertEquals("Check News count - should be 11", 11, ndao.count());
        n = ndao.findOne(saved.getId());
        assertNotNull(n);
    }

    @Test
    public void testAddNews_shouldThrowException_TitleNull() {
        assertEquals("Check DB initial data - is ten first", 10, ndao.count());
        News n = new News();
        n.setNewsText("test text,test text,test text,test text,test text,test text");
        n.setSubmittedOn(new GregorianCalendar().getTime());
        try {
            ndao.save(n);
            fail("DataIntegrityViolationException not thrown");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testAddNews_shouldThrowException_NewsTextNull() {
        assertEquals("Check DB initial data - is ten first", 10, ndao.count());
        News n = new News();
        n.setTitle("NewsDao test");
        n.setSubmittedOn(new GregorianCalendar().getTime());
        try {
            ndao.save(n);
            fail("DataIntegrityViolationException not thrown");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testDeleteNews() {
        assertEquals("Check DB initial data - is ten first", 10, ndao.count());
        ndao.delete(1);
        assertEquals("Check News count - should be 9", 9, ndao.count());
    }

    @Test
    public void testDeleteNews_shouldThrowException_InvalidId() {
        assertEquals("Check DB initial data - is ten first", 10, ndao.count());
        try {
            ndao.delete(-1);
            fail("EmptyResultDataAccessException not thrown");
        } catch (EmptyResultDataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }

}
