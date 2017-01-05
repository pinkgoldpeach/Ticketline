package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Show;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public class LocationDaoImpl implements LocationDaoCustom{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> findByShow(Show show) {
        Query query = entityManager.createQuery("SELECT l FROM Location l, Room r, Show s " +
                "WHERE r.location.id = l.id AND s.room.id = r.id AND s.id = :showID");
        query.setParameter("showID", show.getId());
        return (List<Location>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> findByStreet(Address address) {
        Query query = entityManager.createQuery("SELECT l FROM Location l " +
                "LEFT OUTER JOIN l.address a WHERE LOWER(a.street) LIKE LOWER(concat('%',:street,'%')) ");
        query.setParameter("street", address.getStreet());
        return (List<Location>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> findByCity(Address address) {
        Query query = entityManager.createQuery("SELECT l FROM Location l " +
                "LEFT OUTER JOIN l.address a WHERE LOWER(a.city) LIKE LOWER(concat('%',:city,'%')) ");
        query.setParameter("city", address.getCity());
        return (List<Location>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> findByCountry(Address address) {
        Query query = entityManager.createQuery("SELECT l FROM Location l " +
                "LEFT OUTER JOIN l.address a WHERE LOWER(a.country) LIKE LOWER(concat('%',:country,'%')) ");
        query.setParameter("country", address.getCountry());
        return (List<Location>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> findByPostalCode(Address address) {
        Query query = entityManager.createQuery("SELECT l FROM Location l " +
                "LEFT OUTER JOIN l.address a WHERE LOWER(a.postalCode) LIKE LOWER(concat('%',:postalCode,'%')) ");
        query.setParameter("postalCode", address.getPostalCode());
        return (List<Location>) query.getResultList();
    }

    /*
     @Override
    public List<Show> findByAreaPrice(Double price) {
        Query query = entityManager.createQuery("SELECT s FROM Show s, Room r, Row ro WHERE s.room.id = r.id AND ro.room.id = r.id AND ro.price BETWEEN :min AND :max");
        query.setParameter("min", price-30);
        query.setParameter("max", price+30);
        return (List<Show>) query.getResultList();
    }
     */
}
