package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public class ProcessingSeatDaoImpl implements ProcessingSeatDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProcessingSeat> getProcessingSeats(int showId) {
        Query query = entityManager.createQuery("SELECT ps FROM ProcessingSeat ps WHERE  ps.showId = :showId");
        query.setParameter("showId", showId);
        List r = query.getResultList();
        return (List<ProcessingSeat>) r;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Modifying
    @Override
    public void removeDeprecatedProcessingSeat(int seconds) {
        Date now = new Date();
        //Multiply seconds by 1000 to convert to milliseconds
        Timestamp timestamp = new Timestamp((now.getTime() - (seconds * 1000)));

        Query query = entityManager.createQuery("DELETE FROM ProcessingSeat ps " +
                "WHERE ps.processingSince < :timeStamp");
        query.setParameter("timeStamp", (timestamp));
        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Modifying
    @Override
    public void freeProcessingSeat(ProcessingSeat processingSeat, String username) {
        Query query = entityManager.createQuery("DELETE FROM ProcessingSeat ps " +
                "WHERE ps.lockedForUser = :username AND ps.showId = :showId AND ps.seatId = :seatId");
        query.setParameter("showId", processingSeat.getShowId());
        query.setParameter("seatId", processingSeat.getSeatId());
        query.setParameter("username", username);
        query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessingSeat getProcessingSeat(ProcessingSeat processingSeat) {
        Query query = entityManager.createQuery("SELECT ps FROM ProcessingSeat ps " +
                "WHERE ps.showId = :showId AND ps.seatId = :seatId");
        query.setParameter("showId", processingSeat.getShowId());
        query.setParameter("seatId", processingSeat.getSeatId());
        List r = query.getResultList();
        return (r.isEmpty()) ? null : (ProcessingSeat) r.get(0);

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Modifying
    @Override
    public void removeProcessingSeatsForUser(String username) {
        Query query = entityManager.createQuery("DELETE FROM ProcessingSeat ps " +
                "WHERE ps.lockedForUser = :username");
        query.setParameter("username", username);
        query.executeUpdate();
    }
}
