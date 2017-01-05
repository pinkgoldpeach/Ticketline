package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.CancelTicketDto;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface ReservationService {

    /**
     * Returns a collection of all reservations
     *
     * @param pageNumber the pagenumber you want to access
     * @param entriesPerPage number of records to be shown per page
     * @return a collection of all reservations.
     * @throws ServiceException the service exception
     */
    List<Reservation> getPageOfReservations(int pageNumber, int entriesPerPage) throws ServiceException;

    /**
     * Saves a Reservation without set Employee and Customer. Entities will be loaded by their IDs and set on the Reservation
     *
     * @param reservation to be persisted
     * @param employeeId  to be set on Reservation
     * @param customerId  to be set on Reservation
     * @return Reservation with set DB ID
     * @throws ServiceException on error
     */
    Reservation save(Reservation reservation, Integer employeeId, Integer customerId) throws ServiceException;

    /**
     * Persists an Reservation
     *
     * @param reservation to be persisted
     * @return persisted Reservation object
     * @throws ServiceException on error
     */
    Reservation save(Reservation reservation) throws ServiceException;

    /**
     * Returns the number of records
     *
     * @return count of records
     * @throws ServiceException on error
     */
    long count() throws ServiceException;

    /**
     * Returns the number of pages for pagination
     *
     * @return count of pages
     * @throws ServiceException on error
     */
    int getPageCount() throws ServiceException;

    /**
     * Cancel whole reservation
     *
     * @param reservationNumber of reservation which should be canceled
     * @return if the cancellation was successful
     * @throws ServiceException on error
     */
    boolean cancelReservationByNumber(String reservationNumber) throws ServiceException;

    /**
     * Give back some tickets
     *
     * @param cancelTicketDto which contains tickets which should be canceled
     * @throws ServiceException on error
     */
    void cancelReservationPosition(CancelTicketDto cancelTicketDto) throws ServiceException;

    /**
     * Returns a reservation found by its reservation number
     *
     * @param reservationNumber of reservation
     * @return found reservation
     * @throws ServiceException on error
     */
    Reservation findByReservationNumber(String reservationNumber) throws ServiceException;

    /**
     * Returns a list of reservations which belongs to a customer with a specific lastname
     *
     * @param lastname of the customer
     * @return list of found reservation
     * @throws ServiceException on error
     */
    List<Reservation> findReservationByLastName(String lastname) throws ServiceException;

    /**
     *
     * returns a list of deprecated reservations
     *
     * @return list of deprecated reservations
     * @throws ServiceException on error
     */
    List<Reservation> findDeprecatedReservations() throws ServiceException;

    /**
     * deletes a reservation by entity
     * @param reservation the reservation that needs to be deleted
     * @throws ServiceException on error
     */
    void delete(Reservation reservation) throws ServiceException;


    /**
     * returns a collection of all reservations
     * @return a collection of all reservations
     * @throws ServiceException on error
     */
    List<Reservation> getAll() throws ServiceException;

    /**
     * get reservations by performance (id)
     * @param performance the performance you want the reservations for
     * @return a list of all reservation for a specific performance
     * @throws ServiceException on error
     */
    List<Reservation> getReservationsByPerformance(Performance performance) throws ServiceException;

    /**
     * get reservations by performance name
     * @param performance the performance name you want the reservations for
     * @return a list of all reservation for that performance name
     * @throws ServiceException on error
     */
    List<Reservation> getReservationsByPerformanceName(Performance performance) throws ServiceException;

}
