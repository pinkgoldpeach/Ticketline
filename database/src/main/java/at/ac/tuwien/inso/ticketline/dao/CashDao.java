package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Cash;
import at.ac.tuwien.inso.ticketline.model.MethodOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Carla on 17/05/2016.

 /**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Cash}
 */
@Repository
public interface CashDao extends JpaRepository<MethodOfPayment, Integer> {
}
