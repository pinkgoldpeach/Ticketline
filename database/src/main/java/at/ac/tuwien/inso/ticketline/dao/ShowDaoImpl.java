package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public class ShowDaoImpl implements ShowDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByRowPrice(Row row) {
        Query query = entityManager.createQuery("SELECT s FROM Show s, Room r, Row ro " +
                "WHERE s.room.id = r.id AND ro.room.id = r.id " +
                "AND ro.price BETWEEN :min AND :max " +
                "AND s.canceled = false AND s.dateOfPerformance >= :date group by s.id");
        double min = Math.max((row.getPrice()-30),0);
        query.setParameter("min", min);
        query.setParameter("max", row.getPrice()+30);
        query.setParameter("date", new Date());
        return (List<Show>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByAreaPrice(Area area) {
        Query query = entityManager.createQuery("SELECT s FROM Show s, Room r, Area a " +
                "WHERE s.room.id = r.id AND a.room.id = r.id AND a.price BETWEEN :min " +
                "AND :max AND s.canceled = false AND s.dateOfPerformance >= :date group by s.id");
        double min = Math.max((area.getPrice()-30),0);
        query.setParameter("min", min);
        query.setParameter("max", area.getPrice()+30);
        query.setParameter("date", new Date());
        return (List<Show>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByPerformance(Performance performance) {
        Query q = entityManager.createQuery("SELECT s FROM Show s WHERE s.performance.id = :performance " +
                "AND s.canceled = false AND s.dateOfPerformance >= :date");
        q.setParameter("performance", performance.getId());
        q.setParameter("date", new Date());
        return (List<Show>) q.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByRoom(Room room) {
        Query q = entityManager.createQuery("SELECT s FROM Show s WHERE s.room.id = :room " +
                "AND s.canceled = false AND s.dateOfPerformance >= :date");
        q.setParameter("room", room.getId());
        q.setParameter("date", new Date());
        return (List<Show>) q.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByLocation(Location location) {
        Query q = entityManager.createQuery("SELECT s FROM Show s, Room r, Location l " +
                "WHERE s.room.id = r.id AND r.location.id = l.id AND l.id = :location " +
                "AND s.canceled = false AND s.dateOfPerformance >= :date");
        q.setParameter("location", location.getId());
        q.setParameter("date", new Date());
        return (List<Show>) q.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> findByDateRange(Date min, Date max) {
        Query query = entityManager.createQuery("SELECT s FROM Show s WHERE s.dateOfPerformance >= :min " +
                "AND s.dateOfPerformance <= :max AND s.canceled = false AND s.dateOfPerformance >= :date");
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameter("date", new Date());
        return (List<Show>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Show, Long> findTopTen() {
        Query query = entityManager.createQuery("SELECT s, Count(*) AS timesSold FROM Ticket t, Show s, " +
                "ReceiptEntry re, Receipt r WHERE t.valid = true AND s.id = t.show.id " +
                "AND re.ticket.id = t.id AND re.receipt.id = r.id AND s.dateOfPerformance >= :date " +
                "AND (r.transactionDate BETWEEN :min AND :max) Group by s.id ORDER BY timesSold desc");
        query.setParameter("date", new Date());
        query.setParameter("min", GregorianCalendar.from(ZonedDateTime.now().minusDays(30)).getTime());
        query.setParameter("max", new Date());
        query.setMaxResults(10);
        List<Object[]> lst = query.getResultList();
        HashMap<Show, Long> map = new HashMap<>();
        for (Object[] obj : lst) {
            Show show = (Show)obj[0];
            long lng =  (long)obj[1];
            map.put(show, lng);
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Show, Long> findTopTenByPerformanceType(PerformanceType performanceType) {
        Query query = entityManager.createQuery("SELECT s, Count(*) AS timesSold FROM Ticket t, Show s, " +
                "Performance p, ReceiptEntry re, Receipt r WHERE t.valid = true " +
                "AND s.id = t.show.id AND s.performance.id = p.id AND re.ticket.id = t.id AND re.receipt.id = r.id " +
                "AND s.dateOfPerformance >= :date AND p.performanceType = :performanceType " +
                "AND (r.transactionDate BETWEEN :min AND :max) Group by s.id ORDER BY timesSold desc");
        query.setParameter("date", new Date());
        query.setParameter("min", GregorianCalendar.from(ZonedDateTime.now().minusDays(30)).getTime());
        query.setParameter("max", new Date());
        query.setParameter("performanceType", performanceType);
        query.setMaxResults(10);
        List<Object[]> lst = query.getResultList();
        HashMap<Show, Long> map = new HashMap<>();
        for (Object[] obj : lst) {
            Show show = (Show)obj[0];
            long lng =  (long)obj[1];
            map.put(show, lng);
        }
        return map;
    }


}
