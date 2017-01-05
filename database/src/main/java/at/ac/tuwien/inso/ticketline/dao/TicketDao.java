package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Ticket}
 */
@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer>, TicketDaoCustom {

    /**
     * Finds all tickets, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found tickets
     */
    @Query(value = "SELECT t FROM Ticket t")
    Page<Ticket> findAllPageable(Pageable pageable);

    /**
     * Finds all valid tickets, pagination is possible
     *
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found valid tickets
     */
    @Query(value = "SELECT t FROM Ticket t WHERE t.valid = true")
    Page<Ticket> findAllPageableValid(Pageable pageable);

    /**
     * Finds all tickets
     * @return the list of all found tickets
     */
    @Query(value = "SELECT t FROM Ticket t ")
    List<Ticket> findAll();

    /**
     * Finds all valid tickets
     * @return the list of all found valid tickets
     */
    @Query(value = "SELECT t FROM Ticket t WHERE t.valid = true")
    List<Ticket> findAllValid();
}
