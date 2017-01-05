package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;

import java.util.List;

/**
 * Created by Carla on 10/05/2016.
 */
public interface PerformanceDaoCustom {

    /**
     * Finds Performances by Artists first and last name
     * @param artist we are searching for
     * @return the list of all found performances
     */
    List<Performance> findPerformanceByArtistName(Artist artist);

    /**
     * Finds Performances by Artist
     * @param artist we are searching for
     * @return the list of all found performances
     */
    List<Performance> findPerformanceByArtist(Artist artist);

}
