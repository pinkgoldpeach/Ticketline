package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Carla on 08/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Participation}
 */
@Repository
public interface ParticipationDao extends JpaRepository<Participation, Integer> {
}

