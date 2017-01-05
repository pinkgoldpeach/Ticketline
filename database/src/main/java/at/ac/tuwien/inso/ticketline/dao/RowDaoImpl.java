package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Show;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public class RowDaoImpl implements RowDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Row> findByRoom(Room room) {
        Query query = entityManager.createQuery("SELECT r FROM Row r WHERE r.room.id = :roomID");
        query.setParameter("roomID", room.getId());
        return (List<Row>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Row> findByShow(Show show) {
        Query query = entityManager.createQuery("SELECT r FROM Row r, Show s " +
                "WHERE s.room.id = r.room.id AND s.id = :showID AND s.canceled = false ");
        query.setParameter("showID", show.getId());
        return (List<Row>) query.getResultList();
    }
}
