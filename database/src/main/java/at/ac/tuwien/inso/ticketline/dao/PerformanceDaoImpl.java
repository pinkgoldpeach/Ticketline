package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import at.ac.tuwien.inso.ticketline.model.Performance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Carla on 10/05/2016.
 */
public class PerformanceDaoImpl implements PerformanceDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> findPerformanceByArtistName(Artist artist) {

        Query query = entityManager.createQuery("SELECT pe FROM Participation pa " +
                "LEFT OUTER JOIN pa.performance pe LEFT OUTER JOIN pa.artist a " +
                "WHERE lower(a.firstname) LIKE Lower(concat('%',:firstname,'%') )" +
                " AND lower(a.lastname) LIKE Lower(concat('%',:lastname,'%') )");

        query.setParameter("firstname", artist.getFirstname());
        query.setParameter("lastname", artist.getLastname());

        return (List<Performance>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> findPerformanceByArtist(Artist artist) {
        Query query = entityManager.createQuery("SELECT pe FROM Participation pa " +
                "LEFT OUTER JOIN pa.performance pe LEFT OUTER JOIN pa.artist a WHERE a.id = :artistID");
        query.setParameter("artistID", artist.getId());
        return (List<Performance>) query.getResultList();
    }


}

//SELECT performance.* FROM Performance JOIN Participation ON performance.id=participation.performance_id JOIN Artist ON artist.id=participation.artist_id WHERE lower(artist.firstname) LIKE '%anthony%'