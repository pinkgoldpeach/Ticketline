package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.MethodOfPayment;
import at.ac.tuwien.inso.ticketline.model.StripePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dev on 03/06/16.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.StripePayment}
 */
@Repository
public interface StripePaymentDao extends JpaRepository<MethodOfPayment, Integer> {
}

