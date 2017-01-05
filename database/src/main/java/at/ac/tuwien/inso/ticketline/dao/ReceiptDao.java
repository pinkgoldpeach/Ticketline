package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Receipt}
 */
@Repository
public interface ReceiptDao extends JpaRepository<Receipt, Integer>, ReceiptDaoCustom {

    /**
     * Finds all receipts, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found receipts
     */
    @Query(value = "SELECT r FROM Receipt r")
    Page<Receipt> findAllPageable(Pageable pageable);

    /**
     * Finds all receipts
     * @return the list of all found receipts
     */
    @Query(value = "SELECT r FROM Receipt r")
    List<Receipt> findAll();
}
