package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ArtistDaoTest extends AbstractDaoTest {

    @Autowired
    private ArtistDao artistDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void findOne() {
        Artist artist = artistDao.findOne(1);
        assertNotNull(artist);
        assertEquals("Check findOne on first element", 1, (int) artist.getId());

        artist = artistDao.findOne(2);
        assertNotNull(artist);
        assertEquals("Check findOne on second element", 2, (int) artist.getId());
    }

    @Test
    public void findOneById_NegativId() {
        assertNull(artistDao.findOne(-1));
    }

    @Test
    public void FindOneById_InvalidId() {
        assertNull(artistDao.findOne(0));
    }

    @Test
    public void findAll() {
        List<Artist> artists = artistDao.findAll();
        assertEquals("Check findAll for 3 Elements", 3, artists.size());
    }

    @Test
    public void findAllPageable() {

        List<Artist> artists = artistDao.findAllPageable(new PageRequest(0, 2)).getContent();
        assertEquals("Check findAll - is 2 first", 2, artists.size());

    }


    @Test
    public void findByName(){
        List<Artist> artists = artistDao.findByNameLike("anthon", "kied");
        assertEquals("Check findByName for 1 Element", 1, artists.size());
    }

    @Test
    public void findByFirstName(){
        List<Artist> artists = artistDao.findByFirstnameLike("an");
        assertEquals("Check findByFirstnameLike for 2 Element", 2, artists.size());
    }

    @Test
    public void findByLastName(){
        List<Artist> artists = artistDao.findByLastnameLike("tant");
        assertEquals("Check findByFirstnameLike for 1 Element", 1, artists.size());
        List<Artist> artists2 = artistDao.findByLastnameLike("lalala");
        assertEquals("Check findByFirstnameLike for 0 Element", 0, artists2.size());
    }

    @Test
    public void save() {
        List<Artist> artists = artistDao.findAll();
        assertEquals("Check findAll for 3 Elements", 3, artists.size());

        Artist a = new Artist("Rainer", "Zufall", "Text text");
        a = artistDao.save(a);
        assertTrue("Check if id of new saved element is set", a.getId() > 0);
        artists = artistDao.findAll();
        assertEquals("Check findAll for now 4 Elements", 4, artists.size());
    }

    @Test
    public void save_EmptyObject() {
        exception.expect(DataIntegrityViolationException.class);
        artistDao.save(new Artist());
    }

    @Test
    public void save_MissingFirstName() {
        exception.expect(DataIntegrityViolationException.class);
        artistDao.save(new Artist(null, "Lastname", "Text"));
    }

    @Test
    public void save_MissingLastName() {
        exception.expect(DataIntegrityViolationException.class);
        artistDao.save(new Artist("Firstname", null, "Text"));
    }

    @Test
    public void delete() {
        List<Artist> artists = artistDao.findAll();
        assertEquals("Check findAll for 3 Elements", 3, artists.size());
        artistDao.delete(artists.get(1));
        artists = artistDao.findAll();
        assertEquals("Delete artist from db - only 2 artists left", 2, artists.size());
    }

    @Test
    public void delete_InvalidId() {
        assertEquals("Check data", 3, artistDao.count());
        exception.expect(EmptyResultDataAccessException.class);
        artistDao.delete(-1);
    }

    @Test
    public void update() {
        List<Artist> artists = artistDao.findAll();
        assertEquals("Check findAll for 3 Elements", 3, artists.size());
        artists.get(0).setFirstname("Heinzi");
        artistDao.save(artists);
        artists = artistDao.findAll();
        assertEquals("Check DB data size after update - must stay 3", 3, artists.size());
        assertEquals("Check new username", "Heinzi", artists.get(0).getFirstname());
    }

}