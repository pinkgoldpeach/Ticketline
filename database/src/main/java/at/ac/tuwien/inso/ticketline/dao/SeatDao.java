package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Carla on 15/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Seat}
 */
@Repository
public interface SeatDao extends JpaRepository<Seat, Integer>, SeatDaoCustom {
}
