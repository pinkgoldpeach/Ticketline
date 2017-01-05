package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
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
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.ReceiptEntry}
 */
@Repository
public interface ReceiptEntryDao extends JpaRepository<ReceiptEntry, Integer> {

    /**
     * Finds all receipt entries, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found receipt entries
     */
    @Query(value = "SELECT r FROM ReceiptEntry r")
    Page<ReceiptEntry> findAllPageable(Pageable pageable);

    /**
     * Finds all receipt entry
     * @return the list of all found receipt entry
     */
    @Query(value = "SELECT r FROM ReceiptEntry r ")
    List<ReceiptEntry> findAll();
}
