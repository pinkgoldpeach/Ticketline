package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Show;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
public class TicketDaoImpl implements TicketDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getSoldSeats(Integer showId) {
        Query query = entityManager.createQuery("SELECT t.seat.id FROM Ticket t " +
                "LEFT OUTER JOIN t.show s WHERE s.id = :showId AND t.valid = true AND t.reservation IS NULL");
        query.setParameter("showId", showId);
        return (List<Integer>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getReservedSeats(Integer showId) {
        Query query = entityManager.createQuery("SELECT t.seat.id FROM Ticket t " +
                "LEFT OUTER JOIN t.show s WHERE s.id = :showId AND t.valid = true " +
                "AND t.reservation IS NOT NULL");
        query.setParameter("showId", showId);
        return (List<Integer>) query.getResultList();
    }


}
