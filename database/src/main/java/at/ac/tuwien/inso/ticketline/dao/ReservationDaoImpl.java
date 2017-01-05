package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dev on 23/05/16.
 * @author Patrick Weiszkirchner
 */
public class ReservationDaoImpl implements ReservationDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findReservationByLastName(String lastname) {
        Query query = entityManager.createQuery("SELECT re FROM Reservation re " +
                "LEFT OUTER JOIN re.customer cu WHERE lower(cu.lastname) " +
                "LIKE Lower(concat('%',:firstname,'%')) GROUP BY re.id");
        query.setParameter("firstname", lastname);
        return (List<Reservation>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findAllDeprecated() {
        Query query = entityManager.createQuery("SELECT re FROM Reservation re, Ticket t, Show s " +
                "WHERE re.id = t.reservation.id AND s.id = t.show.id " +
                "AND (s.dateOfPerformance < :dateNow OR s.canceled = TRUE)");
        Date dateNow = GregorianCalendar.from(ZonedDateTime.now().plusMinutes(20)).getTime();
        query.setParameter("dateNow", dateNow);
        return (List<Reservation>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findByPerformance(Performance performance) {
        Query query = entityManager.createQuery("SELECT re FROM Reservation re, Ticket t, Show s " +
                "WHERE re.id = t.reservation.id AND s.id = t.show.id " +
                "AND s.performance.id = :performanceID GROUP BY re.id");
        query.setParameter("performanceID", performance.getId());
        return (List<Reservation>) query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findByPerformanceName(Performance performance) {
        Query query = entityManager.createQuery("SELECT re FROM Reservation re, Ticket t, Show s, Performance p " +
                "WHERE re.id = t.reservation.id AND s.id = t.show.id AND s.performance.id = p.id " +
                "AND lower(p.name) LIKE Lower(concat('%',:name,'%')) GROUP BY re.id");
        query.setParameter("name", performance.getName());
        return (List<Reservation>) query.getResultList();
    }

    /*
    @Override
    public Reservation findByReservationNumber(String reservationNumber) {
        Query query = entityManager.createQuery("SELECT re FROM Reservation re " +
                "WHERE re.reservationNumber = :resNumber");
        query.setParameter("resNumber", reservationNumber);
        return (Reservation) query.getResultList().get(0);
    }
*/

}
