package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Reservation;
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
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Reservation}
 */
@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer>, ReservationDaoCustom {

    /**
     * Finds all reservations, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found reservations
     */
    @Query(value = "SELECT r FROM Reservation r")
    Page<Reservation> findAllPageable(Pageable pageable);


    /**
     * Finds all reservations
     * @return the list of all found reservations
     */
    @Query(value = "SELECT r FROM Reservation r ")
    List<Reservation> findAll();

    /**
     * Finds a Reservation by its reservation number
     * @param reservationNumber of reservation
     * @return found reservation
     */
    //@Query(value = "SELECT r FROM Reservation r")
    Reservation findByReservationNumber(String reservationNumber);

}
