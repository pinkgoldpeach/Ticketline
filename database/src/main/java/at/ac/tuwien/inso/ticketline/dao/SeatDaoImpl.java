package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.model.Row;
import at.ac.tuwien.inso.ticketline.model.Seat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by Carla on 16/05/2016.
 */
public class SeatDaoImpl implements SeatDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Seat> findByRow(Row row) {
        Query query = entityManager.createQuery("SELECT s FROM Seat s WHERE s.row.id = :rowID");
        query.setParameter("rowID", row.getId());
        return (List<Seat>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Seat> findByRoom(Room room) {
        Query query = entityManager.createQuery("SELECT s FROM Seat s, Row ro " +
                "WHERE s.row.id = ro.id AND ro.room.id = :roomID");
        query.setParameter("roomID", room.getId());
        return (List<Seat>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getPrice(Seat seat) {
        Query query = entityManager.createQuery("SELECT ro.price FROM Seat s, Row ro WHERE s.row.id = ro.id");
        List<Double> res = (List<Double>) query.getResultList();
        return res.get(0);
    }
}
