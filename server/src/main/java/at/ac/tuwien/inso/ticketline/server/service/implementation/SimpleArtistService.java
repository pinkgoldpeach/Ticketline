package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ArtistDao;
import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.AbstractPaginationService;
import at.ac.tuwien.inso.ticketline.server.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 *
 * @author Carla
 * @author Alexander Poschenreithner (1328924)
 */
@Service
public class SimpleArtistService extends AbstractPaginationService implements ArtistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);

    @Autowired
    private ArtistDao artistDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> getPageOfArtists(Integer pageNumber, int entriesPerPage) throws ServiceException {
        try {
            Page<Artist> artists = artistDao.findAllPageable(new PageRequest(pageNumber, entriesPerPage));
            return artists.getContent();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> getArtistByName(Artist artist) throws ServiceException {
        LOGGER.info("getArtistByName() called");

        return artistDao.findByNameLike(artist.getFirstname(), artist.getLastname());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> getArtistByFirstname(Artist artist) throws ServiceException {
        LOGGER.info("getArtistByFirstname() called");

        return artistDao.findByFirstnameLike(artist.getFirstname());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> getArtistByLastname(Artist artist) throws ServiceException {
        LOGGER.info("getArtistByLastname() called");

        return artistDao.findByLastnameLike(artist.getLastname());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() throws ServiceException {
        LOGGER.info("Requesting page count");
        try {
            return artistDao.count();
        } catch (Exception e) {
            LOGGER.error("Requesting page count - " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPageCount() throws ServiceException {
        LOGGER.info("getPageCount() was called");
        try {
            return calculatePages(artistDao.count());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setArtistDao(ArtistDao dao) {
        this.artistDao = dao;
    }

    // -------------------- For Testing purposes --------------------
}
