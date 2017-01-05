package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;

import java.util.List;

/**
 * Created by dev on 23/05/16.
 */
public interface ReservationDaoCustom {

    /**
     * Find reservations by their customer's last name
     * @param lastname lastname from the customer
     * @return list of reservation from specific customer
     */
    List<Reservation> findReservationByLastName(String lastname);


    /**
     * Find reservations where the show starts in less than 20 minutes
     * these reservations are going to be deleted, could not find out how to make a custom delete method
     * @return list of reservations that are deprecated
     */
    List<Reservation> findAllDeprecated();

    /**
     * find reservations by performance (id)
     * @param performance the performance you want the reservations for
     * @return a list of all reservation for a specific performance
     */
    List<Reservation> findByPerformance(Performance performance);

    /**
     * find reservations by performance name
     * @param performance the performance name you want the reservations for
     * @return a list of all reservation for that performance name
     */
    List<Reservation> findByPerformanceName(Performance performance);


}
