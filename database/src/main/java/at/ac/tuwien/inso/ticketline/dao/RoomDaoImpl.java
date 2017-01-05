package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Carla on 25/06/2016.
 */
public class RoomDaoImpl implements RoomDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public String findPriceRange(Room room) {
        Query seatChoiceQuery = entityManager.createQuery("SELECT r.seatChoice FROM Room r WHERE r.id = :id");
        seatChoiceQuery.setParameter("id", room.getId());
        Boolean seatChoice = (Boolean) seatChoiceQuery.getResultList().get(0);

        Double min = -1.0;
        Double max = -1.0;
        if(seatChoice) {
            Query minQuery = entityManager.createQuery("SELECT min(ro.price) FROM Room r, Row ro " +
                    "WHERE r.id = ro.room.id AND r.id = :id");
            minQuery.setParameter("id", room.getId());
            min = (Double) minQuery.getResultList().get(0);
            Query maxQuery = entityManager.createQuery("SELECT max(ro.price) FROM Room r, Row ro " +
                    "WHERE r.id = ro.room.id AND r.id = :id");
            maxQuery.setParameter("id", room.getId());
            max = (Double) maxQuery.getResultList().get(0);
        }else{
            Query minQuery = entityManager.createQuery("SELECT min(a.price) FROM Room r, Area a " +
                    "WHERE r.id = a.room.id AND r.id = :id");
            minQuery.setParameter("id", room.getId());
            min = (Double) minQuery.getResultList().get(0);
            Query maxQuery = entityManager.createQuery("SELECT max(a.price) FROM Room r, Area a " +
                    "WHERE r.id = a.room.id AND r.id = :id");
            maxQuery.setParameter("id", room.getId());
            max = (Double) maxQuery.getResultList().get(0);
        }
         String minMax = min + "€ - " + max+"€";
        return minMax;
    }
}
