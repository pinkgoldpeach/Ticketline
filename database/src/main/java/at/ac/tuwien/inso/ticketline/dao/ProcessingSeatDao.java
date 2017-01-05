package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Poschenreithner (1328924)
 */
@Repository
public interface ProcessingSeatDao extends JpaRepository<ProcessingSeat, Integer>, ProcessingSeatDaoCustom {
}
