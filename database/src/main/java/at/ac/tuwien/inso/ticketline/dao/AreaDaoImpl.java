package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Show;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public class AreaDaoImpl implements AreaDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Area> findByRoom(Room room) {
        Query query = entityManager.createQuery("SELECT a FROM Area a WHERE a.room.id = :roomID");
        query.setParameter("roomID", room.getId());
        return (List<Area>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Area> findByShow(Show show) {
        Query query = entityManager.createQuery("SELECT a FROM Area a, Show s " +
                "WHERE s.room.id = a.room.id AND s.id = :showID AND s.canceled = false");
        query.setParameter("showID", show.getId());
        return (List<Area>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    //no idea, why * is underlined - works anyways
    @Override
    public Long getNumberOfAvailableTickets(Show show, Area area) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Ticket t " +
                "WHERE t.show.id = :showID AND t.area.id = :areaID AND t.valid = true");
        query.setParameter("showID", show.getId());
        query.setParameter("areaID", area.getId());
        Long soldTickets = (Long) query.getResultList().get(0);
        Query queryAvailableTickets = entityManager.createQuery("SELECT a.ticketAmount FROM Area a " +
                "WHERE a.id = :areaID");
        queryAvailableTickets.setParameter("areaID", area.getId());
        Long ticketAmount = (Long) queryAvailableTickets.getResultList().get(0);
        return Math.max((ticketAmount-soldTickets), 0);
    }
}
