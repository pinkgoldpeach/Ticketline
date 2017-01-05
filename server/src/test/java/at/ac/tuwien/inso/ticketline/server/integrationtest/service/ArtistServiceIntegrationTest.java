package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ArtistService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Carla on 01/06/2016.
 */
public class ArtistServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private ArtistService service;


    @Test
    public void testGetPageOfArtists(){
        try {
            List<Artist> artists = service.getPageOfArtists(0, 3);
            assertEquals(3, artists.size());
            artists = service.getPageOfArtists(0, 2);
            assertEquals(2, artists.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetArtistByName(){
        try {
            Artist artist = new Artist();
            artist.setFirstname("Very");
            artist.setLastname("Important");
            List<Artist> artists = service.getArtistByName(artist);
            assertEquals(1, artists.size());
            artist.setFirstname("An");
            artist.setLastname("Kied");
            artists = service.getArtistByName(artist);
            assertEquals(1, artists.size());
            artist.setFirstname("An");
            artist.setLastname("Important");
            artists = service.getArtistByName(artist);
            assertEquals(0, artists.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetArtistByFirstname(){
        try {
            Artist artist = new Artist();
            artist.setFirstname("Very");
            List<Artist> artists = service.getArtistByFirstname(artist);
            assertEquals(1, artists.size());
            artist.setFirstname("An");
            artists = service.getArtistByFirstname(artist);
            assertEquals(2, artists.size());
            artist.setFirstname("Hulululu");
            artists = service.getArtistByFirstname(artist);
            assertEquals(0, artists.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    @Test
    public void testGetArtistByLastname(){
        try {
            Artist artist = new Artist();
            artist.setLastname("Kied");
            List<Artist> artists = service.getArtistByLastname(artist);
            assertEquals(1, artists.size());
            artist.setLastname("n");
            artists = service.getArtistByLastname(artist);
            assertEquals(2, artists.size());
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }
}
