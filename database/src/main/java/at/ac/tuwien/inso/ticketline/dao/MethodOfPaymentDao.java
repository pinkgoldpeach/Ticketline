package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.MethodOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dev on 03/06/16.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.MethodOfPayment}
 */
@Repository
public interface MethodOfPaymentDao extends JpaRepository<MethodOfPayment, Integer> {
}

